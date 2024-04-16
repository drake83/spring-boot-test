package org.example.instance;

import jakarta.annotation.PostConstruct;
import org.example.service.ServiceExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BeanInstanceExample {

    private Logger logger = LoggerFactory.getLogger(BeanInstanceExample.class);

    @Autowired
    private ServiceExample serviceExample;

    private boolean instanceCreated = false;

    public BeanInstanceExample() {
        logger.info("BeanInstanceExample - constructor");
    }

    @PostConstruct
    private void init() {
        logger.info("BeanInstanceExample - init");
        this.instanceCreated = true;
    }

    public boolean isInstanceCreated() {
        return this.serviceExample != null && instanceCreated;
    }
}
