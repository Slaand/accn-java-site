package com.slaand.site.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class EmailNotificationService {

    private List<EmailInformationReceived> subscribers;

    public EmailNotificationService() {
        subscribers = new ArrayList<>();
    }

    /**
     * Services can "sign up" here to receive updates
     * @param subscriber
     */
    public void subscribe(EmailInformationReceived subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Service can "opt-out" from receiving these updates
     * @param subscriber
     */
    public void unsubscribe(EmailInformationReceived subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * This is called when desired event happens, all subscribers will be informed
     */
    public void publish(EmailInformation data) {
        for (EmailInformationReceived subscriber : subscribers) {
            subscriber.receivedInformation(data);
        }
    }
    
}
