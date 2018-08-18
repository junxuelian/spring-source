package com.uestc.test;

import com.uestc.aop.MathCaculator;
import com.uestc.config.MainConfigOfAop;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);

    @Test
    public void test01() {
        MathCaculator mathCaculator = applicationContext.getBean(MathCaculator.class);
        mathCaculator.div(1, 1);
    }

}
