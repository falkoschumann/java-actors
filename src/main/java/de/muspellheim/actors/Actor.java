/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors;

import de.muspellheim.events.Event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An actor is a concurrently unit, which receive messages and work there asynchronously.
 * <p>The inbox queue of messages are worked as FIFO (first in, first out).</p>
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public abstract class Actor {

    /**
     * The outbox of this actor. All messages from this actor are send to its outbox.
     */
    public final Event<Object> outbox = new Event<>();

    private final BlockingQueue<Object> inbox = new LinkedBlockingQueue<>();

    Actor() {
    }

    /**
     * Creates an actor.
     *
     * @param threadName the name of the actors worker thread.
     */
    public Actor(String threadName) {
        Thread thread = new Thread(() -> work());
        thread.setName(threadName);
        thread.setDaemon(true);
        thread.start();
    }

    private void work() {
        while (true) {
            try {
                Object message = inbox.take();
                work(message);
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    /**
     * Implement this method to work one message of the inbox.
     * <p>Exceptions are handled by {@link #handleException(Exception)}.</p>
     *
     * @param message the message to process.
     * @throws Exception rethrow all exception in work process.
     */
    protected abstract void work(Object message) throws Exception;

    /**
     * Handle all exceptions of {@link #work(Object)}.
     *
     * @param e the exception to handle.
     */
    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * WIth this method the actor receive a message and put it into the inbox.
     *
     * @param message the received messsage.
     */
    public void receive(Object message) {
        inbox.offer(message);
    }

}
