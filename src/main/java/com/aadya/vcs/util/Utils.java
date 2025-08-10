package com.yourname.vcs.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Format LocalDateTime to readable string
    public static String formatTimestamp(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    // Validate file name (simple example)
    public static boolean isValidFileName(String fileName) {
        return fileName != null && !fileName.trim().isEmpty() && fileName.matches("[\\w\\.\\-]+");
    }

    // Simple yes/no prompt parser
    public static boolean parseYesNo(String input) {
        if (input == null) return false;
        input = input.trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}

