package com.ps.action;

import java.util.concurrent.Callable;

public record ReadStringAction(String key) implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " reading " + key);
        Thread.sleep(5000);
        return key;
    }
}
