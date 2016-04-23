/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors;

import de.muspellheim.events.Event;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor {

    public final Event<Object> outbox = new Event<>();

    private final BlockingQueue<Object> inbox = new LinkedBlockingQueue<>();

    Actor() {
    }

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

    protected abstract void work(Object message) throws Exception;

    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    public void receive(Object message) {
        inbox.offer(message);
    }

}
