// Author: Aswin Sai Subramanian
// Date: 10 January 2021

package ui.panels;

import ui.ScheduleViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// represents the window with the option to load saved agenda
public class AgendaLoaderPanel extends JPanel implements ActionListener {
    // panel content
    private JLabel loadOrNot;
    private JLabel feedback;
    private JButton yes;
    private JButton no;
    private ScheduleViewer sv;
    private boolean commandIsGiven;

    // EFFECTS: Constructs a JPanel for a window with the option to load saved agenda
    public AgendaLoaderPanel(ScheduleViewer sv) {
        initContent();
        addContentsToPanel();
        this.setOpaque(true);
        this.sv = sv;
        commandIsGiven = false;
    }

    // MODIFIES: this
    // EFFECTS: Initializes panel components
    private void initContent() {
        loadOrNot = new JLabel("Load agenda?");
        feedback = new JLabel();

        yes = new JButton("Yes");
        yes.setActionCommand("y");
        yes.addActionListener(this);

        no = new JButton("No");
        no.addActionListener(this);
        no.setActionCommand("n");
    }

    // EFFECTS: adds to the panel, text indicating option to load agenda,
    //          as well as the buttons to indicate user's choice
    private void addContentsToPanel() {
        this.add(loadOrNot);
        this.add(yes);
        this.add(no);
        this.add(feedback);
    }

    public boolean getCommandIsGiven() {
        return commandIsGiven;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() != null) {
            try {
                sv.loadAgenda(e.getActionCommand());
                if (e.getActionCommand() == "y") {
                    feedback.setText("...Agenda loaded! Welcome back.");
                } else {
                    feedback.setText("...Welcome to your brand new student agenda!");
                }
                sv.setGreeting(feedback);
            } catch (IOException ioe) {
                feedback.setText("...Unable to read save file. Replacement agenda created");
            } finally {
                commandIsGiven = true;
            }
        }
    }
}
