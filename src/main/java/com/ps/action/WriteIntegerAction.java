package com.ps.action;

import java.util.concurrent.Callable;

public record WriteIntegerAction(Integer value) implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " writing " + value);
        Thread.sleep(1000);
        return value;
    }
}
