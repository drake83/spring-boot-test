package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceExample {

    private Logger logger = LoggerFactory.getLogger(ServiceExample.class);

    public void doSomething() {
        logger.info("ServiceExample doSomething");
    }
}
