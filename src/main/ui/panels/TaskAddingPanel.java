// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package ui.panels;

import model.ScheduleForDay;
import model.Task;
import ui.ScheduleViewer;
import ui.panels.listeners.TaskNameListener;
import ui.panels.listeners.TaskTimingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// CITATION:
// referred to https://www.youtube.com/watch?v=YKaea4ezQQE for using Java Swing and GridBagLayout
// (channel: Cave Of Programming | video: "Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout")

// a panel that constructs and adds a task to the schedule of a day,
// based on user input for task name, and start and finish times.
public class TaskAddingPanel  extends JPanel implements ActionListener {
    private static int MINUTES_IN_AN_HOUR = 60;
    private static int HOURS_IN_A_DAY = 24;

    private ScheduleViewer sv;

    private JTextField taskName;
    private JButton addTaskButton;

    private TaskNameListener taskNameListener;
    private ArrayList<JLabel> taskTimingLabels;

    private TaskTimingListener startHourListener;
    private TaskTimingListener startMinuteListener;
    private TaskTimingListener endHourListener;
    private TaskTimingListener endMinuteListener;

    // EFFECTS: Constructs a panel that creates and adds a task to the schedule of a day
    public TaskAddingPanel(ScheduleViewer sv) {
        taskName = new JTextField("Enter the name of the task, and press enter");
        init(taskName, sv);

        // set layout
        this.setLayout(new GridBagLayout());

        // create components
        JLabel labelTaskName = new JLabel("Task Name: ");

        JMenuBar taskTimingMenuBar = taskTimingMenuBarInit();

        addTaskButton = new JButton("Add task!");
        addTaskButton.addActionListener(this);

        // add components
        addComponents(labelTaskName, taskTimingMenuBar, taskName, addTaskButton);
    }

    // EFFECTS: Initializes the class's fields
    private void init(JTextField enterTaskName, ScheduleViewer sv) {
        this.sv = sv;

        taskNameListener = new TaskNameListener(enterTaskName);
        enterTaskName.addActionListener(taskNameListener);

        taskTimingLabels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            taskTimingLabels.add(new JLabel());
        }

        startHourListener = new TaskTimingListener(true, true, taskTimingLabels.get(0));
        startMinuteListener = new TaskTimingListener(true, false, taskTimingLabels.get(1));
        endHourListener = new TaskTimingListener(false, true, taskTimingLabels.get(2));
        endMinuteListener = new TaskTimingListener(false, false, taskTimingLabels.get(3));
    }

    // EFFECTS: initializes and returns a menu bar that contains menus for user to indicate task timing.
    private JMenuBar taskTimingMenuBarInit() {
        JMenuBar taskTimingMenuBar = new JMenuBar();
        addTaskTimingMenu(taskTimingMenuBar, "Start Hour", startHourListener);
        addTaskTimingMenu(taskTimingMenuBar, "Start Minute", startMinuteListener);
        addTaskTimingMenu(taskTimingMenuBar, "End Hour", endHourListener);
        addTaskTimingMenu(taskTimingMenuBar, "End Minute", endMinuteListener);
        return taskTimingMenuBar;
    }

    // EFFECTS: adds a task timing menu to a task timing menu bar
    private void addTaskTimingMenu(JMenuBar menuBar, String menuName, TaskTimingListener actionListener) {
        JMenu menu = new JMenu(menuName);
        int timeUnits;
        int timeIncrement = 1;
        if (actionListener == startHourListener || actionListener == endHourListener) {
            timeUnits = HOURS_IN_A_DAY;
        } else {
            timeUnits = MINUTES_IN_AN_HOUR;
            timeIncrement = 10;
        }
        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < timeUnits; i += timeIncrement) {
            String timeUnitsPassed = (new Integer(i)).toString();
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(timeUnitsPassed);
            menuItem.setActionCommand(timeUnitsPassed);
            menuItem.addActionListener(actionListener);

            bg.add(menuItem);
            menu.add(menuItem);
        }
        menuBar.add(menu);
    }

    // MODIFIES: this
    // EFFECTS: adds the components to their appropriate locations in this panel
    private void addComponents(JLabel labelTaskName,
                               JMenuBar taskTimingMenuBar,
                               JTextField taskName, JButton addTaskButton) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        // add task name entry label and field
        addTaskNameEntryComponents(labelTaskName, taskName, gc);

        // add components related to task timing
        gc.gridx++;
        gc.gridy = 0;
        this.add(taskTimingMenuBar, gc);

        JPanel taskTimingDisplay = createTaskTimingDisplay();
        gc.gridy++;
        this.add(taskTimingDisplay, gc);

        // add the "Add task" button
        gc.gridy++;
        gc.gridx = 0;
        this.add(addTaskButton, gc);
    }

    // EFFECTS: creates and returns a panel to display the task timings chosen
    private JPanel createTaskTimingDisplay() {
        JPanel taskTimingDisplay = new JPanel();

        // set layout
        taskTimingDisplay.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.5;
        gc.weighty = 0.5;

        // add components
        gc.gridx = 0;
        gc.gridy = 0;
        taskTimingDisplay.add(taskTimingLabels.get(0), gc);
        gc.gridx++;
        taskTimingDisplay.add(taskTimingLabels.get(1), gc);
        gc.gridx++;
        taskTimingDisplay.add(taskTimingLabels.get(2), gc);
        gc.gridx++;
        taskTimingDisplay.add(taskTimingLabels.get(3), gc);

        return taskTimingDisplay;
    }

    // EFFECTS: adds to this panel, the components related to entering the name of the new task
    private void addTaskNameEntryComponents(JLabel label, JTextField taskName, GridBagConstraints gc) {
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(label, gc);

        gc.gridy++;
        this.add(taskName, gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Task task = new Task(taskNameListener.getTaskName(),
                startHourListener.getTaskTiming(), startMinuteListener.getTaskTiming(),
                endHourListener.getTaskTiming(), endMinuteListener.getTaskTiming());
        sv.getDaySchedule().addTask(task);

        // reset the text field
        taskName.setText("Enter the name of the task, and press enter");
        taskName.setEnabled(true);
    }
}
