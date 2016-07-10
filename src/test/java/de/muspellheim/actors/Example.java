/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors;

import de.muspellheim.events.Event;

public class Example {

    public void example() {
        MyObject object1 = new MyObject();
        MyActor actor1 = new MyActor(object1);

        MyObject object2 = new MyObject();
        MyActor actor2 = new MyActor(object2);

        Message1 message = new Message1();
        actor1.receive(message);

        actor1.outbox.addHandler(m -> actor2.receive(m));
    }

    public static class MyActor extends Actor {

        private MyObject delegate;

        public MyActor(MyObject delegate) {
            super("actors worker thread name");
            this.delegate = delegate;

            delegate.anEvent.addHandler(v -> {
                Message2 m = new Message2();
                m.value = v;
                outbox.send(m);
            });
        }

        @Override
        protected void work(Object message) throws Exception {
            if (message instanceof Message1) {
                Message1 m = (Message1) message;
                delegate.doSomethingWith(m.value);
            } else if (message instanceof Message2) {
                Message2 m = (Message2) message;
                AnOtherMessage aom = new AnOtherMessage();
                aom.value = delegate.half(m.value);
                outbox.send(aom);
            }
        }

    }

    public static class MyObject {

        public final Event<Integer> anEvent = new Event<>();

        public void doSomethingWith(String value) {
            System.out.println(value);
        }

        public int half(int value) {
            return value / 2;
        }

    }

    public static class Message1 {

        public String value;

    }

    public static class Message2 {

        public int value;

    }

    public static class AnOtherMessage {

        public int value;

    }

}
