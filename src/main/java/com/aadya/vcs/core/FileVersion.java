package com.yourname.vcs.core;

import java.util.LinkedList;

public class FileVersion {
    private LinkedList<String> lines;

    public FileVersion() {
        lines = new LinkedList<>();
    }

    public FileVersion(LinkedList<String> lines) {
        this.lines = new LinkedList<>(lines);
    }

    // Add a new line at the end
    public void addLine(String line) {
        lines.add(line);
    }

    // Edit a specific line (0-based index)
    public void editLine(int index, String newLine) {
        if (index >= 0 && index < lines.size()) {
            lines.set(index, newLine);
        }
    }

    // Delete a line
    public void deleteLine(int index) {
        if (index >= 0 && index < lines.size()) {
            lines.remove(index);
        }
    }

    public LinkedList<String> getLines() {
        return new LinkedList<>(lines);
    }

    public int getLineCount() {
        return lines.size();
    }

    // Get line content (null if out of bounds)
    public String getLine(int index) {
        if (index >= 0 && index < lines.size()) {
            return lines.get(index);
        }
        return null;
    }
}

