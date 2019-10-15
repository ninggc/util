package com.ninggc.template.springbootfastdemo.project.util;

import com.ninggc.template.springbootfastdemo.project.annotation.MethodLog;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @MethodLog
    public void test() {
        System.out.println("Test.test()");
    }
}