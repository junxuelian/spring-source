package com.uestc.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

    /**
     *
     * @param conditionContext,判断条件能使用的上下文（环境）
     * @param annotatedTypeMetadata，注释信息
     * @return
     */

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //ToDo是否linux系统
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();

        ClassLoader classLoader = conditionContext.getClassLoader();

        ConfigurableEnvironment environment = (ConfigurableEnvironment) conditionContext.getEnvironment();

        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        String property = environment.getProperty("os.name");

        boolean definition = registry.containsBeanDefinition("person");
        if (property.contains("Linux")) {
            return true;
        }

        return false;
    }
}
