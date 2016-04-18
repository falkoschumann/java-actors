package de.muspellheim.actors.example.actors;

import de.muspellheim.actors.SwtActor;
import de.muspellheim.actors.example.actors.messages.*;
import de.muspellheim.actors.example.portals.SwtDlgAlarmclock;

public class SwtDlgActor extends SwtActor {

    private SwtDlgAlarmclock dlg;

    public SwtDlgActor(SwtDlgAlarmclock dlg) {
        this.dlg = dlg;

        dlg.onStartRequested.addHandler(t -> {
            StartCommand e = new StartCommand();
            e.wakeupTime = t;
            event.send(e);
        });
        dlg.onStopRequested.addHandler(v -> {
            StopCommand e = new StopCommand();
            event.send(e);
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
