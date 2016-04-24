/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example.portals;

import de.muspellheim.events.Event;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    public final Event<LocalTime> onCurrentTime = new Event<>();

    private Timer timer;

    public Clock() {
        timer = new Timer("Clock", true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                onCurrentTime.send(LocalTime.now());
            }

        }, 0, 1000);
    }

}
