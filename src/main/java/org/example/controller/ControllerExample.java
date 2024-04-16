package org.example.controller;


import org.example.command.CommandExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerExample {

    private final static Logger logger = LoggerFactory.getLogger(ControllerExample.class);

    @Autowired
    private BeanFactory beanFactory;

    @GetMapping("/test/{id}")
    public ResponseEntity<String> commandApi(@PathVariable Integer id) {
        logger.info("commandApi with id {}", id);
        try {
            CommandExample commandExample = beanFactory.getBean(CommandExample.class, id);
            commandExample.doSomething();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("ok");
    }
}
