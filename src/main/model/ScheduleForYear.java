// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package model;

import exceptions.IncorrectNumberOfAgendaPagesException;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

// A class to collect all ScheduleForDay objects, and peg each to a actual date in the year.
public class ScheduleForYear {

    private Year year;                        // Represents the current year.
    private ArrayList<ScheduleForDay> agenda; // Index of this array list will correspond to the day of
                                              // the year represented by the ScheduleForDay object.

    // EFFECTS: Constructs a collection of 365 or 366 empty schedules (agenda pages), one for each day of the year
    public ScheduleForYear() {
        year = Year.now();
        agenda = new ArrayList<ScheduleForDay>();

        for (int i = 0; i < year.length(); i++) {
            LocalDate date = year.atDay(i + 1);
            ScheduleForDay agendaPage = new ScheduleForDay(date.getDayOfMonth(), date.getMonthValue(), year.getValue());
            agenda.add(agendaPage);
        }
    }

    // REQUIRES: the list of daily schedules being loaded should have the
    //           same size as there are days in the present year.
    // EFFECTS: - Constructs an agenda with the given ArrayList of daily schedules.
    //          - If the ArrayList has a size other than the number of days in the
    //            present year, throws an IncorrectNumberOfAgendaPagesException.
    // NOTE: for use with loading saved agenda only
    public ScheduleForYear(ArrayList<ScheduleForDay> dailySchedules) throws IncorrectNumberOfAgendaPagesException {
        year = Year.now();
        if (dailySchedules.size() == year.length()) {
            agenda = dailySchedules;
        } else {
            throw new IncorrectNumberOfAgendaPagesException();
        }
    }


    // getters

    // EFFECTS: Returns the nth agenda page (nth day's ScheduleForDay object in agenda).
    //          Pages start at page 0.
    public ScheduleForDay getNthDayInAgenda(int n) {
        return agenda.get(n);
    }

    // EFFECTS: Returns the number of pages (ScheduleForDay objects) in the agenda
    public int getAgendaSize() {
        return agenda.size();
    }

    // EFFECTS: Returns the year assigned to the agenda.
    public Year getYear() {
        return year;
    }
}
