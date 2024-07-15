package com.ps.task;

import java.util.UUID;

public record TaskGroup (
    UUID groupUUID
) {
    @Override
    public String toString() {
        return "TaskGroup [groupUUID: " + groupUUID.toString() + "]";
    }

    public TaskGroup {
        if(groupUUID == null) {
            throw new IllegalArgumentException("All parameters must not be null");
        }
    }
}