package de.muspellheim.actors.example.portals;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public abstract class SwtDlgAlarmclockForm extends Composite {

    protected Label lblCurrentTime;
    protected Label lblRemainingTime;
    protected Label lblWakeupTime;
    protected Text txtWakeupTime;
    protected Button tbSwitchAlarmOnOff;

    public SwtDlgAlarmclockForm(Composite composite, int style) {
        super(composite, style);
    }

    public void initializeComponent() {
        setLayout(new GridLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                dlgAlarmclockFormClosed(e);
            }

        });

        lblCurrentTime = new Label(this, SWT.NONE);
        lblCurrentTime.setText("17:26:34");
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(lblCurrentTime);

        lblRemainingTime = new Label(this, SWT.NONE);
        lblRemainingTime.setText("00:33:26");
        lblRemainingTime.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
        lblRemainingTime.setVisible(false);
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(lblRemainingTime);

        lblWakeupTime = new Label(this, SWT.NONE);
        lblWakeupTime.setText("Wakeup time:");
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(lblWakeupTime);

        txtWakeupTime = new Text(this, SWT.BORDER);
        txtWakeupTime.setText("18:00");
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).applyTo(txtWakeupTime);

        tbSwitchAlarmOnOff = new Button(this, SWT.TOGGLE);
        tbSwitchAlarmOnOff.setText("Off");
        tbSwitchAlarmOnOff.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                tbSwitchAlarmOnOffClicked(e);
            }

        });
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(txtWakeupTime);

        setVisible(true);
    }

    protected abstract void tbSwitchAlarmOnOffClicked(SelectionEvent e);

    protected abstract void dlgAlarmclockFormClosed(WindowEvent e);

}
