package de.muspellheim.actors.example.portals;

import de.muspellheim.events.Event;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SwtAlarmclockDialog extends SwtAlarmclockDialogForm {

    public final Event<LocalTime> onStartRequested = new Event<>();
    public final Event<Void> onStopRequested = new Event<>();

    public SwtAlarmclockDialog() {
        initializeComponent();
    }

    protected void tbSwitchAlarmOnOffClicked(SelectionEvent e) {
        Button tb = (Button) e.getSource();
        if (tb.getText().endsWith("Off")) {
            tb.setText("On");
            if (!remainingTimeLabel.isVisible()) {
                remainingTimeLabel.setVisible(true);
                remainingTimeLabel.setText("");

                onStartRequested.send(LocalTime.parse(wakeupTimeText.getText()));
            }
        } else {
            tb.setText("Off");
            remainingTimeLabel.setVisible(false);
            onStopRequested.send(null);
        }
    }

    protected void dlgAlarmclockFormClosed(ShellEvent e) {
        onStopRequested.send(null);
    }

    public void updateCurrentTime(LocalTime currentTime) {
        currentTimeLabel.setText(currentTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
    }

    public void updateRemainingTime(Duration remainingTime) {
        long hours = remainingTime.getSeconds() / 60 / 60;
        long minutes = remainingTime.getSeconds() / 60 - hours * 60;
        long seconds = remainingTime.getSeconds() - hours * 60 * 60 - minutes * 60;

        remainingTimeLabel.setText(String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds));
    }

    public void wakeupTimeReached() {
        remainingTimeLabel.setVisible(false);
        switchAlarmOnOffButton.setSelection(false);
    }

}
