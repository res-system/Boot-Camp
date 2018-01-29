package com.res_system.commons.mvc.sandbox;

public class Child extends Parent {

    @Override
    public void method() {
        System.out.println("Child");
    }

    public void method(String str) {
        System.out.println(str);
    }
}
