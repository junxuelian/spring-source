package com.uestc;

import com.uestc.bean.Person;
import com.uestc.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class MainTest {

    public static void main(String[] args) {
        ApplicationContext  applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person);

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
//        Person person = applicationContext.getBean(Person.class);
//        System.out.println(person);
//        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
//        for (String name : namesForType) {
//            System.out.println(name);
//        }

        }
}
