package com.hamza.songmicroservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {

    @Value("${stage}")
    private String stage;

    @Value("${region}")
    private String region;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        if ("dev".equals(stage)) {
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(
                            new EndpointConfiguration("http://localhost:8000", region)
                    )
                    .withCredentials(
                            new AWSStaticCredentialsProvider(
                                    new BasicAWSCredentials("dummy", "dummy")
                            )
                    )
                    .build();
        }

        if ("prod".equals(stage)) {
            return AmazonDynamoDBClientBuilder.standard()
                    .withRegion(region)
                    .build();
        }

        throw new IllegalStateException("Unknown stage: " + stage);
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }
}