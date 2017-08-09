/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example.actors;

import de.muspellheim.actors.SwtActor;
import de.muspellheim.actors.example.actors.messages.*;
import de.muspellheim.actors.example.portals.SwtAlarmclockDialog;

public class SwtActorDialog extends SwtActor {

    private SwtAlarmclockDialog dlg;

    public SwtActorDialog(SwtAlarmclockDialog dlg) {
        this.dlg = dlg;

        dlg.onStartRequested.addHandler(t -> {
            StartCommand e = new StartCommand();
            e.wakeupTime = t;
            outbox.send(e);
        });
        dlg.onStopRequested.addHandler(v -> {
            StopCommand e = new StopCommand();
            outbox.send(e);
        });
    }

    @Override
    protected void work(Object message) {
        if (message instanceof CurrentTimeEvent) {
            CurrentTimeEvent e = (CurrentTimeEvent) message;
            dlg.updateCurrentTime(e.currentTime);
        } else if (message instanceof RemainingTimeEvent) {
            RemainingTimeEvent e = (RemainingTimeEvent) message;
            dlg.updateRemainingTime(e.remainingTime);
        } else if (message instanceof WakeupTimeDiscoveredEvent) {
            dlg.wakeupTimeReached();
        }
    }

}
