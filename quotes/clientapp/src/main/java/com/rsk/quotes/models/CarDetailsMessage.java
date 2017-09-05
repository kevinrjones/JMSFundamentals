package com.rsk.quotes.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by kevinj on 03/05/2017.
 */
public class CarDetailsMessage {

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

    private StringProperty value = new SimpleStringProperty();
    public StringProperty valueProperty() {
        return value;
    }

    private String registration;
    private String mileage;

    public String getValue() {
        return value.getValue();
    }

    public void setValue(String value) {
        this.value.setValue(value);
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

}
