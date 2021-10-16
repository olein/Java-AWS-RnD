package com.rnd.aws.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.annotation.PostConstruct;
import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

  @Autowired AmazonSqsClient amazonSQSClient;
  private SQSConnectionFactory connectionFactory;

  @PostConstruct
  public void init() {
    ProviderConfiguration providerConfiguration = new ProviderConfiguration();
    connectionFactory =
        new SQSConnectionFactory(providerConfiguration, amazonSQSClient.getClient());
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(this.connectionFactory);
    factory.setDestinationResolver(new DynamicDestinationResolver());
    factory.setConcurrency("3-10");
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    return factory;
  }

  @Bean
  public JmsTemplate defaultJmsTemplate() {
    return new JmsTemplate(this.connectionFactory);
  }
}
