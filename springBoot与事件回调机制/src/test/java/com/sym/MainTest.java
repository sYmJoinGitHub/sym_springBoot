package com.sym;

import org.junit.Test;

/**
 * @author shenym
 * @date 2020/3/28 17:58
 */

public class MainTest {
    
    public static void main(String[] args) {
        StackTraceElement[] stackTraces = new RuntimeException().getStackTrace();
        for (StackTraceElement traceElement : stackTraces) {
            String className = traceElement.getClassName();
            String fileName = traceElement.getFileName();
            String methodName = traceElement.getMethodName();
            System.out.println(className + "\t" + methodName + "\t" + fileName);
        }
    }

    @Test
    public void test(){

    }
}
