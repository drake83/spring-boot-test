package org.example.command;


import org.example.service.ServiceExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommandExample {

    private Logger logger = LoggerFactory.getLogger(CommandExample.class);

    private Integer id;

    @Autowired
    private ServiceExample serviceExample;

    public CommandExample() {
        logger.info("CommandExample constructor empty - {}", this);
        logger.info("CommandExample constructor empty");
    }

    public CommandExample(Integer id) {
        logger.info("CommandExample constructor with params - {}", this);
        logger.info("CommandExample constructor with id {}", id);
        this.id = id;
    }

    public void doSomething() throws Exception {
        if (this.id == null) {
            logger.info("Id is null");
            throw new Exception("id is null");
        } else {
            logger.info("Id is not null. Do something");
            serviceExample.doSomething();
        }
    }
}
