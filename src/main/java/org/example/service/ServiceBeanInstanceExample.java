package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.instance.BeanInstanceExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Random;

@Lazy
@Service
public class ServiceBeanInstanceExample {

    private Logger logger = LoggerFactory.getLogger(ServiceBeanInstanceExample.class);

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private BeanInstanceExample beanInstanceExample;

    @PostConstruct
    private void init() {
        logger.info("ServiceBeanInstanceExample - init");
        this.beanInstanceExample = new BeanInstanceExample();
        logger.info("ServiceBeanInstanceExample - new BeanInstanceExample");
        this.beanFactory.autowireBean(this.beanInstanceExample);
        logger.info("ServiceBeanInstanceExample - autowireBean");
        this.beanInstanceExample = (BeanInstanceExample) this.beanFactory.initializeBean(this.beanInstanceExample, "beanName" + new Random().nextInt());
        logger.info("ServiceBeanInstanceExample - initializeBean");
    }

    public boolean isInitialized() {
        return this.beanInstanceExample.isInstanceCreated();
    }
}
