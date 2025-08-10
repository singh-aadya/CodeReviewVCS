
package com.aadya.vcs.ui;

import com.aadya.vcs.service.VersionControlSystem;

import java.util.Scanner;

public class ConsoleUI {
    private VersionControlSystem vcs;
    private Scanner scanner;

    public ConsoleUI() {
        vcs = new VersionControlSystem();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to Code Review & Version Control Simulator ===");

        while (true) {
            printMenu();
            System.out.print("Choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> createFile();
                case "2" -> addLine();
                case "3" -> editLine();
                case "4" -> deleteLine();
                case "5" -> vcs.commit(prompt("Enter commit message: "));
                case "6" -> createBranch();
                case "7" -> switchBranch();
                case "8" -> vcs.showCommitHistory();
                case "9" -> vcs.listFiles();
                case "10" -> showFileContent();
                case "11" -> vcs.undo();
                case "12" -> vcs.redo();
                case "0" -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create new file");
        System.out.println("2. Add line to file");
        System.out.println("3. Edit line in file");
        System.out.println("4. Delete line from file");
        System.out.println("5. Commit changes");
        System.out.println("6. Create branch");
        System.out.println("7. Switch branch");
        System.out.println("8. Show commit history");
        System.out.println("9. List files");
        System.out.println("10. Show file content");
        System.out.println("11. Undo last edit");
        System.out.println("12. Redo last edit");
        System.out.println("0. Exit");
    }

    private void createFile() {
        String fileName = prompt("Enter file name: ");
        vcs.createFile(fileName);
    }

    private void addLine() {
        String fileName = prompt("Enter file name: ");
        String line = prompt("Enter line to add: ");
        vcs.addLine(fileName, line);
    }

    private void editLine() {
        String fileName = prompt("Enter file name: ");
        int lineNumber = promptInt("Enter line number (0-based): ");
        String newLine = prompt("Enter new line content: ");
        vcs.editLine(fileName, lineNumber, newLine);
    }

    private void deleteLine() {
        String fileName = prompt("Enter file name: ");
        int lineNumber = promptInt("Enter line number (0-based): ");
        vcs.deleteLine(fileName, lineNumber);
    }

    private void createBranch() {
        String branchName = prompt("Enter new branch name: ");
        vcs.createBranch(branchName);
    }

    private void switchBranch() {
        String branchName = prompt("Enter branch name to switch to: ");
        vcs.switchBranch(branchName);
    }

    private void showFileContent() {
        String fileName = prompt("Enter file name: ");
        vcs.showFileContent(fileName);
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private int promptInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer.");
            }
        }
    }
}
