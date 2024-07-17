package com.ps.task;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskExecutorService implements TaskExecutor {

    private final ExecutorService executorService;
    private final Map<UUID, Queue<Runnable>> threadGroupTaskQueue;

    private final Map<UUID, Lock> threadGroupLocks;

    public TaskExecutorService(final int concurrency) {
        this.executorService = Executors.newFixedThreadPool(concurrency);
        threadGroupTaskQueue = new ConcurrentHashMap<>();
        threadGroupLocks = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Future<T> submitTask(Task<T> task) {
        System.out.println("Submitting task: " + Thread.currentThread().getName() + " for " + task);
        var groupUUID = task.taskGroup().groupUUID();
        threadGroupLocks.putIfAbsent(groupUUID, new ReentrantLock());
        threadGroupTaskQueue.putIfAbsent(groupUUID, new ConcurrentLinkedQueue<>());

        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        Runnable futureTask = () -> {
            Lock groupLock = threadGroupLocks.get(groupUUID);
            groupLock.lock();
            System.out.println("Lock acquired by : " + Thread.currentThread().getName() + " for " + task);
            try {
                T result = task.taskAction().call();
                completableFuture.complete(result);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            } finally {
                groupLock.unlock();
                System.out.println("Lock released: " + Thread.currentThread().getName() + " for " + task);
            }
        };
        System.out.println("Task submitted: " + Thread.currentThread().getName() + " for " + task);

        threadGroupTaskQueue.get(groupUUID).add(futureTask);
        executorService.submit(threadGroupTaskQueue.get(groupUUID).poll());
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
