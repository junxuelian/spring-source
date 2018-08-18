package com.uestc.test;

import com.uestc.aop.MathCaculator;
import com.uestc.config.MainConfigOfAop;
import com.uestc.ext.ExtConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

    @Test
    public void test01() {
        applicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
        });


        applicationContext.close();
    }

}
