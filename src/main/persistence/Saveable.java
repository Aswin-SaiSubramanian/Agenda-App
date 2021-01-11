package persistence;

import java.io.PrintWriter;

// CITATION: Saveable class taken from tellerApp (example personal project)

// A class representing data that can be saved to file
public interface Saveable {


    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}
