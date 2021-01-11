package ui;

import exceptions.IncorrectNumberOfAgendaPagesException;
import model.ScheduleForDay;
import model.ScheduleForYear;
import persistence.Reader;
import persistence.Writer;
import ui.panels.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

// Forms the user interface. To run the app, instantiated in ui.Main.
public class ScheduleViewer {

    private static final String AGENDA_FILE = "./data/agenda.txt";
    private ScheduleForYear yearSchedule;
    private ScheduleForDay daySchedule;
    private String monthToRetrieve;         // represents the months by "01" for Jan., "02" for Feb. and so on
    private String dayOfMonthToRetrieve;    // represents days of a month as a string ("01", "02", ... , "30", "31").
    private JLabel greeting;

    // MODIFIES: this
    // EFFECTS: Creates a new schedule viewer, and runs the schedule viewer
    public ScheduleViewer() {
        yearSchedule = new ScheduleForYear();
        runScheduleViewer();
    }

    // EFFECTS: gets the field of type ScheduleForDay from this class
    public ScheduleForDay getDaySchedule() {
        return daySchedule;
    }

    // MODIFIES: this
    // EFFECTS: Sets the month from which daySchedule will be retrieved out of yearSchedule
    public void setMonthToRetrieve(String monthToRetrieve) {
        this.monthToRetrieve = monthToRetrieve;
    }

    // MODIFIES: this
    // EFFECTS: Sets the day of the month, which is the final piece of info
    //          that will be used to identify and retrieve the desired daySchedule from yearSchedule
    public void setDayOfMonthToRetrieve(String dayOfMonthToRetrieve) {
        this.dayOfMonthToRetrieve = dayOfMonthToRetrieve;
    }

    // MODIFIES: this
    // EFFECTS: Sets the greeting label to displayed on the JFrame that displays the chosen month
    public void setGreeting(JLabel greeting) {
        this.greeting = greeting;
    }

    // EFFECTS: runs the schedule viewer
    //
    // CITATION: Adapted from tellerApp (C1 lecture lab starter)
    private void runScheduleViewer() {
        // set up window
        JFrame window = windowInit();

        agendaLoadOptionWindow(window);
        askForDesiredMonthAndDayOfMonth();
    }

    // EFFECTS: Initializes JFrame window object
    private JFrame windowInit() {
        JFrame window = new JFrame("Agenda App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return window;
    }

    // EFFECTS: displays a window with the option to load an agenda, or create a new one.
    private void agendaLoadOptionWindow(JFrame window) {
        // set up content pane
        AgendaLoaderPanel agendaLoadWindow = new AgendaLoaderPanel(this);

        // put content pane into window, and display window
        window.setContentPane(agendaLoadWindow);
        window.setMinimumSize(new Dimension(1000,100));
        window.pack();
        do {
            window.setVisible(true);
        } while (!agendaLoadWindow.getCommandIsGiven());
        window.dispose();
    }

    // CITATION: Adapted from tellerApp (example personal project)
    // EFFECTS: saves state of all changed daySchedule objects stored in yearSchedule to AGENDA_FILE
    private void saveAgenda() {
        try {
            Writer writer = new Writer(new File(AGENDA_FILE));
            for (int i = 0; i < yearSchedule.getAgendaSize(); i++) {
                ScheduleForDay agendaPage = yearSchedule.getNthDayInAgenda(i);
                writer.write(agendaPage);
            }
            writer.close();
            System.out.println("Accounts saved to file " + AGENDA_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + AGENDA_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // EFFECTS: Loads saved daySchedule objects into yearSchedule from AGENDA_FILE
    public void loadAgenda(String command) throws IOException {
        if (command.equals("y")) {
            ArrayList<ScheduleForDay> daySchedules = Reader.readDaySchedules(new File(AGENDA_FILE));
            try {
                yearSchedule = new ScheduleForYear(daySchedules);
                System.out.println("\nAgenda loaded! Welcome back.");
            } catch (IncorrectNumberOfAgendaPagesException e) {
                System.out.println("\nIncorrect number of daily schedule entries in save file.");
                System.out.println("\nWelcome to your brand new student agenda!");
            }
        } else {
            System.out.println("\nWelcome to your brand new student agenda!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays a window with a pull-down menu for the user to select the
    //          month they want to look at, and then that month.
    private void askForDesiredMonthAndDayOfMonth() {
        JFrame window = windowInit();

        Container c = window.getContentPane();
        BorderLayout bl = new BorderLayout();
        c.setLayout(bl);

        MonthSelectPanel monthChoiceWindow = new MonthSelectPanel(this, window, bl);

        c.add(greeting, bl.LINE_START);
        c.add(monthChoiceWindow, bl.LINE_END);

        window.setMinimumSize(new Dimension(1000,100));
        window.pack();
        window.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: displays and returns the desired month in a calendar format.
    //          Called by MonthSelectPanel object whenever a month is selected.
    public JPanel displayDesiredMonth(Container c, BorderLayout bl, JFrame window, JPanel previousMonthPanel) {
        // remove the previous month's calendar from view
        if (previousMonthPanel != null) {
            c.remove(previousMonthPanel);
        }

        // add the newly selected month's calender into view
        int year = yearSchedule.getYear().getValue();
        DateSelectPanel dateSelectPanel = new DateSelectPanel(this, monthToRetrieve, year);
        c.add(dateSelectPanel, bl.CENTER);
        window.pack();

        // this return value will be looped back in as previousMonthPanel, when a new month is selected
        return dateSelectPanel;
    }

    // MODIFIES: this
    // EFFECTS: Gets from user, the day of the year which they want to view.
    private void getRequestedDayOfYear() {
        String yearMonthDate;
        yearMonthDate = yearSchedule.getYear().getValue() + "-" + monthToRetrieve + "-" + dayOfMonthToRetrieve;
        daySchedule = yearSchedule.getNthDayInAgenda((LocalDate.parse(yearMonthDate).getDayOfYear()) - 1);
    }

    // EFFECTS: - Gets the day of the year which the user wants to view
    //          - Displays the agenda page menu (agenda page referring to the requested day)
    //
    public JFrame selectAgendaPage(JFrame previousAgendaPageMenu) {
        getRequestedDayOfYear();

        if (previousAgendaPageMenu != null) {
            previousAgendaPageMenu.dispose();
        }

        // This return value will be looped back to this method as previousAgendaPageMenu
        // the next time another date in DateSelectPanel object is clicked
        return agendaPageMenu();
    }

    // EFFECTS: Displays the following menu:
    //
    //          <day>, <date> <month> <year>
    //               View schedule for the day
    //               Add task
    //               Save changes
    //
    private JFrame agendaPageMenu() {
        JFrame window = windowInit();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set layout for the window
        window.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 5;
        gc.weighty = 5;

        int year = daySchedule.getYear();
        int month = daySchedule.getMonth();
        int date = daySchedule.getDate();
        LocalDate agendaPage = LocalDate.of(year, month, date);
        AgendaPageMenuPanel agendaPageMenu = new AgendaPageMenuPanel(this, agendaPage, window);

        gc.gridx = 0;
        gc.gridy = 0;
        window.getContentPane().add(agendaPageMenu, gc);
        window.setMinimumSize(new Dimension(400,200));
        addTaskAddingPanel(window);
        window.pack();
        window.setVisible(true);
        return window;
    }

    // REQUIRES: command == s, a or k.
    // EFFECTS: If command == s
    //              The day's schedule will be displayed
    //          else if command == a
    //              Add the specified task
    //          else if command == k
    //              Save changes
    //
    public JPanel selectAgendaPageOption(String command, JPanel previousShapeOfTheDay, JFrame window) {
        JPanel panel = null;
        switch (command) {
            case "s":
                panel = displayShapeOfTheDay(window, previousShapeOfTheDay);
                break;
            case "k" :
                saveAgenda();
                break;
        }
        return panel;
    }

    // EFFECTS: Displays the day's schedule in the following format:
    //
    //          Shape of the Day
    //                      Start time                  Task
    //              <Start hour>:<Start minute>     <Task name>
    //                          ...                      ...
    //
    //          Each task will be a button, leading to a window displaying that task
    //
    private JPanel displayShapeOfTheDay(JFrame window, JPanel previousShapeOfTheDayPanel) {
        // dispose the previous shape of the day window
        if (previousShapeOfTheDayPanel != null) {
            previousShapeOfTheDayPanel.setVisible(false);
            window.getContentPane().remove(previousShapeOfTheDayPanel);
        }

        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 5;
        gc.weighty = 5;

        ShapeOfTheDayPanel shapeOfTheDayPanel = new ShapeOfTheDayPanel(this, window);
        shapeOfTheDayPanel.setOpaque(true);

        gc.gridx = 1;
        gc.gridy = 0;
        window.getContentPane().add(shapeOfTheDayPanel, gc);

        window.pack();
        return shapeOfTheDayPanel;
    }

    // EFFECTS: displays the window showing details of task with name taskName,
    //          as well as option to remove it from schedule.
    //
    public JPanel displayTask(JFrame window, ShapeOfTheDayPanel schedule,
                              JPanel previousTaskDisplayed, String taskName) {
        // remove the previous panel displaying a task
        if (previousTaskDisplayed != null) {
            previousTaskDisplayed.setVisible(false);
        }

        TaskPanel taskPanel = new TaskPanel(daySchedule, taskName, schedule);

        // set layout
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 5;
        gc.weighty = 5;

        gc.gridx = 2;
        gc.gridy = 0;
        window.getContentPane().add(taskPanel, gc);
        window.pack();

        // This return value will be looped back as the previousTaskDisplayed parameter
        // when the next task button in a shape of the day is clicked.
        return taskPanel;
    }

    // REQUIRES: Task name should have no white-space in it.
    // MODIFIES: This
    // EFFECTS: Creates and adds an add-task panel to the agenda page menu.
    //          Requests taskName, startHour, startMinute, finishHour, and finishMinute.
    //          Adds a task with task name taskName,
    //          start time startHour:startMinute,
    //          and end time finishHour:finishMinute
    private void addTaskAddingPanel(JFrame window) {
        TaskAddingPanel taskAddingPanel = new TaskAddingPanel(this);

        // set layout
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 5;
        gc.weighty = 5;

        gc.gridx = 0;
        gc.gridy = 1;
        window.getContentPane().add(taskAddingPanel, gc);
        window.pack();
    }

}
