package com.example.demo.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@Component
public class AServiceFactory {
    private static List<Object> proxyImpl = new ArrayList<>();
   /* @Autowired
    private ProxyInvocaHandler proxyInvocaHandler;
    @Autowired
    private ProxyInvocaHandler proxyInvocaHandler2;*/
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BeanFactory beanFactory;

    @PostConstruct
    void init() {
        //获取多例bean
        ProxyInvocaHandler bean = beanFactory.getBean(ProxyInvocaHandler.class);
        //创建代理对象
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{AService.class}, bean);
        bean = beanFactory.getBean(ProxyInvocaHandler.class);
        Object proxy2 = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{BService.class}, bean);
        proxyImpl.add(proxy);
        proxyImpl.add(proxy2);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AService.class);
        BeanDefinitionBuilder beanDefinitionBuilder2 = BeanDefinitionBuilder.genericBeanDefinition(BService.class);
       /* defaultListableBeanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return proxy;
            }
        });*/
        //动态注册bean
        // defaultListableBeanFactory.registerBeanDefinition("aService",beanDefinitionBuilder.getBeanDefinition());
        // defaultListableBeanFactory.registerBeanDefinition("bService",beanDefinitionBuilder2.getBeanDefinition());
        //bean动态注入spring,注意名字问题
        defaultListableBeanFactory.registerSingleton("aService", proxy);
        defaultListableBeanFactory.registerSingleton("bService", proxy2);
        //测试bean是否正确
        Object aService = applicationContext.getBean("aService");
        AService bean3 = applicationContext.getBean(AService.class);
        BService bean4 = applicationContext.getBean(BService.class);
        System.out.println(aService);
        System.out.println(proxy);
    }


    public static Object getProxy(Class clazz) {


        for (Object o : proxyImpl) {
            if (clazz.getSimpleName().equalsIgnoreCase(o.getClass().getSimpleName())) {
                return o;
            }
        }

        return null;
    }
}
