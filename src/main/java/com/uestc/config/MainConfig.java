package com.uestc.config;

import com.uestc.bean.Person;
import org.springframework.context.annotation.*;

//配置类 == 配置文件
@Configuration //告诉spring这是一个配置类
@ComponentScans(
        value = {@ComponentScan(value="com.uestc", includeFilters = {
//                @ComponentScan.Filter(type=FilterType.ANNOTATION, classes={Controller.class, Service.class}),
//                        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes={BookService.class}),
                @ComponentScan.Filter(type=FilterType.CUSTOM,classes = MyTypeFilter.class)
        }, useDefaultFilters = false)  }
)
//ComponentScan value:指定要扫描的包
//excludeFilters = Filter[], 指定扫描的时候按照什么规则排除那些组件
//includeFilters = Filter[], 指定扫描的时候只需要包含哪些组件
//FilterType.ANNOTATION:按照注解
//FilterType.ASSIGNABLE_TYPE:按照给定的类型
public class MainConfig {
    //给容器中注册一个Bean，类型为返回值的类型，id默认是用方法名作为id
    @Bean()
    public Person person() {
        return new Person("lisi", 20);
    }

}
