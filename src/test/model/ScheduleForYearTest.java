package model;

import java.time.Year;
import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.IncorrectNumberOfAgendaPagesException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleForYearTest {
    ScheduleForYear testAgenda;

    @BeforeEach
    public void runBefore() {
        testAgenda = new ScheduleForYear();
    }

    @Test
    public void testConstructor() {
        Year currentYear = Year.now();

        // check that there are an appropriate number of ScheduleForDay objects stored in ScheduleForYear
        assertEquals(currentYear.length(), testAgenda.getAgendaSize());

        // check that agenda has been assigned the current year
        assertEquals(currentYear, testAgenda.getYear());

        // check that in each day, nothing is scheduled
        // check that each day has the correct year, month, and date
        checkAgendaPageYearMonthDate(currentYear);
    }

    @Test
    public void testOverloadedConstructorExceptionNotExpected() {
        // make an array list of ScheduleForDay objects of correct size
        ArrayList<ScheduleForDay> daySchedules = initializeYearsWorthOfDailySchedules();
        ScheduleForDay daySchedule1 = daySchedules.get(0);
        ScheduleForDay daySchedule2 = daySchedules.get(1);

        // call constructor
        try {
            testAgenda = new ScheduleForYear(daySchedules);
            // expected
        } catch (IncorrectNumberOfAgendaPagesException e) {
            fail("Should not have thrown an IncorrectNumberOfAgendaPagesException");
        }

        // check that agenda has been assigned the current year
        assertEquals(Year.now(), testAgenda.getYear());

        // check that the right number of ScheduleForDay objects have been added to testAgenda
        assertEquals(Year.now().length(), testAgenda.getAgendaSize());

        // check that the daySchedules have been added, and are in the right order
        assertEquals(daySchedule1, testAgenda.getNthDayInAgenda(0));
        assertEquals(daySchedule2, testAgenda.getNthDayInAgenda(1));

    }

    @Test
    public void testOverLoadedConstructorExceptionExpected() {
        // make an array list of ScheduleForDay objects
        ScheduleForDay daySchedule1 = new ScheduleForDay(1, 1, 2020);
        ScheduleForDay daySchedule2 = new ScheduleForDay(2,1, 2020);
        ArrayList<ScheduleForDay> daySchedules = new ArrayList<>();

        daySchedules.add(daySchedule1);
        daySchedules.add(daySchedule2);

        // call constructor
        try {
            testAgenda = new ScheduleForYear(daySchedules);
            fail("Should have thrown an IncorrectNumberOfAgendaPagesException");
        } catch (IncorrectNumberOfAgendaPagesException e) {
            // expected
        }
    }

    private ArrayList<ScheduleForDay> initializeYearsWorthOfDailySchedules() {
        ArrayList<ScheduleForDay> daySchedules = new ArrayList<>();
        for (int i = 0; i < Year.now().length(); i++) {
            LocalDate today = Year.now().atDay(i + 1);
            int date = today.getDayOfMonth();
            int month = today.getMonthValue();
            int year = today.getYear();
            daySchedules.add(new ScheduleForDay(date, month, year));
        }
        return daySchedules;
    }

    private void checkAgendaPageYearMonthDate(Year currentYear) {
        for (int i = 0; i < currentYear.length(); i++) {
            ScheduleForDay agendaPage = testAgenda.getNthDayInAgenda(i);
            LocalDate agendaPageDate = currentYear.atDay(i + 1);

            assertTrue(agendaPage.isEmpty());

            assertEquals(currentYear.getValue(), agendaPage.getYear());
            assertEquals(agendaPageDate.getMonthValue(), agendaPage.getMonth());
            assertEquals(agendaPageDate.getDayOfMonth(), agendaPage.getDate());
        }
    }

}
