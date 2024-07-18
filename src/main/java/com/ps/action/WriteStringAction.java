package com.ps.action;

import java.util.concurrent.Callable;

public record WriteStringAction(String value) implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " writing " + value);
        Thread.sleep(1000);
        return value;
    }
}
