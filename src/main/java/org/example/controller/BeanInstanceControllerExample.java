package org.example.controller;


import org.example.command.BeanInstanceCommandExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanInstanceControllerExample {

    private final static Logger logger = LoggerFactory.getLogger(BeanInstanceControllerExample.class);

    @Autowired
    private BeanFactory beanFactory;

    @GetMapping("/instance")
    public ResponseEntity<Boolean> instanceApi() {
        logger.info("instanceApi");
        BeanInstanceCommandExample commandExample = beanFactory.getBean(BeanInstanceCommandExample.class);
        Boolean result = commandExample.isInitialized();
        logger.info("instanceApi result {}", result);
        return ResponseEntity.ok(result);
    }
}
