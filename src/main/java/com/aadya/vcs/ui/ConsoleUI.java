
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
