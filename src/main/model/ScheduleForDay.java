// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.LinkedList;

// Represents the schedule of a day
public class ScheduleForDay implements Saveable {

    private LinkedList<Task> shapeOfTheDay; // A list of Tasks scheduled
    private int numTasksScheduled;          // The number of tasks scheduled in the day
    private int date;                       // The date of the day represented by a ScheduleOfDay object
    private int month;                      // The month of the day represented by a ScheduleOfDay object
    private int year;                       // The year of the day represented by a ScheduleOfDay object

    // REQUIRES: year >= 0, 1 <= month <= 12,
    //           if month is 1, 3, 5, 7, 8, 10, or 12
    //              1 <= date <= 31
    //           else if month == 2 and year is a leap year
    //              1 <= date <= 29
    //           else if month == 2 and year is not a leap year
    //              1 <= date <= 28
    //           else
    //              1 <= date <= 30
    // EFFECTS: Creates an empty schedule for day represented by date, month, and year.
    public ScheduleForDay(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
        numTasksScheduled = 0;
        shapeOfTheDay = new LinkedList<Task>();
    }

    // MODIFIES: this
    // EFFECTS: If taskToAdd is not already scheduled, and does not overlap with another task,
    //              adds taskToAdd to the day's schedule, increments numTasksScheduled, then returns true
    //          else returns false
    public boolean addTask(Task taskToAdd) {
        if (!contains(taskToAdd)) {
            if (getTaskScheduledAtTime(taskToAdd.getStartHour(), taskToAdd.getStartMinute()) != null
                    || getTaskScheduledAtTime(taskToAdd.getFinishHour(), taskToAdd.getFinishMinute()) != null) {
                return false;
            }
            shapeOfTheDay.add(taskToAdd);
            numTasksScheduled++;
            return true;
        }
        return false;
    }

    // REQUIRES: Schedule is not empty.
    // MODIFIES: this
    // EFFECTS: If requestedTaskToRemove is scheduled
    //              Removes taskToRemove from schedule, decrements numTasksScheduled then returns true
    //          else returns false
    public boolean removeTask(String requestedTaskToRemove) {
        Task taskToRemove = null;
        for (Task i: shapeOfTheDay) {
            if (i.getTaskName().equals(requestedTaskToRemove)) {
                taskToRemove = i;
            }
        }
        if (taskToRemove != null) {
            shapeOfTheDay.remove(taskToRemove);
            numTasksScheduled--;
            return true;
        }
        return false;
    }

    // EFFECTS: Returns true if task is in schedule.
    //          Else returns false.
    public boolean contains(Task task) {
        return shapeOfTheDay.contains(task);
    }

    // EFFECTS: Returns true if there are no tasks scheduled
    //          Else returns false.
    public boolean isEmpty() {
        return shapeOfTheDay.isEmpty();
    }

    // getters

    // EFFECTS: Returns task scheduled at time hour:minute.
    //          If no tasks are scheduled at time hour:minute, return null.
    public Task getTaskScheduledAtTime(int hour, int minute) {
        int timeInMinutes = (hour * 60) + minute;
        for (Task i: shapeOfTheDay) {
            int startTimeInMinutes = (i.getStartHour() * 60) + i.getStartMinute();
            int endTimeInMinutes = (i.getFinishHour() * 60) + i.getFinishMinute();
            if ((timeInMinutes >= startTimeInMinutes) && (timeInMinutes <= endTimeInMinutes)) {
                return i;
            }
        }
        return null;
    }

    // EFFECTS: Returns today's schedule.
    public LinkedList<Task> getShapeOfTheDay() {
        return shapeOfTheDay;
    }

    // EFFECTS: Returns the number of tasks scheduled during the day.
    public int getNumTasksScheduled() {
        return  numTasksScheduled;
    }

    // EFFECTS: Returns the date of the day the ScheduleForDay object represents.
    public int getDate() {
        return date;
    }

    // EFFECTS: Returns the month of the day the ScheduleForDay object represents.
    public int getMonth() {
        return month;
    }

    // EFFECTS: Returns the year of the day the ScheduleForDay object represents.
    public int getYear() {
        return year;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(year);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(month);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(date);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(numTasksScheduled);
        for (Task i: shapeOfTheDay) {
            printWriter.print(Reader.TASK_LINE_IDENTIFIER);
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.getTaskName());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.getStartHour());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.getStartMinute());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.getFinishHour());
            printWriter.print(Reader.DELIMITER);
            printWriter.println(i.getFinishMinute());
        }
    }
}
