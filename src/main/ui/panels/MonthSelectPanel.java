// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package ui.panels;

import ui.ScheduleViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a list of months to select from
public class MonthSelectPanel extends JPanel implements ActionListener {
    private ScheduleViewer sv;
    private JFrame window;
    private BorderLayout bl;
    private JPanel previousMonthPanel;
    private JMenuBar menuBar;
    private JMenu monthMenu;

    // EFFECTS: constructs a menu bar with a menu of months to select from
    public MonthSelectPanel(ScheduleViewer sv, JFrame window, BorderLayout bl) {
        this.sv = sv;
        menuBar = new JMenuBar();
        this.window = window;
        this.bl = bl;

        monthMenu = new JMenu("Month");
        initMonthMenu();

        menuBar.add(monthMenu);
        this.add(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: Initializes month menu
    private void initMonthMenu() {
        initMonth("January");
        initMonth("February");
        initMonth("March");
        initMonth("April");
        initMonth("May");
        initMonth("June");
        initMonth("July");
        initMonth("August");
        initMonth("September");
        initMonth("October");
        initMonth("November");
        initMonth("December");
    }

    // EFFECTS: initializes a month in the menu
    private void initMonth(String monthName) {
        JMenuItem month = new JMenuItem(monthName);
        month.setActionCommand(monthName);
        month.addActionListener(this);
        monthMenu.add(month);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String monthToRetrieve = handleMonthEvent(e.getActionCommand());
        sv.setMonthToRetrieve(monthToRetrieve);
        monthMenu.setText(e.getActionCommand());

        previousMonthPanel = sv.displayDesiredMonth(window.getContentPane(), bl, window, previousMonthPanel);
    }

    // a recursive chain of helper functions.

    // EFFECTS: returns "01" if the January menu item is clicked
    private String handleMonthEvent(String month) {
        if (month.equals("January")) {
            return "01";
        }
        return handleFebruaryEvent(month);
    }

    // EFFECTS: returns "02" if the February menu item is clicked
    private String handleFebruaryEvent(String month) {
        if (month.equals("February")) {
            return "02";
        }
        return handleMarchEvent(month);
    }

    // EFFECTS: returns "03" if the March menu item is clicked
    private String handleMarchEvent(String month) {
        if (month.equals("March")) {
            return "03";
        }
        return handleAprilEvent(month);
    }

    // EFFECTS: returns "04" if the April menu item is clicked
    private String handleAprilEvent(String month) {
        if (month.equals("March")) {
            return "03";
        }
        return handleMayEvent(month);
    }

    // EFFECTS: returns "05" if the May menu item is clicked
    private String handleMayEvent(String month) {
        if (month.equals("May")) {
            return "05";
        }
        return handleJuneEvent(month);
    }

    // EFFECTS: returns "06" if the June menu item is clicked
    private String handleJuneEvent(String month) {
        if (month.equals("June")) {
            return "06";
        }
        return handleJulyEvent(month);
    }

    // EFFECTS: returns "07" if the July menu item is clicked
    private String handleJulyEvent(String month) {
        if (month.equals("July")) {
            return "07";
        }
        return handleAugustEvent(month);
    }

    // EFFECTS: returns "08" if the August menu item is clicked
    private String handleAugustEvent(String month) {
        if (month.equals("August")) {
            return "08";
        }
        return handleSeptemberEvent(month);
    }

    // EFFECTS: returns "09" if the September menu item is clicked
    private String handleSeptemberEvent(String month) {
        if (month.equals("September")) {
            return "09";
        }
        return handleOctoberEvent(month);
    }

    // EFFECTS: returns "10" if the October menu item is clicked
    private String handleOctoberEvent(String month) {
        if (month.equals("October")) {
            return "10";
        }
        return handleNovemberEvent(month);
    }

    // EFFECTS: returns "11" if the November menu item is clicked
    private String handleNovemberEvent(String month) {
        if (month.equals("November")) {
            return "11";
        }
        return handleDecemberEvent(month);
    }

    // EFFECTS: returns "12" if the December menu item is clicked
    private String handleDecemberEvent(String month) {
        return "12";
    }
}
