package de.muspellheim.actors.example.portals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class SwingAlarmclockDialogForm extends JFrame {

    protected JLabel currentTimeLabel;
    protected JLabel remainingTimeLabel;
    protected JLabel wakeupTimeLabel;
    protected JTextField wakeupTimeText;
    protected JToggleButton switchAlarmOnOffButton;

    public void initializeComponent() {
        setTitle("Alarm Clock");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                dlgAlarmclockFormClosed(e);
            }

        });

        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        getContentPane().add(container);

        currentTimeLabel = new JLabel("17:26:34");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        container.add(currentTimeLabel, constraints);

        remainingTimeLabel = new JLabel("00:33:26");
        remainingTimeLabel.setForeground(Color.RED);
        remainingTimeLabel.setVisible(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        container.add(remainingTimeLabel, constraints);

        wakeupTimeLabel = new JLabel("Wakeup time:");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        container.add(wakeupTimeLabel, constraints);

        wakeupTimeText = new JTextField("18:00");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        container.add(wakeupTimeText, constraints);

        switchAlarmOnOffButton = new JToggleButton("Off");
        switchAlarmOnOffButton.addActionListener(e -> tbSwitchAlarmOnOffClicked(e));
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        container.add(switchAlarmOnOffButton, constraints);

        setVisible(true);
    }

    protected abstract void tbSwitchAlarmOnOffClicked(ActionEvent e);

    protected abstract void dlgAlarmclockFormClosed(WindowEvent e);

}
