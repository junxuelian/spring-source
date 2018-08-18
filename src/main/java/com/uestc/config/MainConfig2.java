package com.uestc.config;

import com.uestc.bean.Color;
import com.uestc.bean.ColorFactoryBean;
import com.uestc.bean.Person;
import com.uestc.bean.Red;
import com.uestc.condition.LinuxCondition;
import com.uestc.condition.MyImportBeanDefinitionRegistrar;
import com.uestc.condition.MyImportSelector;
import com.uestc.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
//导入组件，id默认是组件的全类名
public class MainConfig2 {

    /**
     * prototype 是多实例的, ioc容器启动并不会去调用方法创建对象放在容器中，
     *             每次获取的时候才会调用方法创建对象。
     * singleton 单实例（默认值）, ioc容器启动会调用方法创建对象放到ioc容器中
     *           以后每次获取就是直接从容器中取（map.get()）。
     * request 同一次请求创建一个实例
     * session 同一个session创建一个实例
     *
     * 懒加载：
     *      单例bean，默认在容器启动时候创建对象，
     *      懒加载，容器启动不创建对象，第一次使用（获取）Bean才创建对象。
     * @return
     */
    @Lazy
    @Scope
    @Bean
    public Person person() {
        System.out.println("给容器中添加person");
        return new Person("张三",  25);
    }

    /**
     * @Conditional({Condition}),按照一定条件进行判断，满足条件给容器中注册bean
     * 如果系统是windows，给容器中注册（"bill"）
     * 如果系统是linux。给容器中注册（"linus"）
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linux")
    public Person person02() {
        return new Person("linus",48);
    }

    /**
     * 给容器中注册组件
     * 1）、包扫描+组件注解（@Controller/@Service/@Repository/@Component）[局限自己写的类]
     * 2)、@Bean[导入的第三方包里面的组件]
     * 3）、@Import[快速给容器中导入一个组件]:
     *      1、@Import[快速给容器中导入一个组件]:容器中就会自动注册组件,id默认是全类名
     *      2、@ImportSelector：返回需要导入的组件的全类名数组
     *      3、@ImportBeanDefinitionRegistrar:手动注册bean到容器中
     * 4）、使用spring提供的FactoryBean(工厂Bean)
     *      1、默认获取到的是工厂Bean的是调用getObject创建的对象
     *      2、要获取工厂Bean本身，我们需要给id前面加一个&
     */

    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }

}
