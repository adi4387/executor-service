package com.ps.action;

import java.util.concurrent.Callable;

public record ReadIntegerAction(Integer key) implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " reading " + key);
        Thread.sleep(5000);
        return key;
    }
}
