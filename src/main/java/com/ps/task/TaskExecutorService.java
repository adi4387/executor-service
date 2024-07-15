package com.ps.task;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class TaskExecutorService implements TaskExecutor {

    private final ExecutorService executorService;
    private final Map<UUID, BlockingQueue<Task<?>>> taskQueueThreadGroup;

    public TaskExecutorService(final int concurrency) {
        this.executorService = Executors.newFixedThreadPool(concurrency);
        taskQueueThreadGroup = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Future<T> submitTask(Task<T> task) {
        System.out.println("Submitting task: " + task);
        taskQueueThreadGroup.computeIfAbsent(task.taskGroup().groupUUID(), k -> new LinkedBlockingQueue<>());
        taskQueueThreadGroup.get(task.taskGroup().groupUUID()).add(task);

        try {
            return (Future<T>) executorService.submit(taskQueueThreadGroup.get(task.taskGroup().groupUUID()).take().taskAction());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
