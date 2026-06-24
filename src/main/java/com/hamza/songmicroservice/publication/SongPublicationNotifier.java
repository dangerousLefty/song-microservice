package com.hamza.songmicroservice.publication;

import com.amazonaws.services.sqs.AmazonSQS;
import jakarta.annotation.PostConstruct;
import tools.jackson.databind.ObjectMapper;
import com.hamza.songmicroservice.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SongPublicationNotifier {
    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    private final ObjectMapper objectMapper;

    public SongPublicationNotifier(
            AmazonSQS amazonSQS,
            ObjectMapper objectMapper,
            @Value("${sqs.published-songs-queue-url}") String queueUrl)
    {
        this.amazonSQS = amazonSQS;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    public void notifySongPublished(Song song){
        try{
            String messageBody = objectMapper.writeValueAsString(song);
            amazonSQS.sendMessage(queueUrl, messageBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish song event to SQS", e);
        }
    }

    @PostConstruct
    public void logQueueUrl() {
        System.out.println("Publishing songs to SQS queue URL: " + queueUrl);
    }

}
