package com.databases.synchronizer.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

/**
 * Created by Ghazi Naceur on 04/05/2018.
 */
@Configuration
public class JmsConfiguration {

    @Bean
    public JmsTransactionManager createJmsTransactionManager(final ConnectionFactory connectionFactory){
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

    @Bean
    public JmsComponent createJmsComponent(final ConnectionFactory connectionFactory,
                                           final JmsTransactionManager jmsTransactionManager,
                                           @Value("${max.concurrent.consumers}") final int maxConcurrentConsumers){
        JmsComponent jmsComponent = JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
        jmsComponent.setMaxConcurrentConsumers(maxConcurrentConsumers);
        return jmsComponent;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        factory.setTrustedPackages(Arrays.asList("java.lang","com.databases.synchronizer.entity"));
        return factory;
    }
}
