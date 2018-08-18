package com.uestc.test;

import com.uestc.bean.Boss;
import com.uestc.bean.Car;
import com.uestc.bean.Color;
import com.uestc.bean.Person;
import com.uestc.config.MainConfigOfAutowired;
import com.uestc.config.MainConfigOfPropertyValues;
import com.uestc.dao.BookDao;
import com.uestc.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowried {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

    @Test
    public void test01() {
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService.toString());


        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);

        applicationContext.close();
    }



}
