package ui.panels.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// listens for the task name text field in TaskAddingPanel
public class TaskNameListener implements ActionListener {
    private String taskName;
    private JTextField enterTaskName;

    // EFFECTS: constructs a listener for the task name field in TaskAddingPanel
    public TaskNameListener(JTextField enterTaskName) {
        this.enterTaskName = enterTaskName;
    }

    public String getTaskName() {
        return taskName;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: records the name of a task to be add to the schedule of a day
    public void actionPerformed(ActionEvent e) {
        taskName = enterTaskName.getText();
        enterTaskName.setEnabled(false);
    }
}
