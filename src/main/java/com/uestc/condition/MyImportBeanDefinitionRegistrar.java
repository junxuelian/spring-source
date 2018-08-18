package com.uestc.condition;

import com.uestc.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * AnnotationMetadata:当前类的注解信息
     * BeanDefinitionRegistry:BeanDefinition的注册类
     * 把所有需要添加到容器中的bean，调用
     * BeanDefinitionRegistry.registerBeanDefinition手工注册进来
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean definition = beanDefinitionRegistry.containsBeanDefinition("com.uestc.bean.Red");
        boolean definition2 = beanDefinitionRegistry.containsBeanDefinition("com.uestc.bean.Blue");
        if (definition && definition2) {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            beanDefinitionRegistry.registerBeanDefinition("rainBow", rootBeanDefinition);
        }
    }
}
