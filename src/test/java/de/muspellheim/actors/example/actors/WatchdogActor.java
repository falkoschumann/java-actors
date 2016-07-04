/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors.example.actors;

import de.muspellheim.actors.Actor;
import de.muspellheim.actors.example.Watchdog;
import de.muspellheim.actors.example.actors.messages.*;

public class WatchdogActor extends Actor {

    private final Watchdog dog;

    public WatchdogActor(Watchdog dog) {
        super("Watchdog Actor");
        this.dog = dog;

        dog.onRemainingTime.addHandler(d -> {
            RemainingTimeEvent e = new RemainingTimeEvent();
            e.remainingTime = d;
            outbox.send(e);
        });
        dog.onWakeuptimeDiscovered.addHandler(v -> {
            WakeupTimeDiscoveredEvent e = new WakeupTimeDiscoveredEvent();
            outbox.send(e);
        });
    }

    @Override
    protected void work(Object message) {
        if (message instanceof StartCommand) {
            StartCommand cmd = (StartCommand) message;
            dog.startWatchingFor(cmd.wakeupTime);
        } else if (message instanceof StopCommand) {
            dog.stopWatching();
        } else if (message instanceof CurrentTimeEvent) {
            CurrentTimeEvent e = (CurrentTimeEvent) message;
            dog.check(e.currentTime);
        }
    }

}
