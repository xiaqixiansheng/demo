package com.example.demo.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Component
@Scope("prototype")
public class ProxyInvocaHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this,args);
        }
        System.out.println("proxy handle" + proxy + method.getName() + args);
        return "proxy handle" + proxy + method.getName() + args;
    }
}
