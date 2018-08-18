package com.uestc.config;

import com.uestc.bean.Car;
import com.uestc.bean.Color;
import com.uestc.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配：
 *       spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 * 1)@Autowired:自动注入
 *      1）、优先按照类型去容器中找对应的组件
 *      2）、如果找到多个相同类型的组件，再将属性名作为组件id去容器中查找
 *      3)、@Qualifier指定需要装配的组件的id，而不是使用属性名
 *      4）、默认一定要将属性赋值好，没有就会报错
 *           可以使用@Autowired(required=false)
 *      5)、@Primary 让spring进行自动装配的时候，默认使用首选的bean
 *      也可以继续使用@Qualifier指定需要装配的bean的名字
 * 2）、Spring还支持使用@Resource（JSR250）和@Inject（JSR303）[java规范的注解]
 *      @Resource:
 *          可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配的
 *          没有能支持@Primary功能没有支持@Autowired（required = false）
 *      @Inject:
 *          需要导入javax.inject的包，和autowired功能一样，没有支持@Autowired（required = false）
 * @Autowired：spring定义的，@Resoure、@Inject都是java规范
 * 3）、@Autowired:构造器，参数，方法，属性，都是从容器中获取组件的值
 *      1)、标注在方法位置
 *      2）、标注在构造器上，如果组件只有一个有参构造器，这个有参构造器的@Autowried可以省略，参数位置的组件还是可以自动从容器中获取
 *      3）、放在参数位置上
 *4）、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext, BeanFactory,xxx）
 *      自定义组件实现xxxAware;在创建对象的时候，会调用接口规定的方法注入相关组件；Aware
 *      xxxAware,功能使用xxxProcessor;
 *
 *
 */
@ComponentScan({"com.uestc.service", "com.uestc.dao", "com.uestc.controller", "com.uestc.bean"})
@Configuration
public class MainConfigOfAutowired {

    @Primary
    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLable("2");
        return bookDao;
    }

    /**
     * Bean标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }

}
