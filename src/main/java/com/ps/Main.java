package com.ps;

import static com.ps.task.TaskType.READ;
import static com.ps.task.TaskType.WRITE;
import static java.util.UUID.randomUUID;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.ps.action.ReadStringAction;
import com.ps.action.WriteStringAction;
import com.ps.task.Task;
import com.ps.task.TaskExecutor;
import com.ps.task.TaskExecutorService;
import com.ps.task.TaskGroup;

public class Main {

    public static void main(String[] args) throws Exception {

        TaskGroup taskGroupA = new TaskGroup(randomUUID());
        TaskGroup taskGroupB = new TaskGroup(randomUUID());

        UUID randomUUID1 = randomUUID();
        Callable<String> strReader1 = new ReadStringAction(randomUUID1);
        Task<String> readStringTask1 = new Task<>(randomUUID1, taskGroupA, READ, strReader1);

        UUID randomUUID2 = randomUUID();
        Callable<String> strReader2 = new ReadStringAction(randomUUID2);
        Task<String> readStringTask2 = new Task<>(randomUUID2, taskGroupB, READ, strReader2);
        
        UUID randomUUID3 = randomUUID();
        Callable<String> strReader3 = new ReadStringAction(randomUUID3);
        Task<String> readStringTask3 = new Task<>(randomUUID3, taskGroupA, READ, strReader3);

        UUID randomUUID4 = randomUUID();
        Callable<String> strReader4 = new ReadStringAction(randomUUID4);
        Task<String> readStringTask4 = new Task<>(randomUUID4, taskGroupB, READ, strReader4);

        UUID randomUUID5 = randomUUID();
        Callable<String> strWriter1 = new WriteStringAction(randomUUID5);
        Task<String> writeStringTask1 = new Task<>(randomUUID5, taskGroupA, WRITE, strWriter1);

        UUID randomUUID6 = randomUUID();
        Callable<String> strWriter2 = new WriteStringAction(randomUUID6);
        Task<String> writeStringTask2 = new Task<>(randomUUID6, taskGroupB, WRITE, strWriter2);

        UUID randomUUID7 = randomUUID();
        Callable<String> strWriter3 = new WriteStringAction(randomUUID7);
        Task<String> writeStringTask3 = new Task<>(randomUUID7, taskGroupA, WRITE, strWriter3);

        UUID randomUUID8 = randomUUID();
        Callable<String> strWriter4 = new WriteStringAction(randomUUID8);
        Task<String> writeStringTask4 = new Task<>(randomUUID8, taskGroupB, WRITE, strWriter4);

        TaskExecutor threadGroup1TaskExecutor = new TaskExecutorService(1);
        Future<String> task1Group1 = threadGroup1TaskExecutor.submitTask(readStringTask1);
        Future<String> task2Group2 = threadGroup1TaskExecutor.submitTask(readStringTask2);
        Future<String> task3Group1 = threadGroup1TaskExecutor.submitTask(writeStringTask1);
        Future<String> task4Group2 = threadGroup1TaskExecutor.submitTask(writeStringTask2);
        Future<String> task5Group1 = threadGroup1TaskExecutor.submitTask(readStringTask3);
        Future<String> task6Group2 = threadGroup1TaskExecutor.submitTask(readStringTask4);
        Future<String> task7Group1 = threadGroup1TaskExecutor.submitTask(writeStringTask3);
        Future<String> task8Group2 = threadGroup1TaskExecutor.submitTask(writeStringTask4);

        boolean task1Pending = true;
        boolean task2Pending = true;
        boolean task3Pending = true;
        boolean task4Pending = true;
        boolean task5Pending = true;
        boolean task6Pending = true;
        boolean task7Pending = true;
        boolean task8Pending = true;

        while(true) {
            if(task1Group1.isDone() && task1Pending) {
                System.out.println(readStringTask1 + " finished " + task1Group1.get());
                task1Pending = false;
            }

            if(task2Group2.isDone() && task2Pending) {
                System.out.println(readStringTask2 + " finished " + task2Group2.get());
                task2Pending = false;
            }

            if(task3Group1.isDone() && task3Pending) {
                System.out.println(writeStringTask1 + " finished " + task3Group1.get());
                task3Pending = false;
            }

            if(task4Group2.isDone() && task4Pending) {
                System.out.println(writeStringTask2 + " finished " + task4Group2.get());
                task4Pending = false;
            }

            if(task5Group1.isDone() && task5Pending) {
                System.out.println(readStringTask3 + " finished " + task5Group1.get());
                task5Pending = false;
            }

            if(task6Group2.isDone() && task6Pending) {
                System.out.println(readStringTask4 + " finished " + task6Group2.get());
                task6Pending = false;
            }

            if(task7Group1.isDone() && task7Pending) {
                System.out.println(writeStringTask3 + " finished " + task7Group1.get());
                task7Pending = false;
            }

            if(task8Group2.isDone() && task8Pending) {
                System.out.println(writeStringTask4 + " finished " + task8Group2.get());
                task8Pending = false;
            }
        }
    }
}