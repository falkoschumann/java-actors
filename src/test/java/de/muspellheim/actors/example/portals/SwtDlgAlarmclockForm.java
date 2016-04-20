package de.muspellheim.actors.example.portals;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public abstract class SwtDlgAlarmclockForm {

    protected Label lblCurrentTime;
    protected Label lblRemainingTime;
    protected Label lblWakeupTime;
    protected Text txtWakeupTime;
    protected Button tbSwitchAlarmOnOff;

    private Display display;
    private Shell shell;

    public void initializeComponent() {
        display = new Display();
        shell = new Shell(display);
        shell.setSize(300, 200);
        shell.setText("Alarm Clock");
        shell.setLayout(new FillLayout());
        shell.addShellListener(new ShellAdapter() {

            @Override
            public void shellClosed(ShellEvent e) {
                dlgAlarmclockFormClosed(e);
            }

        });

        Composite container = new Composite(shell, SWT.NONE);
        container.setLayout(new GridLayout(3, true));

        lblCurrentTime = new Label(container, SWT.NONE);
        lblCurrentTime.setText("17:26:34");
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(lblCurrentTime);

        lblRemainingTime = new Label(container, SWT.NONE);
        lblRemainingTime.setText("00:33:26");
        lblRemainingTime.setForeground(container.getDisplay().getSystemColor(SWT.COLOR_RED));
        lblRemainingTime.setVisible(false);
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(lblRemainingTime);

        lblWakeupTime = new Label(container, SWT.NONE);
        lblWakeupTime.setText("Wakeup time:");
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(lblWakeupTime);

        txtWakeupTime = new Text(container, SWT.BORDER);
        txtWakeupTime.setText("18:00");
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(txtWakeupTime);

        tbSwitchAlarmOnOff = new Button(container, SWT.TOGGLE);
        tbSwitchAlarmOnOff.setText("Off");
        tbSwitchAlarmOnOff.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                tbSwitchAlarmOnOffClicked(e);
            }

        });
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(tbSwitchAlarmOnOff);
    }

    public void open() {
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }

    protected abstract void tbSwitchAlarmOnOffClicked(SelectionEvent e);

    protected abstract void dlgAlarmclockFormClosed(ShellEvent e);

}
