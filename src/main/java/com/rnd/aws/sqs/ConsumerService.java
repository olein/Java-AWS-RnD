package com.rnd.aws.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

  private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

  @JmsListener(destination = "${queue.order}")
  public void process(String data) {
    LOG.info("Received message " + data);
  }
}
