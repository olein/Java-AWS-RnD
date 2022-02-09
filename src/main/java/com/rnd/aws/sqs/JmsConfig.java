package com.rnd.aws.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.annotation.PostConstruct;
import javax.jms.Session;

import static org.springframework.jms.listener.DefaultMessageListenerContainer.CACHE_AUTO;

@Configuration
@EnableJms
public class JmsConfig {

  @Autowired AmazonSqsClient amazonSQSClient;
  private SQSConnectionFactory connectionFactory;

//  @PostConstruct
//  public void init() {
//    ProviderConfiguration providerConfiguration = new ProviderConfiguration();
//    connectionFactory =
//        new SQSConnectionFactory(providerConfiguration, amazonSQSClient.getClient());
//  }

//  @Bean
//  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
//    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//    factory.setConnectionFactory(this.connectionFactory);
//    factory.setDestinationResolver(new DynamicDestinationResolver());
//    factory.setConcurrency("3-10");
//    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
//    return factory;
//  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    this.connectionFactory =
            SQSConnectionFactory.builder()
                    .withRegion(Region.getRegion(Regions.US_EAST_1))
                    .withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain())
                    .withNumberOfMessagesToPrefetch(0)
                    .build();

    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setTargetConnectionFactory(this.connectionFactory);
    cachingConnectionFactory.setReconnectOnException(true);

    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(this.connectionFactory);
    factory.setDestinationResolver(new DynamicDestinationResolver());
    factory.setConcurrency("3-10");
    factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    factory.setCacheLevel(CACHE_AUTO);
    return factory;
  }

  @Bean
  public JmsTemplate defaultJmsTemplate() {
    return new JmsTemplate(this.connectionFactory);
  }
}