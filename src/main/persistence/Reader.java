package persistence;

import model.ScheduleForDay;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// CITATION: Adapted Reader class from tellerApp (example personal project)

// A class to read and parse ScheduleForDay object data from save file.
public class Reader {

    public static final String DELIMITER = ",";     // CITATION: taken from tellerApp
    public static final String TASK_LINE_IDENTIFIER = "#";

    // EFFECTS: Constructs a reader object
    public Reader() {}

    // EFFECTS: Returns an array list of ScheduleForDay objects with daily schedules read from the save file;
    //          throws IOException if an exception is raised when opening / reading from file.
    public static ArrayList<ScheduleForDay> readDaySchedules(File file) throws IOException {
        List<String> lineStrings = readFile(file);

        ArrayList<ScheduleForDay> schedules = new ArrayList<ScheduleForDay>();
        LinkedList<Task> tasks = new LinkedList<Task>();
        for (String i: lineStrings) {
            if (splitString(i).get(0).equals(TASK_LINE_IDENTIFIER)) { // determine if line of save file is a task line
                tasks.add(parseTaskLine(i));
            } else {
                // add the list of tasks collected to the schedule under which they were listed
                for (Task j: tasks) {
                    schedules.get(schedules.size() - 1).addTask(j);
                }
                // create a new list of tasks
                tasks = new LinkedList<Task>();
                // read the next schedule line
                schedules.add(parseScheduleLine(i));
            }
        }
        // add the final schedule's tasks to it (wasn't done in above for-each loop, since tasks are added to schedule
        // only when the next schedule is 'detected' in the file).
        for (Task j: tasks) {
            schedules.get(schedules.size() - 1).addTask(j);
        }

        return schedules;
    }

    // EFFECTS: returns content of file as a list of strings, each string
    //          containing the content of one row of the file
    // CITATION: taken from tellerApp
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }


    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    // CITATION: taken from tellerApp
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: Split by DELIMITERs, the components of the line must correspond to the following ScheduleForDay data:
    //              element 1 -> year
    //              element 2 -> month
    //              element 3 -> date
    //              element 4 -> number of tasks scheduled
    // EFFECTS: Creates an empty task based on save file line representing a ScheduleForDayObject.
    private static ScheduleForDay parseScheduleLine(String scheduleLine) {
        ArrayList<String> scheduleForDayComponents = splitString(scheduleLine);
        int year = Integer.parseInt(scheduleForDayComponents.get(0));
        int month = Integer.parseInt(scheduleForDayComponents.get(1));
        int date = Integer.parseInt(scheduleForDayComponents.get(2));
        // int numTasksScheduled = Integer.parseInt(scheduleForDayComponents.get(3));
        return new ScheduleForDay(date, month, year);
    }

    // REQUIRES: Split by DELIMITERs, the components of the line must correspond to the following Task data:
    //              element 1 -> #                  (identifies that this is a task line)
    //              element 2 -> task name
    //              element 3 -> task abbreviation
    //              element 4 -> start hour
    //              element 5 -> start minute
    //              element 6 -> finish hour
    //              element 7 -> finish minute
    // EFFECTS: creates a task based save file line representing a Task object.
    private static Task parseTaskLine(String taskLine) {
        ArrayList<String> taskComponents = splitString(taskLine);
        String taskName = taskComponents.get(1);
        int startHour = Integer.parseInt(taskComponents.get(2));
        int startMinute = Integer.parseInt(taskComponents.get(3));
        int finishHour = Integer.parseInt(taskComponents.get(4));
        int finishMinute = Integer.parseInt(taskComponents.get(5));
        return new Task(taskName, startHour, startMinute, finishHour, finishMinute);
    }


}
