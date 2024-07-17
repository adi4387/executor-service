package com.ps.task;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;

public class TaskExecutorService implements TaskExecutor {

    private final ExecutorService executorService;
    private final Map<UUID, Queue<Task<?>>> taskQueueThreadGroup;

    public TaskExecutorService(final int concurrency) {
        this.executorService = Executors.newFixedThreadPool(concurrency);
        taskQueueThreadGroup = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Future<T> submitTask(Task<T> task) {
        System.out.println("Submitting task: " + task);
        taskQueueThreadGroup.computeIfAbsent(task.taskGroup().groupUUID(), k -> new ConcurrentLinkedQueue<>());
        taskQueueThreadGroup.get(task.taskGroup().groupUUID()).add(task);
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                Task<T> t = (Task<T>) taskQueueThreadGroup.get(task.taskGroup().groupUUID()).poll();
                T result = t.taskAction().call();
                completableFuture.complete(result);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        }, executorService);
        return completableFuture;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
