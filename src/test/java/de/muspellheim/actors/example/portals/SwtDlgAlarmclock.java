package de.muspellheim.actors.example.portals;

import de.muspellheim.events.Event;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SwtDlgAlarmclock extends SwtDlgAlarmclockForm {

    public final Event<LocalTime> onStartRequested = new Event<>();
    public final Event<Void> onStopRequested = new Event<>();

    public SwtDlgAlarmclock() {
        initializeComponent();
    }

    protected void tbSwitchAlarmOnOffClicked(SelectionEvent e) {
        Button tb = (Button) e.getSource();
        if (tb.getText().endsWith("Off")) {
            tb.setText("On");
            if (!lblRemainingTime.isVisible()) {
                lblRemainingTime.setVisible(true);
                lblRemainingTime.setText("");

                onStartRequested.send(LocalTime.parse(txtWakeupTime.getText()));
            }
        } else {
            tb.setText("Off");
            lblRemainingTime.setVisible(false);
            onStopRequested.send(null);
        }
    }

    protected void dlgAlarmclockFormClosed(ShellEvent e) {
        onStopRequested.send(null);
    }

    public void updateCurrentTime(LocalTime currentTime) {
        lblCurrentTime.setText(currentTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
    }

    public void updateRemainingTime(Duration remainingTime) {
        long hours = remainingTime.getSeconds() / 60 / 60;
        long minutes = remainingTime.getSeconds() / 60 - hours * 60;
        long seconds = remainingTime.getSeconds() - hours * 60 * 60 - minutes * 60;

        lblRemainingTime.setText(String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds));
    }

    public void wakeupTimeReached() {
        lblRemainingTime.setVisible(false);
        tbSwitchAlarmOnOff.setSelection(false);
    }

}
