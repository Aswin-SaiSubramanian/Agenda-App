package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleForDayTest {
    ScheduleForDay testSchedule;
    Task task1;
    Task task2;
    Task task3;


    @BeforeEach
    public void runBefore() {
        testSchedule = new ScheduleForDay(11, 2, 2020);
        task1 = new Task("task1", 0, 0, 1, 0);
        task2 = new Task("task2", 0, 0, 1, 0);
        task3 = new Task("task3", 1, 0, 2, 0);
    }

    @Test
    public void testConstructor() {
        // check date, month, and year
        assertEquals(11, testSchedule.getDate());
        assertEquals(2, testSchedule.getMonth());
        assertEquals(2020, testSchedule.getYear());
        // check that the schedule is empty
        assertEquals(0, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testConstructorDateMonthLowerBound() {
        // create new testSchedule
        testSchedule = new ScheduleForDay(1, 1, 2020);
        // check date, month, and year
        assertEquals(1, testSchedule.getDate());
        assertEquals(1, testSchedule.getMonth());
        assertEquals(2020, testSchedule.getYear());
        // check that the schedule is empty
        assertEquals(0, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testConstructorDateMonthUpperBound() {
        // create new testSchedule
        testSchedule = new ScheduleForDay(31, 12, 2020);
        // check date, month, and year
        assertEquals(31, testSchedule.getDate());
        assertEquals(12, testSchedule.getMonth());
        assertEquals(2020, testSchedule.getYear());
        // check that the schedule is empty
        assertEquals(0, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testAddTaskToEmptySchedule() {
        // add a task
        assertTrue(testSchedule.addTask(task3));
        // Check that the number of tasks scheduled is 1
        assertEquals(1, testSchedule.getNumTasksScheduled());
        // Check that the task is scheduled
        assertTrue(testSchedule.contains(task3));
    }

    @Test
    public void testAddTaskDuplicateTask() {
        //add a task twice
        assertTrue(testSchedule.addTask(task3));
        assertFalse(testSchedule.addTask(task3));
        // Check that the number of tasks scheduled is 1
        assertEquals(1, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testAddTaskCompletelyOverlappingTimes() {
        // add two tasks with completely overlapping times
        assertTrue(testSchedule.addTask(task1));
        assertFalse(testSchedule.addTask(task2));
        // check that the number of tasks scheduled is 1
        assertEquals(1, testSchedule.getNumTasksScheduled());
        // check that the task scheduled is task1
        assertTrue(testSchedule.contains(task1));
        assertFalse(testSchedule.contains(task2));
    }

    @Test
    public void testAddTaskTrailingEdgeOverlappingTimes() {
        // add two tasks with overlapping times
        assertTrue(testSchedule.addTask(task2));
        assertFalse(testSchedule.addTask(task3));
        // check that the number of tasks scheduled is 1
        assertEquals(1, testSchedule.getNumTasksScheduled());
        // check that the task scheduled is task1
        assertTrue(testSchedule.contains(task2));
        assertFalse(testSchedule.contains(task3));
    }

    @Test
    public void testAddTaskLeadingEdgeOverlappingTimes() {
        // add two tasks with overlapping times
        assertTrue(testSchedule.addTask(task3));
        assertFalse(testSchedule.addTask(task2));
        // check that the number of tasks scheduled is 1
        assertEquals(1, testSchedule.getNumTasksScheduled());
        // check that the task scheduled is task1
        assertTrue(testSchedule.contains(task3));
        assertFalse(testSchedule.contains(task2));
    }

    @Test
    public void testRemoveTaskNotScheduled() {
        // no tasks scheduled in runBefore()
        assertFalse(testSchedule.removeTask(task1.getTaskName()));
        // check that the number of tasks scheduled remains 0.
        assertEquals(0, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testRemoveTaskScheduled() {
        // Schedule task3
        testSchedule.addTask(task3);
        // Remove task3
        assertTrue(testSchedule.removeTask(task3.getTaskName()));
        // check that the number of tasks scheduled remains 0
        assertEquals(0, testSchedule.getNumTasksScheduled());
        assertFalse(testSchedule.contains(task3));
    }

    @Test
    public void testIsEmptyNoTaskScheduled() {
        // No tasks to add
        // Check that ScheduleForDay.isEmpty() reports that schedule is empty
        assertTrue(testSchedule.isEmpty());
        assertEquals(0, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testIsEmptyTaskScheduled() {
        // Schedule task3
        testSchedule.addTask(task3);
        // Check that ScheduleForDay.isEmpty() reports that schedule is not empty
        assertFalse(testSchedule.isEmpty());
        assertEquals(1, testSchedule.getNumTasksScheduled());
    }

    @Test
    public void testGetTaskScheduledAtTimeUnscheduledTask() {
        Task task = task3;
        // Find a time, in hours and minutes, during the task's duration.
        int middleTimeInMinutes = getMiddleOfDurationInMinutes(task);
        int middleHour = middleTimeInMinutes / 60;
        int middleMinute = middleTimeInMinutes % 60;
        // check if task3 is scheduled. It should not be scheduled, and null should be returned.
        assertNull(testSchedule.getTaskScheduledAtTime(middleHour, middleMinute));
        assertNotEquals(task, testSchedule.getTaskScheduledAtTime(middleHour, middleMinute));
    }

    @Test
    public void testGetTaskScheduledAtTimeMiddleOfTaskDuration() {
        // Schedule a task
        Task task = task3;
        testSchedule.addTask(task);
        // Find a time, in hours and minutes, during the task's duration.
        int middleTimeInMinutes = getMiddleOfDurationInMinutes(task);
        int middleHour = middleTimeInMinutes / 60;
        int middleMinute = middleTimeInMinutes % 60;
        // check that task3 is said to be scheduled at the time computed above
        assertEquals(task, testSchedule.getTaskScheduledAtTime(middleHour, middleMinute));
    }

    @Test
    public void testGetTaskScheduledAtTimeStartOfTaskDuration() {
        // Schedule a task
        testSchedule.addTask(task3);
        // Check that the task is said to be scheduled at its start time. (The start time is included in its duration)
        assertEquals(task3, testSchedule.getTaskScheduledAtTime(task3.getStartHour(), task3.getStartMinute()));
    }

    @Test
    public void testGetTaskScheduleAtTimeEndOfTaskDuration() {
        // Schedule a task
        testSchedule.addTask(task3);
        // Check that the task is said to be scheduled at its end time.
        assertEquals(task3, testSchedule.getTaskScheduledAtTime(task3.getFinishHour(), task3.getFinishMinute()));
    }

    @Test
    public void testGetTaskScheduleAtTimeBeforeTaskDuration() {
        // Schedule a task
        testSchedule.addTask(task3);
        // Check that task3 is not said to be scheduled at a time before its start time.
        assertNull(testSchedule.getTaskScheduledAtTime(task3.getStartHour() - 1, task3.getStartMinute()));
    }

    @Test
    public void testGetTaskScheduledAtTimeAfterTaskDuration() {
        // Schedule a task
        testSchedule.addTask(task3);
        // Check that task3 is not said to be scheduled at a time after its end time.
        assertNull(testSchedule.getTaskScheduledAtTime(task3.getFinishHour() + 1, task3.getFinishMinute()));
    }

    @Test
    public void testGetShapeOfTheDayEmptySchedule() {
        // Schedule should be an empty linked list of tasks
        assertEquals(0, testSchedule.getShapeOfTheDay().size());
    }

    @Test
    public void testGetShapeOfTheDayNonEmptySchedule() {
        // add a task
        testSchedule.addTask(task3);
        // Schedule should be a linked list with task3 as its only element
        assertEquals(1, testSchedule.getShapeOfTheDay().size());
        assertTrue(testSchedule.getShapeOfTheDay().contains(task3));
    }

    private int getMiddleOfDurationInMinutes(Task task) {
        int startTimeInMinutes = (task.getStartHour() * 60) + task.getStartMinute(); // 0 is at 12 AM
        int endTimeInMinutes = (task.getFinishHour() * 60) + task.getFinishMinute(); // 0 is at 12 AM
        return (startTimeInMinutes + endTimeInMinutes) / 2;
    }
}
