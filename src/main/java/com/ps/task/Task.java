package com.ps.task;

import java.util.UUID;
import java.util.concurrent.Callable;

public record Task<T> (
    UUID taskUUID,
    TaskGroup taskGroup,
    TaskType taskType,
    Callable<T> taskAction
) {
    @Override
    public String toString() {
        return "Task [taskUUID: " + taskUUID.toString() + ", taskGroup: " + taskGroup + "]";
    }

    public Task {
        if(taskUUID == null || taskGroup == null || taskType == null || taskAction == null) {
            throw new IllegalArgumentException("All parameters must not be null");
        }
    }
}