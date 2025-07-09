package com.example.phonesuggester;

import com.example.phonesuggester.controller.PhoneController;
import com.example.phonesuggester.service.InMemoryPhoneService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication(exclude = {
    MongoAutoConfiguration.class,
    MongoDataAutoConfiguration.class
})
@ComponentScan(basePackages = "com.example.phonesuggester", 
               excludeFilters = @ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.REGEX, 
                                                    pattern = "com\\.example\\.phonesuggester\\.repository\\..*"))
public class PhoneSuggesterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhoneSuggesterApplication.class, args);
    }
} 