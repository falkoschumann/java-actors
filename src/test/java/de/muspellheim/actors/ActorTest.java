/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActorTest {

    private Object worked;
    private Throwable exception;

    @Test
    public void testReceiveMessage() throws Exception {
        Actor actor = new TestingActor();

        actor.receive("Foobar");
        Thread.sleep(200);

        assertEquals("Foobar", worked);
        assertNull(exception);
    }

    @Test
    public void testHandleException() throws Exception {
        Actor actor = new TestingActor();

        actor.receive("error");
        Thread.sleep(200);

        assertNull(worked);
        assertNotNull(exception);
        assertEquals("error", exception.getMessage());
    }

    private final class TestingActor extends Actor {

        TestingActor() {
            super("ActorTest");
        }

        @Override
        protected synchronized void work(Object message) throws Exception {
            if ("error".equals(message)) {
                throw new NullPointerException("error");
            }

            worked = message;
        }

        @Override
        protected void handleException(Exception e) {
            exception = e;
        }

    }

}
