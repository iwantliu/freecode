package com.freecode.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-19 17:58
 */
public class ReferenceBean implements ApplicationContextAware {
    DefaultListableBeanFactory beanFactory = null;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        beanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                .getBeanFactory();
    }

    public void registerSingleton(Class clazz) {
        try {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ReferenceBean.class);
            builder.addConstructorArgValue(clazz);
            builder.setFactoryMethod("create");
            beanFactory.registerBeanDefinition(clazz.getName(), builder.getBeanDefinition());
            System.out.println("remote 注册" + clazz.getName() + "到Spring容器中...");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T create(Class<T> clazz) {
        RemoteProxy<T> proxy = new RemoteProxy<>();
        System.out.println("ReferenceBean.create:" + clazz.getName());
        T t = (T) proxy.bind(clazz);
        return t;
    }

    private static class RemoteProxy<T> implements InvocationHandler {
        private Object bind(Object object) {
            // 取得代理对象
            return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this); // 要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}
