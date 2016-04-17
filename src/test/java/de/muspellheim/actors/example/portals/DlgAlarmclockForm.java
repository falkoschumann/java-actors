package de.muspellheim.actors.example.portals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class DlgAlarmclockForm extends JFrame {

    protected JLabel lblCurrentTime;
    protected JLabel lblRemainingTime;
    protected JLabel lblWakeupTime;
    protected JTextField txtWakeupTime;
    protected JToggleButton tbSwitchAlarmOnOff;

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

        lblCurrentTime = new JLabel("17:26:34");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        container.add(lblCurrentTime, constraints);

        lblRemainingTime = new JLabel("00:33:26");
        lblRemainingTime.setForeground(Color.RED);
        lblRemainingTime.setVisible(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        container.add(lblRemainingTime, constraints);

        lblWakeupTime = new JLabel("Wakeup time:");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        container.add(lblWakeupTime, constraints);

        txtWakeupTime = new JTextField("18:00");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        container.add(txtWakeupTime, constraints);

        tbSwitchAlarmOnOff = new JToggleButton("Off");
        tbSwitchAlarmOnOff.addActionListener(e -> tbSwitchAlarmOnOffClicked(e));
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        container.add(tbSwitchAlarmOnOff, constraints);

        setVisible(true);
    }

    protected abstract void tbSwitchAlarmOnOffClicked(ActionEvent e);

    protected abstract void dlgAlarmclockFormClosed(WindowEvent e);

}
