package com.rsk.quotes.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JMSDetails {

    private StringProperty queueName = new SimpleStringProperty("QueueName");

    public StringProperty queueNameProperty() {
        return queueName;
    }

    public String getQueueName() {
        return queueName.getValue();
    }

    public void setQueueName(String queueName) {
        this.queueName.setValue(queueName);
    }

    private StringProperty topicName = new SimpleStringProperty("QueueName");

    public StringProperty topicNameProperty() {
        return topicName;
    }

    public String getTopicName() {
        return topicName.getValue();
    }

    public void setTopicName(String queueName) {
        this.topicName.setValue(queueName);
    }

    public int getNumberOfProviders() {
        return numberOfProviders;
    }

    public void setNumberOfProviders(int numberOfProviders) {
        this.numberOfProviders = numberOfProviders;
    }

    private int numberOfProviders;
}
