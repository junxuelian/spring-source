package com.uestc.test;

import com.uestc.bean.Boss;
import com.uestc.bean.Car;
import com.uestc.bean.Color;
import com.uestc.config.MainConfigOfAutowired;
import com.uestc.config.MainConfigOfProfile;
import com.uestc.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOCTest_Profile {


    //1、使用命令行动态参数
    //2、用代码方式激活某种环境
    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("test");
        applicationContext.register(MainConfigOfProfile.class);
        applicationContext.refresh();

        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String str : namesForType) {
            System.out.println(str);
        }


        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }


        applicationContext.close();
    }


}
