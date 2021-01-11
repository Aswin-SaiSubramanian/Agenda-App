package ui.panels.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// listens for the task timing specifying buttons in TaskAddingPanels, and sets the start hour
public class TaskTimingListener implements ActionListener {
    private int taskTiming; // represents the start or end, hour or minute
    private JLabel feedback;    // displays the recorded time
    private boolean isStartTiming; // record whether taskTiming represents a start or an end time
    private boolean isHour; // records wither taskTiming represents an hour or a minute
    private ArrayList<JRadioButton> affectedTimingButtons;

    public TaskTimingListener(boolean isStartTiming, boolean isHour, JLabel feedback) {
        this.isStartTiming = isStartTiming;
        this.isHour = isHour;
        this.feedback = feedback;
    }

    public int getTaskTiming() {
        return taskTiming;
    }


    @Override
    // REQUIRES: the action event being listened to, is parseable as an integer
    // MODIFIES: this, TaskAddingPanel
    // EFFECTS: - records the timing of a task
    public void actionPerformed(ActionEvent e) {
        taskTiming = Integer.parseInt(e.getActionCommand());
        feedback.setText(e.getActionCommand() + " ");
    }
}