package com.ps.action;

import java.util.UUID;
import java.util.concurrent.Callable;

public record ReadStringAction(UUID taskId) implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " processing + " + taskId);
        Thread.sleep(10000);
        return "reading";
    }
}