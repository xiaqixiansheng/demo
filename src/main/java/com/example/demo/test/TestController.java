package com.example.demo.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AServiceFactory aServiceFactory;
    @Autowired

    private AService aService;

    @Autowired

    private BService bService;
    @Autowired
    ProxyInvocaHandler proxyInvocaHandler;


    @Autowired
    private BeanFactory beanFactory;
    @GetMapping("/a")
    public String helloa() {


        return aService.helloA();
    }

    @GetMapping("/b")
    public String hellob() {


        return bService.helloB();
    }

    @GetMapping("/f")
    public String hellof() {

        Object proxy = aServiceFactory.getProxy(AService.class);
        return proxy.toString();
    }

    @GetMapping("/ff")
    public String helloff() {

        Object proxy = AServiceFactory.getProxy(AService.class);
        return proxy.toString();
    }

    @GetMapping("/fff")
    public String hellofff() {
        AServiceFactory aServiceFactory = new AServiceFactory();
        Object proxy = aServiceFactory.getProxy(AService.class);
        return proxy.toString();
    }

    @GetMapping("/p")
    public String hellop() {
        //多例测试
        return beanFactory.getBean(ProxyInvocaHandler.class).toString();
    }
}
