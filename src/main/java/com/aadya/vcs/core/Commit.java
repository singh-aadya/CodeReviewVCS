package com.yourname.vcs.core;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Commit {
    private final String commitId;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<Commit> parents;  // For merges, usually one parent, multiple if merge
    private final Map<String, FileVersion> fileVersions;  // filename -> snapshot of file

    public Commit(String commitId, String message, List<Commit> parents,
                  Map<String, FileVersion> fileVersions) {
        this.commitId = commitId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.parents = parents;
        this.fileVersions = fileVersions;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<Commit> getParents() {
        return parents;
    }

    public Map<String, FileVersion> getFileVersions() {
        return fileVersions;
    }
}
