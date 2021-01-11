package ui.panels;

import model.ScheduleForDay;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// CITATION:
// referred to https://www.youtube.com/watch?v=YKaea4ezQQE for using Java Swing and GridBagLayout
// (channel: Cave Of Programming | video: "Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout")

// this class represents the window that shows the task requested, and the option to remove it
public class TaskPanel extends JPanel implements ActionListener {
    private ScheduleForDay daySchedule;
    private JButton removeTaskButton;
    private ShapeOfTheDayPanel parentSchedulePanel;
    private static final int NUMBER_OF_LABELS_WHEN_TASK_EXISTS = 6;


    // EFFECTS: constructs a panel that displays a task, as well as the option to remove it from schedule
    public TaskPanel(ScheduleForDay daySchedule, String taskName, ShapeOfTheDayPanel parentSchedulePanel) {
        this.daySchedule = daySchedule;
        this.parentSchedulePanel = parentSchedulePanel;
        // set layout
        setLayout(new GridBagLayout());

        // create components
        ArrayList<JLabel> labels = new ArrayList<>();
        Task taskToDisplay = findTask(taskName);
        labelInit("Task: ", labels);
        JLabel task = labelInit(taskName, labels);
        if (taskToDisplay != null) {
            labelInit("Start time: ", labels);
            JLabel startHour = labelInit(taskToDisplay.getStartHour() + ":"
                    + taskToDisplay.getStartMinute(), labels);
            labelInit("End time: ", labels);
            JLabel finishHour = labelInit(taskToDisplay.getFinishHour() + ":"
                    + taskToDisplay.getFinishMinute(), labels);
        }
        removeTaskButton = buttonInit("Remove task", Color.RED, taskName);

        // add components
        addComponents(labels);
    }

    // EFFECTS: Initializes, adds to a destination array, and returns a label with specified text
    private JLabel labelInit(String labelText, ArrayList<JLabel> destinationArrayList) {
        JLabel label = new JLabel(labelText);
        destinationArrayList.add(label);
        return label;
    }

    // EFFECTS: Initializes and returns a button with specified text, color, and action command
    private JButton buttonInit(String buttonText, Color color, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.setBackground(color);
        button.setOpaque(true);
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: finds and returns the specified task from the day's schedule
    private Task findTask(String taskName) {
        Task taskToDisplay = null;
        for (Task i: daySchedule.getShapeOfTheDay()) {
            if (i.getTaskName().equals(taskName)) {
                taskToDisplay = i;
            }
        }
        return taskToDisplay;
    }

    // REQUIRES: the labels ArrayList must have element arranged in the order label, value, label, value, etc..
    // MODIFIES: this
    // EFFECTS: adds created components to this panel
    private void addComponents(ArrayList<JLabel> labels) {
        // layout setup
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        // adding components
        addTaskDetailsAndRemoveTaskButton(labels, gc);
    }

    // EFFECTS: adds the selected task's details to the panel
    private void addTaskDetailsAndRemoveTaskButton(ArrayList<JLabel> labels, GridBagConstraints gc) {
        gc.gridx = 0;
        gc.gridy = 0;
        if (labels.size() == NUMBER_OF_LABELS_WHEN_TASK_EXISTS) {
            int labelCount = 0;
            for (JLabel i: labels) {
                if (labelCount % 2 == 0) {
                    gc.gridx = 0;
                    this.add(i, gc);
                } else {
                    gc.gridx = 1;
                    this.add(i, gc);
                    gc.gridy++;
                }

                labelCount++;
            }
            gc.weighty = 10;
            gc.anchor = GridBagConstraints.NORTH;
            this.add(removeTaskButton, gc);
        } else {
            this.add(new JLabel("This task no longer exists."));
            gc.gridy++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        daySchedule.removeTask(e.getActionCommand());
        parentSchedulePanel.removeTask(e.getActionCommand());
        this.setVisible(false);
    }
}
