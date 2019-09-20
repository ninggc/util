package com.ninggc.template.springbootfastdemo.util;

import com.ninggc.template.springbootfastdemo.annotation.MethodLog;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @MethodLog
    public void test() {
        System.out.println("Test.test()");
    }
}
