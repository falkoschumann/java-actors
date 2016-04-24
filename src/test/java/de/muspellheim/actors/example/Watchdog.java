/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example;

import de.muspellheim.events.Event;

import java.time.Duration;
import java.time.LocalTime;

public class Watchdog {

    public final Event<Duration> onRemainingTime = new Event<>();
    public final Event<Void> onWakeuptimeDiscovered = new Event<>();

    private boolean watching;
    private LocalTime wakeuptime;

    public void startWatchingFor(LocalTime wakeuptime) {
        this.wakeuptime = wakeuptime;
        watching = true;
    }

    public void stopWatching() {
        watching = false;
    }

    public void check(LocalTime currentTime) {
        if (watching) {
            Duration remainingTime = Duration.between(currentTime, wakeuptime);
            if (remainingTime.isNegative()) {
                watching = false;
                onRemainingTime.send(Duration.ZERO);
                onWakeuptimeDiscovered.send(null);
            } else {
                onRemainingTime.send(remainingTime);
            }
        }
    }

}
