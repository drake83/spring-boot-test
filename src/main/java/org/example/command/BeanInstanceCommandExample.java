package org.example.command;


import org.example.service.ServiceBeanInstanceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BeanInstanceCommandExample {

    @Autowired
    private ServiceBeanInstanceExample serviceBeanInstanceExample;

    public BeanInstanceCommandExample() {
    }

    public boolean isInitialized() {
        return serviceBeanInstanceExample.isInitialized();
    }
}
