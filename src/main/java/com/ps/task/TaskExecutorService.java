package com.ps.task;

import com.ps.exception.DomainException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskExecutorService implements TaskExecutor {

    private final ExecutorService executorService;
    private final Map<UUID, Lock> threadGroupLocks;

    public TaskExecutorService(final int concurrency) {
        this.executorService = Executors.newFixedThreadPool(concurrency);
        threadGroupLocks = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Future<T> submitTask(Task<T> task) {
        var groupUUID = task.taskGroup().groupUUID();
        threadGroupLocks.putIfAbsent(groupUUID, new ReentrantLock());

        CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync(() -> {
                    Lock groupLock = threadGroupLocks.get(groupUUID);
                    try {
                        groupLock.lock();
                        return task.taskAction().call();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new DomainException("Error while executing action", e);
                    } finally {
                        groupLock.unlock();
                    }
                }, executorService)
                .handle((result, ex) -> {
                    System.out.println(ex.getMessage());
                    return null;
                });
        System.out.println("Adding future task in queue: " + Thread.currentThread().getName() + " for " + task);
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
