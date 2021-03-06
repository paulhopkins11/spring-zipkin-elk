package uk.co.nstech.sleuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ZipkinController {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${downstream.server.url:}")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/zipkin")
  public String zipkin() {
    log.debug(">>> Call to zipkin");
    String response = applicationName;
    if (url != null && url.length() > 0) {
      String theUrl = url;
      if (!theUrl.endsWith("/")) {
        theUrl += "/";
      }
      theUrl += "zipkin";
      response += " -> " + restTemplate.exchange(theUrl, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();
    }
    return response;
  }
}
