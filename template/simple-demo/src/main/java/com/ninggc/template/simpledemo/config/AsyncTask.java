package com.ninggc.template.simpledemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Future;

@Configuration
@EnableAsync
public class AsyncTask {
    @Async
    public Future<String> asyncTask() {
        System.out.println("enter async task");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("end async task");
            return new AsyncResult<>("async result");
        }
    }
}
