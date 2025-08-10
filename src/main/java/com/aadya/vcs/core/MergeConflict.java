package com.yourname.vcs.core;

public class MergeConflict {
    private final String fileName;
    private final int lineNumber;
    private final String baseLine;      // line from base commit
    private final String currentLine;   // line from current branch
    private final String incomingLine;  // line from branch being merged
    private boolean resolved;

    public MergeConflict(String fileName, int lineNumber, String baseLine, String currentLine, String incomingLine) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.baseLine = baseLine;
        this.currentLine = currentLine;
        this.incomingLine = incomingLine;
        this.resolved = false;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getBaseLine() {
        return baseLine;
    }

    public String getCurrentLine() {
        return currentLine;
    }

    public String getIncomingLine() {
        return incomingLine;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void markResolved() {
        this.resolved = true;
    }

    @Override
    public String toString() {
        return "MergeConflict{" +
                "file='" + fileName + '\'' +
                ", line=" + lineNumber +
                ", base='" + baseLine + '\'' +
                ", current='" + currentLine + '\'' +
                ", incoming='" + incomingLine + '\'' +
                ", resolved=" + resolved +
                '}';
    }
}

