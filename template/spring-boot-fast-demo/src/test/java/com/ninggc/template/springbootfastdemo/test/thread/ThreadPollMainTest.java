package com.ninggc.template.springbootfastdemo.test.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPollMainTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(threadFactoryBuilder.build());

        System.out.println("");
    }
}