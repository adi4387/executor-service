package com.ps;

import com.ps.action.ReadIntegerAction;
import com.ps.action.ReadStringAction;
import com.ps.action.WriteIntegerAction;
import com.ps.action.WriteStringAction;
import com.ps.task.Task;
import com.ps.task.TaskExecutor;
import com.ps.task.TaskExecutorService;
import com.ps.task.TaskGroup;

import java.util.concurrent.*;

import static com.ps.task.TaskType.READ;
import static com.ps.task.TaskType.WRITE;
import static java.util.UUID.randomUUID;

public class Main {

    public static void main(String[] args) {

        TaskGroup taskGroupA = new TaskGroup(randomUUID());
        TaskGroup taskGroupB = new TaskGroup(randomUUID());

        Callable<String> strReader1 = new ReadStringAction("task1Group1");
        Task<String> readStringTask1 = new Task<>(randomUUID(), taskGroupA, READ, strReader1);

        Callable<String> strReader2 = new ReadStringAction("task2Group2");
        Task<String> readStringtask2 = new Task<>(randomUUID(), taskGroupB, READ, strReader2);
        
        Callable<String> strReader3 = new ReadStringAction("task3Group1");
        Task<String> readStringTask3 = new Task<>(randomUUID(), taskGroupA, READ, strReader3);

        Callable<String> strReader4 = new ReadStringAction("task4Group2");
        Task<String> readStringTask4 = new Task<>(randomUUID(), taskGroupB, READ, strReader4);

        Callable<String> strWriter1 = new WriteStringAction("task5Group1");
        Task<String> writeStringTask5 = new Task<>(randomUUID(), taskGroupA, WRITE, strWriter1);

        Callable<String> strWriter2 = new WriteStringAction("task6Group2");
        Task<String> writeStringTask6 = new Task<>(randomUUID(), taskGroupB, WRITE, strWriter2);

        Callable<String> strWriter3 = new WriteStringAction("task7Group1");
        Task<String> writeStringTask7 = new Task<>(randomUUID(), taskGroupA, WRITE, strWriter3);

        Callable<String> strWriter4 = new WriteStringAction("task8Group2");
        Task<String> writeStringTask8 = new Task<>(randomUUID(), taskGroupB, WRITE, strWriter4);

        Callable<Integer> intReader1 = new ReadIntegerAction(91);
        Task<Integer> readIntegerTask9 = new Task<>(randomUUID(), taskGroupA, READ, intReader1);

        Callable<Integer> intReader2 = new ReadIntegerAction(102);
        Task<Integer> readIntegerTask10 = new Task<>(randomUUID(), taskGroupA, READ, intReader2);

        Callable<Integer> intReader3 = new ReadIntegerAction(111);
        Task<Integer> readIntegerTask11 = new Task<>(randomUUID(), taskGroupA, READ, intReader3);

        Callable<Integer> intReader4 = new ReadIntegerAction(122);
        Task<Integer> readIntegerTask12 = new Task<>(randomUUID(), taskGroupA, READ, intReader4);

        Callable<Integer> intWriter1 = new WriteIntegerAction(131);
        Task<Integer> writeIntegerTask13 = new Task<>(randomUUID(), taskGroupA, WRITE, intWriter1);

        Callable<Integer> intWriter2 = new WriteIntegerAction(142);
        Task<Integer> writeIntegerTask14 = new Task<>(randomUUID(), taskGroupB, WRITE, intWriter2);

        Callable<Integer> intWriter3 = new WriteIntegerAction(151);
        Task<Integer> writeIntegerTask15 = new Task<>(randomUUID(), taskGroupA, WRITE, intWriter3);

        Callable<Integer> intWriter4 = new WriteIntegerAction(162);
        Task<Integer> writeIntegerTask16 = new Task<>(randomUUID(), taskGroupB, WRITE, intWriter4);

        TaskExecutor taskExecutor = new TaskExecutorService(2);
        CompletableFuture<String> task1Group1 = (CompletableFuture<String>) taskExecutor.submitTask(readStringTask1);
        CompletableFuture<String> task2Group2 = (CompletableFuture<String>) taskExecutor.submitTask(readStringtask2);
        CompletableFuture<String> task3Group1 = (CompletableFuture<String>) taskExecutor.submitTask(readStringTask3);
        CompletableFuture<String> task4Group2 = (CompletableFuture<String>) taskExecutor.submitTask(readStringTask4);
        CompletableFuture<String> task5Group1 = (CompletableFuture<String>) taskExecutor.submitTask(writeStringTask5);
        CompletableFuture<String> task6Group2 = (CompletableFuture<String>) taskExecutor.submitTask(writeStringTask6);
        CompletableFuture<String> task7Group1 = (CompletableFuture<String>) taskExecutor.submitTask(writeStringTask7);
        CompletableFuture<String> task8Group2 = (CompletableFuture<String>) taskExecutor.submitTask(writeStringTask8);
        CompletableFuture<Integer> task9Group1 = (CompletableFuture<Integer>) taskExecutor.submitTask(readIntegerTask9);
        CompletableFuture<Integer> task10Group2 = (CompletableFuture<Integer>) taskExecutor.submitTask(readIntegerTask10);
        CompletableFuture<Integer> task11Group1 = (CompletableFuture<Integer>) taskExecutor.submitTask(readIntegerTask11);
        CompletableFuture<Integer> task12Group2 = (CompletableFuture<Integer>) taskExecutor.submitTask(readIntegerTask12);
        CompletableFuture<Integer> task13Group1 = (CompletableFuture<Integer>) taskExecutor.submitTask(writeIntegerTask13);
        CompletableFuture<Integer> task14Group2 = (CompletableFuture<Integer>) taskExecutor.submitTask(writeIntegerTask14);
        CompletableFuture<Integer> task15Group1 = (CompletableFuture<Integer>) taskExecutor.submitTask(writeIntegerTask15);
        CompletableFuture<Integer> task16Group2 = (CompletableFuture<Integer>) taskExecutor.submitTask(writeIntegerTask16);

        task1Group1.thenAccept(result -> System.out.println("task1Group1 result: " + result));
        task2Group2.thenAccept(result -> System.out.println("task2Group2 result: " + result));
        task3Group1.thenAccept(result -> System.out.println("task3Group1 result: " + result));
        task4Group2.thenAccept(result -> System.out.println("task4Group2 result: " + result));
        task5Group1.thenAccept(result -> System.out.println("task5Group1 result: " + result));
        task6Group2.thenAccept(result -> System.out.println("task6Group2 result: " + result));
        task7Group1.thenAccept(result -> System.out.println("task7Group1 result: " + result));
        task8Group2.thenAccept(result -> System.out.println("task8Group2 result: " + result));
        task9Group1.thenAccept(result -> System.out.println("task9Group1 result: " + result));
        task10Group2.thenAccept(result -> System.out.println("task10Group2 result: " + result));
        task11Group1.thenAccept(result -> System.out.println("task11Group1 result: " + result));
        task12Group2.thenAccept(result -> System.out.println("task12Group2 result: " + result));
        task13Group1.thenAccept(result -> System.out.println("task13Group1 result: " + result));
        task14Group2.thenAccept(result -> System.out.println("task14Group2 result: " + result));
        task15Group1.thenAccept(result -> System.out.println("task15Group1 result: " + result));
        task16Group2.thenAccept(result -> System.out.println("task16Group2 result: " + result));
        taskExecutor.shutdown();
    }

}