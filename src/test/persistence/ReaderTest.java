// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package persistence;

import model.ScheduleForDay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Task;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    public void testReadDaySchedules() {
        Reader r = new Reader();
        ArrayList<ScheduleForDay> daySchedules = null;
        try {
            daySchedules = r.readDaySchedules(new File("./data/ReaderTestAgenda.txt"));
        } catch (IOException ioe) {
            fail("Unexpected IOException thrown");
        }

        // check ScheduleForDay object fields
        checkScheduleForDayObjectFields(daySchedules);

        // check shapeOfTheDay fields
        ScheduleForDay daySchedule1 = daySchedules.get(0); // first ScheduleForDay object saved
        ScheduleForDay daySchedule2 = daySchedules.get(2); // third ScheduleForDay object saved

        // first ScheduleForDay object saved
        Task taskScheduled = daySchedule1.getShapeOfTheDay().get(0);
        assertEquals("task1", taskScheduled.getTaskName());
        assertEquals(4, taskScheduled.getStartHour());
        assertEquals(40, taskScheduled.getStartMinute());
        assertEquals(6, taskScheduled.getFinishHour());
        assertEquals(30, taskScheduled.getFinishMinute());

        // third ScheduleForDay object saved
        Task taskScheduled1 = daySchedule2.getShapeOfTheDay().get(0);
        assertEquals("task2", taskScheduled1.getTaskName());
        assertEquals(13, taskScheduled1.getStartHour());
        assertEquals(30, taskScheduled1.getStartMinute());
        assertEquals(14, taskScheduled1.getFinishHour());
        assertEquals(30, taskScheduled1.getFinishMinute());

        Task taskScheduled2 = daySchedule2.getShapeOfTheDay().get(1);
        assertEquals("task3", taskScheduled2.getTaskName());
        assertEquals(14, taskScheduled2.getStartHour());
        assertEquals(35, taskScheduled2.getStartMinute());
        assertEquals(15, taskScheduled2.getFinishHour());
        assertEquals(30, taskScheduled2.getFinishMinute());
    }

    private void checkScheduleForDayObjectFields(ArrayList<ScheduleForDay> daySchedules) {
        for (ScheduleForDay i: daySchedules) {
            assertEquals(2020, i.getYear());
            assertEquals(1, i.getMonth());
        }

        assertEquals(1, daySchedules.get(0).getDate());
        assertEquals(2, daySchedules.get(1).getDate());
        assertEquals(3, daySchedules.get(2).getDate());

        assertEquals(1, daySchedules.get(0).getNumTasksScheduled());
        assertEquals(0, daySchedules.get(1).getNumTasksScheduled());
        assertEquals(2, daySchedules.get(2).getNumTasksScheduled());
    }
}
