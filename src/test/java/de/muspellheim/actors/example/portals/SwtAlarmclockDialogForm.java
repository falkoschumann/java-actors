/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

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

public abstract class SwtAlarmclockDialogForm {

    protected Label currentTimeLabel;
    protected Label remainingTimeLabel;
    protected Label wakeupTimeLabel;
    protected Text wakeupTimeText;
    protected Button switchAlarmOnOffButton;

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

        currentTimeLabel = new Label(container, SWT.NONE);
        currentTimeLabel.setText("17:26:34");
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(currentTimeLabel);

        remainingTimeLabel = new Label(container, SWT.NONE);
        remainingTimeLabel.setText("00:33:26");
        remainingTimeLabel.setForeground(container.getDisplay().getSystemColor(SWT.COLOR_RED));
        remainingTimeLabel.setVisible(false);
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(3, 1).applyTo(remainingTimeLabel);

        wakeupTimeLabel = new Label(container, SWT.NONE);
        wakeupTimeLabel.setText("Wakeup time:");
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(wakeupTimeLabel);

        wakeupTimeText = new Text(container, SWT.BORDER);
        wakeupTimeText.setText("18:00");
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(wakeupTimeText);

        switchAlarmOnOffButton = new Button(container, SWT.TOGGLE);
        switchAlarmOnOffButton.setText("Off");
        switchAlarmOnOffButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                tbSwitchAlarmOnOffClicked(e);
            }

        });
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(switchAlarmOnOffButton);
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
