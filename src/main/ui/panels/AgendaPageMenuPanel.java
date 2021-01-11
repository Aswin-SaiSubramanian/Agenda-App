package ui.panels;

import ui.ScheduleViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

// CITATION:
// referred to https://www.youtube.com/watch?v=YKaea4ezQQE for using Java Swing and GridBagLayout
// (channel: Cave Of Programming | video: "Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout")

// represents a window that gives the agenda menu associated with a selected date
public class AgendaPageMenuPanel extends JPanel implements ActionListener {
    private ScheduleViewer sv;
    private LocalDate agendaPage;
    private JPanel previousShapeOfTheDay;
    private JFrame parentWindow;

    // EFFECTS: constructs the agenda menu associated with the selected date
    public AgendaPageMenuPanel(ScheduleViewer sv, LocalDate agendaPage, JFrame parentWindow) {
        this.sv = sv;
        this.agendaPage = agendaPage;
        this.parentWindow = parentWindow;

        // set layout
        setLayout(new GridBagLayout());

        // create components
        JLabel dateLabel = dateLabelInit();
        ArrayList<JButton> menuOptions = menuOptionsInit();

        // add components
        addComponents(dateLabel, menuOptions);
    }

    // EFFECTS: Initializes and returns a label showing the date that this menu is associated with
    private JLabel dateLabelInit() {
        // Formatting the text of the date label
        // format of text: WEDNESDAY, 1 JANUARY 2020
        String dayOfWeek = agendaPage.getDayOfWeek().toString();
        int dayOfMonth = agendaPage.getDayOfMonth();
        String monthOfYear = agendaPage.getMonth().toString();
        int year = agendaPage.getYear();
        String dateString = dayOfWeek + ", " + dayOfMonth + " " + monthOfYear + " " + year;

        return new JLabel(dateString);
    }

    // EFFECTS: Initializes and returns a list of buttons representing menu options
    private ArrayList<JButton> menuOptionsInit() {
        ArrayList<JButton> menuOptions = new ArrayList<>();
        menuOptions.add(singleMenuOptionInit("View schedule for the day", "s"));

        // creating a green save button
        // CITATION: referred to http://www.javased.com/?post=1065691
        JButton saveButton = singleMenuOptionInit("Save changes", "k");
        saveButton.setBackground(Color.GREEN);
        saveButton.setOpaque(true);
        menuOptions.add(saveButton);

        return menuOptions;
    }

    // EFFECTS: Initializes and returns a single menu option button
    private JButton singleMenuOptionInit(String buttonText, String actionCommand) {
        JButton menuOption = new JButton(buttonText);
        menuOption.setSize(new Dimension(10,1));
        menuOption.setActionCommand(actionCommand);
        menuOption.addActionListener(this);
        return menuOption;
    }

    // MODIFIES: this
    // EFFECT: adds the initialized components to this agenda page menu panel
    private void addComponents(JLabel dateLabel, ArrayList<JButton> menuOptions) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTH;
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        this.add(dateLabel, gc);

        for (JButton i: menuOptions) {
            gc.gridy++;
            this.add(i, gc);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        previousShapeOfTheDay = sv.selectAgendaPageOption(e.getActionCommand(), previousShapeOfTheDay, parentWindow);
    }
}
