package ui.panels;

import ui.ScheduleViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.ArrayList;

// CITATION:
// referred to https://www.youtube.com/watch?v=YKaea4ezQQE for using Java Swing and GridBagLayout
// (channel: Cave Of Programming | video: "Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout")

// Represents a panel that will consist of a grid of buttons, each button representing a day of a specified month.
public class DateSelectPanel extends JPanel implements ActionListener {
    private ScheduleViewer sv;
    private JFrame previousAgendaPageMenu;
    private Month month;
    private boolean isLeapYear;

    // EFFECTS: constructs a panel with a grid of buttons,
    //          each button for one day of the month specified by monthNumber.
    public DateSelectPanel(ScheduleViewer sv, String monthNumber, int year) {
        this.sv = sv;
        month = Month.of(Integer.parseInt(monthNumber));
        this.isLeapYear = Year.of(year).isLeap();

        // set layout
        setLayout(new GridBagLayout());

        // create components
        ArrayList<JButton> dayButtons = dayButtonInit();
        ArrayList<JLabel> daysOfTheWeek = daysOfTheWeekInit();
        // add components
        addComponents(dayButtons, daysOfTheWeek, year);
    }

    // EFFECTS: initializes and returns a list of the appropriate number of day buttons for the set month.
    private ArrayList<JButton> dayButtonInit() {
        ArrayList<JButton> dayButtons = new ArrayList<>();
        for (int i = 1; i <= month.length(isLeapYear); i++) {
            Integer dayOfTheMonth = new Integer(i);
            String dayNumber = dayOfTheMonth.toString(); // add leading zero to single digit numbers?
            dayButtons.add(singleButtonInit(dayNumber));
        }
        return dayButtons;
    }

    // EFFECTS: initializes and returns a day button with name and action command, dayNumber.
    private JButton singleButtonInit(String dayNumber) {
        JButton button = new JButton(dayNumber);
        if (dayNumber.length() == 1) {
            button.setActionCommand("0" + dayNumber);
        } else {
            button.setActionCommand(dayNumber);
        }
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: initializes and returns JLabels for the days of the week
    private ArrayList<JLabel> daysOfTheWeekInit() {
        ArrayList<JLabel> daysOfTheWeek = new ArrayList<>();
        daysOfTheWeek.add(singleDayOfTheWeekInit("Sunday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Monday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Tuesday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Wednesday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Thursday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Friday"));
        daysOfTheWeek.add(singleDayOfTheWeekInit("Saturday"));

        return daysOfTheWeek;
    }

    // EFFECTS: initializes and returns a JLabel corresponding to a day of the week
    private JLabel singleDayOfTheWeekInit(String dayOfTheWeek) {
        return new JLabel(dayOfTheWeek);
    }

    // MODIFIES: this
    // EFFECTS: adds day labels, and date buttons in a calendar grid format.
    private void addComponents(ArrayList<JButton> dayButtons, ArrayList<JLabel> dayOfTheWeek, int year) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 3;
        gc.weighty = 0.5;

        // add day labels
        for (int i = 1; i <= 7; i++) {
            gc.gridx = i - 1;
            gc.gridy = 0;
            this.add(dayOfTheWeek.get(i - 1), gc);
        }

        // add the buttons of each day
        gc.gridy = 1;
        gc.gridx = 0;
        for (JButton i: dayButtons) {
            LocalDate dayOfButton = LocalDate.of(year, month, Integer.parseInt(i.getActionCommand()));
            DayOfWeek dayOfWeek = dayOfButton.getDayOfWeek();

            gc.gridx = dayOfWeek.getValue();
            if (dayOfWeek == DayOfWeek.SUNDAY) {
                gc.gridy++;
                gc.gridx = 0;
            }
            add(i, gc);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        sv.setDayOfMonthToRetrieve(e.getActionCommand());
        previousAgendaPageMenu = sv.selectAgendaPage(previousAgendaPageMenu);
    }
}
