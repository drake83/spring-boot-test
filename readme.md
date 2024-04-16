## TEST with standard build
> mvn clean install

> java -jar ./target/spring-boot-test-1.0-SNAPSHOT.jar

Test Command pattern
> curl http://localhost:8080/test/1

- 2024-04-16T21:58:23.539+02:00  INFO 26435 --- [nio-8080-exec-1] o.example.controller.ControllerExample   : commandApi with id 1
- 2024-04-16T21:58:23.540+02:00  INFO 26435 --- [nio-8080-exec-1] org.example.command.CommandExample       : CommandExample constructor with params - org.example.command.CommandExample@7da94f5
- 2024-04-16T21:58:23.540+02:00  INFO 26435 --- [nio-8080-exec-1] org.example.command.CommandExample       : **CommandExample constructor with id 1** 
- 2024-04-16T21:58:23.541+02:00  INFO 26435 --- [nio-8080-exec-1] org.example.command.CommandExample       : Id is not null. Do something
- 2024-04-16T21:58:23.541+02:00  INFO 26435 --- [nio-8080-exec-1] org.example.service.ServiceExample       : ServiceExample doSomething

Test Autowire&InitializeBean
> curl http://localhost:8080/instance

- 2024-04-16T22:11:17.288+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.c.BeanInstanceControllerExample      : instanceApi
- 2024-04-16T22:11:17.290+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - init
- 2024-04-16T22:11:17.290+02:00  INFO 27147 --- [nio-8080-exec-1] o.example.instance.BeanInstanceExample   : BeanInstanceExample - constructor
- 2024-04-16T22:11:17.290+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - new BeanInstanceExample
- 2024-04-16T22:11:17.291+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - autowireBean
- 2024-04-16T22:11:17.291+02:00  INFO 27147 --- [nio-8080-exec-1] o.example.instance.BeanInstanceExample   : **BeanInstanceExample - init**
- 2024-04-16T22:11:17.291+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - initializeBean
- 2024-04-16T22:11:17.291+02:00  INFO 27147 --- [nio-8080-exec-1] o.e.c.BeanInstanceControllerExample      : **instanceApi result true**

## TEST with native build
> mvn clean -Pnative native:compile

> ./target/spring-boot-test

Test Command pattern
> curl http://localhost:8080/test/1

- 2024-04-16T22:06:38.831+02:00  INFO 26755 --- [nio-8080-exec-1] o.example.controller.ControllerExample   : commandApi with id 1
- 2024-04-16T22:06:38.831+02:00  INFO 26755 --- [nio-8080-exec-1] org.example.command.CommandExample       : CommandExample constructor empty - org.example.command.CommandExample@2694fbc3
- 2024-04-16T22:06:38.831+02:00  INFO 26755 --- [nio-8080-exec-1] org.example.command.CommandExample       : **CommandExample constructor empty**
- 2024-04-16T22:06:38.831+02:00  INFO 26755 --- [nio-8080-exec-1] org.example.command.CommandExample       : Id is null
- 2024-04-16T22:06:38.831+02:00 ERROR 26755 --- [nio-8080-exec-1] o.example.controller.ControllerExample   : id is null

Test Autowire&InitializeBean
> curl http://localhost:8080/instance

- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.c.BeanInstanceControllerExample      : instanceApi
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - init
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.example.instance.BeanInstanceExample   : BeanInstanceExample - constructor
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - new BeanInstanceExample
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - autowireBean
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.service.ServiceBeanInstanceExample   : ServiceBeanInstanceExample - initializeBean
- 2024-04-16T22:14:55.770+02:00  INFO 27855 --- [nio-8080-exec-1] o.e.c.BeanInstanceControllerExample      : **instanceApi result false**
