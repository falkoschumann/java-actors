[![Build Status](https://travis-ci.org/falkoschumann/java-actors.svg?branch=master)](https://travis-ci.org/falkoschumann/java-actors)
[![GitHub release](https://img.shields.io/github/release/falkoschumann/java-actors.svg)]()


Actors
======

A simple actor model implementation.

In the actor model, communication between objects uses only messages. Every
object, also known as actor, has an inbox queue for messages received. This
queue is worked asynchronously in a thread owned by the actor.

Read more on [Wikipedia][1].


Installation
------------

### Gradle

Add the the repository _jcenter_ to your `build.gradle`

    repositories {
        jcenter()
    }

and add the dependency

    compile 'de.muspellheim:actors:1.1.0'


### Maven

Add the the repository _jcenter_ to your `pom.xml`
    
    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>central</id>
            <name>bintray</name>
            <url>http://jcenter.bintray.com</url>
        </repository>
    </repositories>

and add the dependency

    <dependencies>
        <dependency>
            <groupId>de.muspellheim</groupId>
            <artifactId>actors</artifactId>
            <version>1.1.0</version>
        </dependency>
    </dependencies>


### Download

You can download JARs with binary, source and JavaDoc from GitHub under
https://github.com/falkoschumann/java-actors/releases.


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

The most UI frameworks are not synchronized and all interaction with widgets
must be processed in UI thread. The classes `JavaFxActor`, `SwingActor` and
`SwtActor` process all messagess in the UI thread of the framework. An UI actor
wrap an controller class and delegate all request. There are no AWT actor,
because AWT is synchronized.

A complete application example you can found in test source in package
`de.muspellheim.actors.example`. It based on this [Blog][2] and include three
main classes.

*   `JavaFxProgramm`
*   `SwingProgramm`
*   `SwtProgramm`


Contributing
------------

### Publish artifacts to Bintray

1.  Create file `gradle.properties` and set properties `bintrayUser` and
    `bintrayApiKey`.
2.  Run `./gradlew uploadArchives`.
3.  Check uploaded files and publish.

### Publish distribution to GitHub

1.  Run `./gradle distZip`.
2.  Upload created ZIP to GitHub releases.


[1]: https://en.wikipedia.org/wiki/Actor_model
[2]: http://geekswithblogs.net/theArchitectsNapkin/archive/2015/05/12/actors-in-a-ioda-architecture-by-example.aspx
