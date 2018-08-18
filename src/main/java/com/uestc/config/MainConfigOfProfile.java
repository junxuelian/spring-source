package com.uestc.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.uestc.bean.Yellow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能；
 *
 * 开发环境、测试环境、生产环境
 * 数据源;(/A)(/B)(/C)
 * @Profile:指定组件在哪个环境的情况下才能被注册到容器中，不指定，在任何环境都能注册这个组件
 *
 * 1)、加了环境标识的bean，只有这个环境激活的时候才能注册到容器中,默认是default环境
 * 2）、写在配置类上，只有是指定环境的时候，整个配置类里面所有配置才能生效
 * 3)、没有标注环境标识的bean在，任何环境下都是加载的
 */
@Profile("test")
@Configuration
public class MainConfigOfProfile {


    @Bean
    public Yellow yellow() {
        return new Yellow();
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/wenda");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/seckill");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/imooc-services");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

}
