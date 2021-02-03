package ir.practice.microservices.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ir.practice.microservices.bean.Configuration;
import ir.practice.microservices.utility.LimitConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  LimitsConfigurationController {

    private Configuration configuration;

    public LimitsConfigurationController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfiguration(){
        return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
    }

    @GetMapping("limits/fault-tolerance-sample")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public LimitConfiguration retrieveConfiguration(){
        throw new RuntimeException("Not avaiable");
    }

    public LimitConfiguration fallbackRetrieveConfiguration() {
        return new LimitConfiguration(99, 9);
    }
}
