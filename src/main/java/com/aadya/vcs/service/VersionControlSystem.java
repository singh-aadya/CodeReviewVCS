package com.aadya.vcs.service;

import com.yourname.vcs.core.*;

import java.util.*;

public class VersionControlSystem {

    private Map<String, Branch> branches = new HashMap<>();
    private Branch currentBranch;

    // Working files before commit: filename -> FileVersion
    private Map<String, FileVersion> workingFiles = new HashMap<>();

    // Undo/Redo stacks for edit operations
    private Stack<EditOperation> undoStack = new Stack<>();
    private Stack<EditOperation> redoStack = new Stack<>();

    private int commitCounter = 0;

    public VersionControlSystem() {
        // Initialize with master branch and empty initial commit
        Commit initialCommit = new Commit(generateCommitId(), "Initial commit", new ArrayList<>(), new HashMap<>());
        Branch master = new Branch("master", initialCommit);
        branches.put("master", master);
        currentBranch = master;
    }

    private String generateCommitId() {
        return "c" + (++commitCounter);
    }

    public void createFile(String fileName) {
        if (workingFiles.containsKey(fileName)) {
            System.out.println("File already exists in working directory.");
            return;
        }
        workingFiles.put(fileName, new FileVersion());
        System.out.println("File '" + fileName + "' created.");
    }

    public void addLine(String fileName, String line) {
        FileVersion fv = workingFiles.get(fileName);
        if (fv == null) {
            System.out.println("File not found.");
            return;
        }
        fv.addLine(line);
        undoStack.push(new EditOperation(fileName, fv.getLineCount() - 1, null, line, EditOperation.Type.ADD));
        redoStack.clear();
        System.out.println("Line added.");
    }

    public void editLine(String fileName, int lineNumber, String newLine) {
        FileVersion fv = workingFiles.get(fileName);
        if (fv == null || lineNumber < 0 || lineNumber >= fv.getLineCount()) {
            System.out.println("Invalid file or line number.");
            return;
        }
        String oldLine = fv.getLine(lineNumber);
        fv.editLine(lineNumber, newLine);
        undoStack.push(new EditOperation(fileName, lineNumber, oldLine, newLine, EditOperation.Type.EDIT));
        redoStack.clear();
        System.out.println("Line edited.");
    }

    public void deleteLine(String fileName, int lineNumber) {
        FileVersion fv = workingFiles.get(fileName);
        if (fv == null || lineNumber < 0 || lineNumber >= fv.getLineCount()) {
            System.out.println("Invalid file or line number.");
            return;
        }
        String oldLine = fv.getLine(lineNumber);
        fv.deleteLine(lineNumber);
        undoStack.push(new EditOperation(fileName, lineNumber, oldLine, null, EditOperation.Type.DELETE));
        redoStack.clear();
        System.out.println("Line deleted.");
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        EditOperation op = undoStack.pop();
        FileVersion fv = workingFiles.get(op.getFileName());
        switch (op.getOperationType()) {
            case ADD -> {
                // Remove added line
                fv.deleteLine(op.getLineNumber());
            }
            case EDIT -> {
                fv.editLine(op.getLineNumber(), op.getOldLine());
            }
            case DELETE -> {
                fv.getLines().add(op.getLineNumber(), op.getOldLine());
            }
        }
        redoStack.push(op);
        System.out.println("Undo performed.");
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        EditOperation op = redoStack.pop();
        FileVersion fv = workingFiles.get(op.getFileName());
        switch (op.getOperationType()) {
            case ADD -> {
                fv.addLine(op.getNewLine());
            }
            case EDIT -> {
                fv.editLine(op.getLineNumber(), op.getNewLine());
            }
            case DELETE -> {
                fv.deleteLine(op.getLineNumber());
            }
        }
        undoStack.push(op);
        System.out.println("Redo performed.");
    }

    public void commit(String message) {
        if (workingFiles.isEmpty()) {
            System.out.println("No files to commit.");
            return;
        }

        // Deep copy current working files for snapshot
        Map<String, FileVersion> snapshot = new HashMap<>();
        for (Map.Entry<String, FileVersion> entry : workingFiles.entrySet()) {
            snapshot.put(entry.getKey(), new FileVersion(entry.getValue().getLines()));
        }

        Commit newCommit = new Commit(generateCommitId(), message,
                List.of(currentBranch.getHead()), snapshot);

        currentBranch.setHead(newCommit);

        // Clear undo/redo stacks after commit
        undoStack.clear();
        redoStack.clear();

        System.out.println("Committed: " + newCommit.getCommitId() + " - " + message);
    }

    public void createBranch(String branchName) {
        if (branches.containsKey(branchName)) {
            System.out.println("Branch already exists.");
            return;
        }
        Branch newBranch = new Branch(branchName, currentBranch.getHead());
        branches.put(branchName, newBranch);
        System.out.println("Branch '" + branchName + "' created at commit " + currentBranch.getHead().getCommitId());
    }

    public void switchBranch(String branchName) {
        Branch branch = branches.get(branchName);
        if (branch == null) {
            System.out.println("Branch not found.");
            return;
        }
        currentBranch = branch;

        // Update working files to snapshot of branch head
        workingFiles.clear();
        Map<String, FileVersion> filesSnapshot = currentBranch.getHead().getFileVersions();
        for (var entry : filesSnapshot.entrySet()) {
            workingFiles.put(entry.getKey(), new FileVersion(entry.getValue().getLines()));
        }

        // Clear undo/redo on branch switch
        undoStack.clear();
        redoStack.clear();

        System.out.println("Switched to branch '" + branchName + "'.");
    }

    public void showCommitHistory() {
        System.out.println("Commit history for branch: " + currentBranch.getName());
        printCommitRecursive(currentBranch.getHead(), new HashSet<>());
    }

    private void printCommitRecursive(Commit commit, Set<String> visited) {
        if (commit == null || visited.contains(commit.getCommitId())) return;
        visited.add(commit.getCommitId());
        System.out.println(commit.getCommitId() + ": " + commit.getMessage() + " (" + commit.getTimestamp() + ")");
        for (Commit parent : commit.getParents()) {
            printCommitRecursive(parent, visited);
        }
    }

    public void listFiles() {
        if (workingFiles.isEmpty()) {
            System.out.println("No files in working directory.");
            return;
        }
        System.out.println("Files in working directory:");
        for (String fileName : workingFiles.keySet()) {
            System.out.println("- " + fileName);
        }
    }

    public void showFileContent(String fileName) {
        FileVersion fv = workingFiles.get(fileName);
        if (fv == null) {
            System.out.println("File not found.");
            return;
        }
        System.out.println("Contents of file '" + fileName + "':");
        int lineNum = 0;
        for (String line : fv.getLines()) {
            System.out.printf("%d: %s%n", lineNum++, line);
        }
    }
}

