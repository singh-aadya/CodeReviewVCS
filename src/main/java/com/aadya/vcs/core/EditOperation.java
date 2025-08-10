package com.aadya.vcs.core;

public class EditOperation {
    public enum Type {
        ADD, EDIT, DELETE
    }

    private final String fileName;
    private final int lineNumber;
    private final String oldLine;
    private final String newLine;
    private final Type operationType;

    public EditOperation(String fileName, int lineNumber, String oldLine, String newLine, Type operationType) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.oldLine = oldLine;
        this.newLine = newLine;
        this.operationType = operationType;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getOldLine() {
        return oldLine;
    }

    public String getNewLine() {
        return newLine;
    }

    public Type getOperationType() {
        return operationType;
    }
}
