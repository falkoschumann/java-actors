/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example.portals;

import de.muspellheim.events.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class JavaFxAlarmclockDialog {

    public final Event<LocalTime> onStartRequested = new Event<>();
    public final Event<Void> onStopRequested = new Event<>();

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label remainingTimeLabel;

    @FXML
    private Label wakeupTimeLabel;

    @FXML
    private TextField wakeupTimeText;

    @FXML
    private ToggleButton switchAlarmOnOffButton;

    @FXML
    private void switchAlarmOnOffClicked() {
        if (switchAlarmOnOffButton.getText().endsWith("Off")) {
            switchAlarmOnOffButton.setText("On");
            if (!remainingTimeLabel.isVisible()) {
                remainingTimeLabel.setVisible(true);
                remainingTimeLabel.setText("");

                onStartRequested.send(LocalTime.parse(wakeupTimeText.getText()));
            }
        } else {
            switchAlarmOnOffButton.setText("Off");
            remainingTimeLabel.setVisible(false);
            onStopRequested.send(null);
        }
    }

    @FXML
    private void dlgAlarmclockClosed() {
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
        switchAlarmOnOffButton.setSelected(false);
    }

}
