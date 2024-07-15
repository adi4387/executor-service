package com.ps.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskExecutorService implements TaskExecutor {

    private final ExecutorService executorService;

    public TaskExecutorService(final int concurrency) {
        this.executorService = Executors.newFixedThreadPool(concurrency);
    }

    @Override
    public <T> Future<T> submitTask(Task<T> task) {
        System.out.println("Submitting task: " + task);
        return executorService.submit(task.taskAction());
    }
}
