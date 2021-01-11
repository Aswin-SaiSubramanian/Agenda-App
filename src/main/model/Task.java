package model;

import java.time.LocalDate;

// Represents a single task, with an identifying name, start time, and end time.
public class Task {

    private String taskName;
    private char taskAbbreviation;  // used for displaying the task in a schedule
    private int startHour;
    private int startMinute;
    private int finishHour;
    private int finishMinute;


    // REQUIRES: startHour and endHour between 0 and 23, inclusive.
    //           startMinute and endMinute between 0 and 59, inclusive.
    //           (startHour != endHour) && (startMinute != endMinute).
    // EFFECTS: Creates a new task with name taskName, and start and end times.
    //            Note: The start and end times are included in the task's duration
    public Task(String taskName, int startHour, int startMinute, int finishHour, int finishMinute) {
        this.taskName = taskName;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.finishHour = finishHour;
        this.finishMinute = finishMinute;
    }

    // getters

    // EFFECTS: Returns the task's start hour
    public int getStartHour() {
        return startHour; // stub
    }

    // EFFECTS: Returns the task's start minute
    public int getStartMinute() {
        return startMinute;
    }

    // EFFECTS: Returns the task's finish hour
    public int getFinishHour() {
        return finishHour;
    }

    // EFFECTS: Returns the task's finish time
    public int getFinishMinute() {
        return finishMinute;
    }

    // EFFECTS: Returns the name of the task
    public String getTaskName() {
        return taskName;
    }

    // EFFECTS: Returns the taskAbbreviation
    public char getTaskAbbreviation() {
        return taskAbbreviation;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: Assigns the task an abbreviation character by which to identify it
    public void setTaskAbbreviation(char abbreviation) {
        taskAbbreviation = abbreviation;
    }
}
