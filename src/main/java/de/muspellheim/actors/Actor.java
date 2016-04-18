package de.muspellheim.actors;

import de.muspellheim.events.Event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor {

    public final Event<Object> event = new Event<>();

    private final BlockingQueue<Object> inbox = new LinkedBlockingQueue<>();

    Actor() {
    }

    public Actor(String threadName) {
        Thread t = new Thread(() -> work());
        t.setName(threadName);
        t.setDaemon(true);
        t.start();
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

    protected abstract void work(Object message);

    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    public void receive(Object message) {
        inbox.offer(message);
    }

}
