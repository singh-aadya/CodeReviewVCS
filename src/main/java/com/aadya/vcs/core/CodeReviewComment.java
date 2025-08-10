package com.aadya.vcs.core;

import java.time.LocalDateTime;

public class CodeReviewComment {
    private final String fileName;
    private final int lineNumber;
    private final String commentText;
    private final String author;
    private final LocalDateTime timestamp;

    public CodeReviewComment(String fileName, int lineNumber, String commentText, String author) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.commentText = commentText;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + author + " (line " + lineNumber + "): " + commentText;
    }
}

