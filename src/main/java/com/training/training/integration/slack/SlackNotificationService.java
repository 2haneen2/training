package com.training.training.integration.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class SlackNotificationService {

    private final String webhookUrl;

    private final RestClient restClient;

    public SlackNotificationService(
            @Value("${slack.webhook.url}") String webhookUrl) {

        this.webhookUrl = webhookUrl;
        this.restClient = RestClient.create();
    }

    public void sendMessage(String message) {

        restClient.post()
                .uri(webhookUrl)
                .body(Map.of("text", message))
                .retrieve()
                .toBodilessEntity();
    }
}