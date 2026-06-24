package com.hamza.songmicroservice.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConfig {

    @Value("${region}")
    private String region;

    @Bean
    public AmazonSQS amazonSQS(){
        return AmazonSQSClientBuilder.standard()
                .withRegion(region)
                .build();
    }
}
