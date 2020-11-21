package com.sym.bboss.config;

import com.sym.bboss.annotation.BbossMapper;
import com.sym.bboss.annotation.BbossMapperHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * 处理{@link com.sym.bboss.annotation.BbossMapper}的实例化操作
 * 由于spring无法实例化接口，所以必须我们手动实例化，这里
 * 用的是动态代理{@link com.sym.bboss.annotation.BbossMapperHandler}，将其代理类注入到IOC容器中
 *
 * Created by shenYm on 2019/8/8.
 */
public class BbossInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(BbossInstantiationAwareBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if( beanClass.isAnnotationPresent(BbossMapper.class) && beanClass.isInterface()){
            Object mapper = BbossMapperHandler.getMapper(beanClass);
            LOGGER.info("为接口[{}]创建代理类[{}]",beanClass.getName(),mapper);
            return mapper;
        }
        // 其他Bean，我们返回null，让spring自己去实例化
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        // 这边是已经实例好的Bean对象，这边我们不需要再为其赋值，所以这里可以返回false
        // 而对于其他Bean，全都返回true
        return !bean.getClass().isAnnotationPresent(BbossMapper.class);
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return pvs;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        // 这里千万不要返回null，不然spring会直接返回（源码中看到的）
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
