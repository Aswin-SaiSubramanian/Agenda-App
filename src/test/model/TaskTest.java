// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    Task task1;

    @BeforeEach
    public void runBefore() {
        task1 = new Task("task1", 12, 30, 13, 30);
    }

    @Test
    public void testGetTaskName() {
        assertEquals("task1", task1.getTaskName());
    }

    @Test
    public void testSetAndGetTaskAbbreviation() {
        task1.setTaskAbbreviation('A');
        assertEquals('A', task1.getTaskAbbreviation());
    }
}
