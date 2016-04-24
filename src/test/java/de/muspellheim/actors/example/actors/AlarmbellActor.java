/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example.actors;

import de.muspellheim.actors.Actor;
import de.muspellheim.actors.example.actors.messages.WakeupTimeDiscoveredEvent;
import de.muspellheim.actors.example.providers.Alarmbell;

public class AlarmbellActor extends Actor {

    private Alarmbell bell;

    public AlarmbellActor(Alarmbell bell) {
        super("Alarmbell Actor");
        this.bell = bell;
    }

    @Override
    protected void work(Object message) {
        if (message instanceof WakeupTimeDiscoveredEvent) {
            bell.ring();
        }
    }

}
