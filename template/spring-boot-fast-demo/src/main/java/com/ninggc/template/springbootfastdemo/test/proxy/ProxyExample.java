package com.ninggc.template.springbootfastdemo.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyExample {
    static interface IB {

        Integer getI();

        void setI(Integer i);
    }

    static class B implements IB {

        private Integer i;

        @Override
        public Integer getI() {
            return i;
        }

        @Override
        public void setI(Integer i) {
            this.i = i;
        }
    }
    static class A implements InvocationHandler {
        private Object obj;

        public A(Object object) {
            this.obj = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + " is use");
            return method.invoke(obj, args);
        }
    }

    public static void main(String[] args) {
        B b = new B();
        IB ib = (IB) Proxy.newProxyInstance(b.getClass().getClassLoader(), new Class<?>[]{IB.class}, new A(b));
        ib.setI(1);
    }
}
