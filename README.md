[![Build Status](https://travis-ci.org/falkoschumann/java-actors.svg?branch=master)](https://travis-ci.org/falkoschumann/java-actors)
[![Build Status](https://api.bintray.com/packages/falkoschumann/maven/actors/images/download.svg)](https://bintray.com/falkoschumann/maven/actors)

Actors
======

A simple actor model implementation.

Introduction
------------

In the actor model, communication between objects uses only messages. Every
object, also known as actor, has an inbox queue for messages received. This
queue is worked asynchronously in a thread owned by the actor.

Read more on [Wikipedia](https://en.wikipedia.org/wiki/Actor_model).


Usage
-----

It is a good practice to have an synchron and easy testable class, e.g. a POJO.

    public class MyObject {

        public final Event<Integer> anEvent = new Event<>();

        public void doSomethingWith(String value) {
            System.out.println(value);
        }

        public int half(int value) {
            return value / 2;
        }

    }

This class is used as delegate by an actor.

    public class MyActor extends Actor {

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

The `work` method process a message in the inbox queue of the actor.

The POJO use values as method parameter and return values or for events. Instead
the actor use messages wrapping this values. This Messages are used to
distinguish the messages in the `work` method. To link the object events with
other actors, its are wrapped also as events in the actor constructor.

Actors are created with its delegate.

    MyObject object1 = new MyObject();
    MyActor actor1 = new MyActor(object1);

A message is put in the inbox queue of an actor with the `receive` method.

    Message1 message = new Message1();
    actor1.receive(message);

Actors are connected each other by connecting the outbox with the inbox
`receive` method.

    actor1.outbox.addHandler(m -> actor2.receive(m));
