// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package persistence;

import model.ScheduleForDay;
import model.Task;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: WriterTest class adapted from tellerApp (example personal project)
public class WriterTest {
    private static final String TEST_FILE = "./data/testAgenda";
    private static File testFile;
    private static ScheduleForDay testSchedule;
    private static Writer testWriter;

    @BeforeEach
    public void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testFile = new File(TEST_FILE);
        testSchedule = new ScheduleForDay(01, 01, 2020);
        testWriter = new Writer(testFile);
    }

    @Test
    public void testSaveScheduleForDayNoTasksScheduled() {
        // confirm that no tasks are scheduled in testSchedule
        assertTrue(testSchedule.isEmpty());

        // write testSchedule to file
        testWriter.write(testSchedule);
        testWriter.close();

        // Check that the file output is as expected
        try {
            ArrayList<ScheduleForDay> schedulesReadFromFile = Reader.readDaySchedules(testFile);
            ScheduleForDay scheduleToCheck = schedulesReadFromFile.get(0);
            // check that only one ScheduleForDay object has been created
            assertEquals(1, schedulesReadFromFile.size());
            compareScheduleForDayFields(testSchedule, scheduleToCheck);
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }

    @Test
    public void testSaveScheduleForDayOneTaskScheduled() {
        // schedule a task in testSchedule
        Task task1 = new Task("task1", 13, 30, 14, 30);
        testSchedule.addTask(task1);

        // write testSchedule to file
        testWriter.write(testSchedule);
        testWriter.close();

        // Check that the file output is as expected
        try {
            ArrayList<ScheduleForDay> schedulesReadFromFile = Reader.readDaySchedules(testFile);
            ScheduleForDay scheduleToCheck = schedulesReadFromFile.get(0);
            // check that only one ScheduleForDay object has been created
            assertEquals(1, schedulesReadFromFile.size());
            // check ScheduleForDay fields
            compareScheduleForDayFields(testSchedule, scheduleToCheck);
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }

    @Test
    public void testSaveTwoScheduleForDayObjects() {
        // create a new day schedule
        ScheduleForDay testSchedule2 = new ScheduleForDay(02, 01, 2020);

        // create two tasks
        Task task1 = new Task("task1", 13, 30, 14, 30);
        Task task2 = new Task("task2", 14, 40, 15, 40);

        // add tasks to testSchedules
        testSchedule.addTask(task1);
        testSchedule.addTask(task2);
        testSchedule2.addTask(task2);

        // write testSchedules to file
        testWriter.write(testSchedule);
        testWriter.write(testSchedule2);
        testWriter.close();

        // Check that the file output is as expected
        try {
            ArrayList<ScheduleForDay> schedulesReadFromFile = Reader.readDaySchedules(testFile);
            ScheduleForDay scheduleToCheck = schedulesReadFromFile.get(0);
            ScheduleForDay scheduleToCheck2 = schedulesReadFromFile.get(1);
            // check that only two ScheduleForDay object has been created
            assertEquals(2, schedulesReadFromFile.size());
            // check ScheduleForDay fields
            compareScheduleForDayFields(testSchedule, scheduleToCheck);
            compareScheduleForDayFields(testSchedule2, scheduleToCheck2);
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }

    private void compareShapeOfTheDay(ScheduleForDay testSchedule, ScheduleForDay scheduleToCheck) {
        int count = 0;
        for (Task i : testSchedule.getShapeOfTheDay()) {
            Task j = scheduleToCheck.getShapeOfTheDay().get(count);
            assertEquals(i.getTaskName(), j.getTaskName());
            // assertEquals(i.getTaskAbbreviation(), j.getTaskAbbreviation());
            assertEquals(i.getStartHour(), j.getStartHour());
            assertEquals(i.getStartMinute(), j.getStartMinute());
            assertEquals(i.getFinishHour(), j.getFinishHour());
            assertEquals(i.getFinishMinute(), j.getFinishMinute());
            count++;
        }
    }

    private void compareScheduleForDayFields(ScheduleForDay testSchedule, ScheduleForDay scheduleToCheck) {
        assertEquals(testSchedule.isEmpty(), scheduleToCheck.isEmpty());
        assertEquals(testSchedule.getNumTasksScheduled(), scheduleToCheck.getNumTasksScheduled());
        compareShapeOfTheDay(testSchedule, scheduleToCheck);
        assertEquals(testSchedule.getYear(), scheduleToCheck.getYear());
        assertEquals(testSchedule.getMonth(), scheduleToCheck.getMonth());
        assertEquals(testSchedule.getDate(), scheduleToCheck.getDate());
    }
}
