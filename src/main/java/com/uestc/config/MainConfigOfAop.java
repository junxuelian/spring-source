package com.uestc.config;

import com.uestc.aop.LogAspects;
import com.uestc.aop.MathCaculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP
 * 1、导入aop模块，spring aop（spring-aspects）
 * 2、定义一个业务逻辑类(MathCaculator),在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常等等）
 * 3、定义一个日志切面类(LogAspects)，切面类里面的方法需要动态感知MathCaculator.div运行到哪
 *          通知方法：
 *                  前置通知(@Before)：logStart在目标方法（div）运行之前运行
 *                  后置通知(@After)：logEnd在目标方法（div）运行结束之后运行
 *                  返回通知((@AfterReturning)：logReturn在目标方法（div）正常返回之后运行
 *                  异常通知(@AfterThrowing)：logException在比目标方法（div）出现异常以后运行
 *                  环绕通知(@Around)：动态代理，手动推进目标方法运行（jionPoint.procced()）
 * 4、给切面类的目标方法标注何时何地运行（通知注解）
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入容器中
 *6、必须告诉spring哪个是切面类（给切面类上加注解）
 * 7、给配置类中加上@EnableAspectJAutoProxy[开启基于注解的aop模式]
 *
 * 三步：
 * 1）、将业务逻辑组件和切面类都加入到容器中，告诉spring哪个是切面类（@Aspect）
 * 2)、在切面类上的每一个通知方法上标注通知注解，告诉spring何时何地运行（切入点表达式）
 * 3）、开启基于aop模式
 *
 * AOP原理:[给容器中注册了什么组件，这个组件什么时候工作，包括这个组件的功能是什么]
 *      @EnableAspectJAutoProxy
 * 1、@EnableAspectJAutoProxy是什么？
 *      @Import(AspectJAutoProxyRegistrar.class）给容器中导入AspectJAutoProxyRegistrar组件
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean；
 *          给容器中注册一个AnnotationAwareAspectJAutoProxyCreator;
 * 2、AnnotationAwareAspectJAutoProxyCreator
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *          ->AbstractAdvisorAutoProxyCreator
 *              ->AbstractAutoProxyCreator
 *                  implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *              关注后置处理器（bean初始化完成前后做事情）、自动装配BeanFactory
 *  AbstractAutoProxyCreator.setBeanFactory()
 *  AbstractAutoProxyCreator.有后置处理器的逻辑；
 *  AbstractAutoProxyCreator.postProcessBeforeInstantiation()
 *
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory()->initBeanFactory()
 *
 *  AspectJAwareAdvisorAutoProxyCreator
 *
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *
 * 流程：
 *      1）、传入配置类，创建ioc容器
 *      2）、注册配置类，调用refresh（）刷新容器
 *      3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          1）、先获取ioc容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *          2)、给容器中加别的BeanPostProcessor
 *          3)、优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *          4）、再给容器中注册实现了Ordered接口的BeanPostProcessor
 *          5）、注册没实现优先级接口的BeanPostProcessor
 *          6）、注册BeanPostProcessor,实际上就是创建BeanPostProcessor对象，保存在容器中
 *               创建internalAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator)的BeanPostProcessor
 *               1)、创建Bean实例
 *               2）、populateBean给Bean的各种属性赋值
 *               3)、initializeBean,初始化bean
 *                     1)、invokeAwareMethods()，处理Aware接口的方法回调
 *                     2）、applyBeanPostProcessorsBeforeInitialization（），应用后置处理器的postProcessBeforeInitialization
 *                     3）、invokeAwareMethods()执行自定义的初始化方法
 *                     4）、applyBeanPostProcessorsAfterInitialization(),执行后置处理器的postProcessAfterInitialization
 *               4)、BeanPostProcessor(AnnotationAspectJAutoProxyCreator)创建成功
 *           7）、把BeanPostProcessor注册到BeanFactory中
 *                beanFactory.addBeanPostProcessor(PostProcessor);
 * =============以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程======
 *            4)、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作，创建剩下单实例Bean
 *                  1）、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
 *                  getBean->doGetBean()->getSingleton()->
 *                  2)、创建Bean
 *                      [AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation]
 *                      1）、先从缓存中获取当前bean,如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
 *                              只要创建好的Bean都会被缓存起来
 *                      2）、createBean(),创建bean；AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前尝试返回一个bean的实例
 *                          [BeanPostProcessor是在Bean对象创建完成初始化前后调用]
 *                          [InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的]
 *                          1)、resolveBeforeInstantiation(beanName, mbdToUse),解析BeforeInstantiation
 *                              希望后置处理器能在此返回一个代理对象，如果能返回代理对象就使用，如果不能就继续
 *                              1）、后置处理器先尝试返回对象
 *                                  bean = applyBeanPostProcessorsBeforeInstantiation()
 *                                  拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor就执行
 *                                  后置处理器的postProcessBeforeInstantiation
 *                                  if (bean != null) {
 * 						                bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 * 					                }
 *                          2）、doCreateBean(beanName, mbdToUse, args)真正去创建一个Bean实例
 *                          3）、
 *
 *AnnotationAwareAspectJAutoProxyCreator[InstantiationAwareBeanPostProcessor]的作用
 * 1）、每一个Bean创建之前，调用postProcessorBeforeInstantiation();
 *      关心MathCalculator和LogAspect的创建
 *      1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 *      2）、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean
 *      或者是否是切面(@Aspect)
 *      3）、是否需要跳过
 *           1）、获取候选的增强器（切面里面的通知方法）[List<Advisor> candidateAdvisor]
 *              每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor.
 *              判断每一个增强器是否是AspectJPointcutAdvisor类型的,返回true
 *           2）、永远返回false
 *
 *2)、创建对象
 * postProcessAfterInitialization
 *      return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 *      1)、获取当前bean的所有增强器（通知方法） Object[] specificInterceptors
 *          1、找到获选的所有增强器(找哪些通知方法是需要切入当前bean方法的)
 *          2、获取到能在bean使用的增强器
 *          3、给增强器排序
 *      2）、保存当前bean在advicedBeans中
 *      3）、如果当前bean对象需要增强，创建当前bean的代理对象；
 *           1）、获取所有增强器（通知方法）
 *           2）、保存到proxyFactory
 *           3）、创建代理对象:spring可以自己决定
 *                  JdkDynamicAopProxy（config）
 *                  ObjenesisCglibAopProxy(config)
 *
 * 3）、目标方法执行
 *          容器中保存了组件的代理对象（cglib增强后的对象）,这个对象里面保存了详细信息（比如增强器、
 *          目标对象，XXX）；
 *          1）、CglibAopProxy.intercept(),拦截目标方法的执行
 *          2)、根据ProxyFactory对象获取将要执行的目标方法拦截器链
 *              List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *              1)、List<Object> interceptorList保存所有拦截器
 *              一个默认的ExposeInvocationInterceptor 和 4个增强器；
 *              2）、遍历所有的增强器，将其转为Interceptor
 *              registry.getInterceptors(advisor);
 *              3）、将增强器转为List<MethodInterceptor>,
 *                  如果是MethodInterceptor直接加入到集合中
 *                  如果不是，使用AdvisorAdapter将增强器转为Interceptor
 *                  转换完成返回MethodInterceptor的数组
 *
 *         3）、如果没有拦截器链，直接执行目标方法
 *              拦截器链（每一个通知方法有被包装为拦截器，利用MethodInterceptor机制）
 *         4）、如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等信息传入创建一个CglibMethodInvocation对象，
 *         并调用Object retVal = mi.proceed（）
 *         5）、拦截器链的触发过程
 *              1）、如果没有拦截器执行目标方法，或者拦截器索引和拦截器数组减一大小一样（执行到了最后一个拦截器）执行目标方法，
 *              2）、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回后再来执行。
 *                  拦截器链的机制，保证通知方法和目标方法执行顺序
 *
 * 总结：
 *      1）、@EnableAspectJAutoProxy开启AOP
 *      2）、@EnableAspectJAutoProxy会给容器中注册一个组件AnnotationAwareAspectJAutoProxyCreator
 *      3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 *      4）、容器的创建流程：
 *               1）、registerBeanPostProcessor（）注册后置处理器，会创建AnnotationAwareAspectJAutoProxyCreator对象
 *               2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
 *                   1）、创建业务逻辑组件和切面组件
 *                   2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 *                   3）、组件创建完成之后，判断组件是否需要增强
 *                        是，切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象
 *       5）、执行目标方法
 *          1）、代理对象执行目标方法
 *          2）、CglibAopProxy.intercept()
 *              1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *              2）、利用拦截器链式机制，依次进入每一个拦截器进行执行
 *              3）、效果
 *                      正常执行：前置通知--》目标方法--》后置通知-》返回通知
 *                     异常执行：前置通知--》目标方法--》后置通知-》异常通知
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    @Bean
    public MathCaculator caculator() {
        return new MathCaculator();
    }

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
