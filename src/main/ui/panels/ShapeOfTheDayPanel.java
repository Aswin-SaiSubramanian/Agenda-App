// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package ui.panels;

import model.ScheduleForDay;
import model.Task;
import ui.ScheduleViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// CITATION:
// referred to https://www.youtube.com/watch?v=YKaea4ezQQE for using Java Swing and GridBagLayout
// (channel: Cave Of Programming | video: "Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout")

// This class represents the window that will show the schedule for the day
public class ShapeOfTheDayPanel extends JPanel implements ActionListener {
    private ScheduleViewer sv;
    private ArrayList<JButton> taskButtons;
    private JPanel previousTaskDisplayed;
    private JFrame parentWindow;

    public ShapeOfTheDayPanel(ScheduleViewer sv, JFrame parentWindow) {
        this.sv = sv;
        this.parentWindow = parentWindow;

        // set layout
        setLayout(new GridBagLayout());

        // create components
        ArrayList<JLabel> timeLabels = timeLabelsInit();
        taskButtons = taskButtonsInit();

        // add components
        addComponents(timeLabels, taskButtons);
    }

    // EFFECTS: Initializes and returns a list of labels representing the hours in the day
    private ArrayList<JLabel> timeLabelsInit() {
        ArrayList<JLabel> hourLabels = new ArrayList<>();
        for (Task i: sv.getDaySchedule().getShapeOfTheDay()) {
            Integer hour = new Integer(i.getStartHour());
            Integer minute = new Integer(i.getStartMinute());
            hourLabels.add(singleTimeLabelInit(hour.toString(), minute.toString()));
        }
        return hourLabels;
    }

    // EFFECTS: Initializes and returns a label representing an hour in the day
    private JLabel singleTimeLabelInit(String hour, String minute) {
        JLabel hourLabel = new JLabel(hour + ":" + minute);
        return hourLabel;
    }

    // EFFECTS: Initializes and returns a list of buttons representing the tasks scheduled during the day
    private ArrayList<JButton> taskButtonsInit() {
        ArrayList<JButton> taskButtonList = new ArrayList<>();
        for (Task i: sv.getDaySchedule().getShapeOfTheDay()) {
            taskButtonList.add(singleTaskButtonInit(i.getTaskName()));
        }
        return taskButtonList;
    }

    // EFFECTS: Initializes and return a single task scheduled during the day
    private JButton singleTaskButtonInit(String taskName) {
        JButton task = new JButton(taskName);
        task.setActionCommand(taskName);
        task.addActionListener(this);
        return task;
    }

    // MODIFIES: this
    // EFFECTS: adds the created components to this shape of the day panel
    private void addComponents(ArrayList<JLabel> timeLabel, ArrayList<JButton> taskButtons) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        if (sv.getDaySchedule().getNumTasksScheduled() != 0) {
            addHeadings(gc);

            // add start time labels
            gc.anchor = GridBagConstraints.EAST;
            gc.gridx = 0;
            gc.gridy = 2;
            for (JLabel i: timeLabel) {
                this.add(i, gc);
                gc.gridy++;
            }

            // add task buttons (aligned with their start times)
            gc.anchor = GridBagConstraints.CENTER;
            gc.gridx = 1;
            gc.gridy = 2;
            for (JButton i: taskButtons) {
                this.add(i, gc);
                gc.gridy++;
            }
        } else {
            this.add(new JLabel("At this time, there are no tasks scheduled today."), gc);
        }

    }

    // EFFECTS: Add the necessary headings to the shape of the day panel
    private void addHeadings(GridBagConstraints gc) {
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.gridy = 0;
        gc.gridy = 0;
        this.add(new JLabel("Shape of the Day"), gc);

        gc.anchor = GridBagConstraints.EAST;
        gc.gridy = 1;
        this.add(new JLabel("Start Time"), gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        this.add(new JLabel("Task"), gc);
    }

    // EFFECTS: when the "remove" button in TaskPanel is clicked,
    //          the associated task button in this ShapeOfTheDayPanel will be disabled
    public void removeTask(String taskName) {
        for (JButton i: taskButtons) {
            if (i.getText() == taskName) {
                i.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        previousTaskDisplayed = sv.displayTask(parentWindow, this, previousTaskDisplayed, e.getActionCommand());
    }
}
