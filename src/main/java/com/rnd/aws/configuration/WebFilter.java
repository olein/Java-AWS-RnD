package com.rnd.aws.configuration;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/*
Fahim created at 7/3/2022
*/
@Component
@Order(1)
public class WebFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    try {
      MDC.put("userId", UUID.randomUUID().toString());
      chain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
