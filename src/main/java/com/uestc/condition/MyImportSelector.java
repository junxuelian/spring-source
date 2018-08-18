package com.uestc.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    //返回值，就是导入到容器中的组件全类名
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        //方法不能返回nulll
        return new String[] {"com.uestc.bean.Blue", "com.uestc.bean.Yellow"};
    }
}
