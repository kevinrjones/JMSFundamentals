package com.rsk.quotes.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by kevinj on 03/05/2017.
 */
public class CarDetails {

    private BooleanProperty transacted = new SimpleBooleanProperty(false);
    private StringProperty queueName = new SimpleStringProperty("QueueName");
    private StringProperty topicName = new SimpleStringProperty("QueueName");
    private StringProperty value = new SimpleStringProperty();
    private String registration;
    private String mileage;

    public StringProperty queueNameProperty() {
        return queueName;
    }

    public String getQueueName() {
        return queueName.getValue();
    }

    public void setQueueName(String queueName) {
        this.queueName.setValue(queueName);
    }


    public StringProperty topicNameProperty() {
        return topicName;
    }

    public String getTopicName() {
        return topicName.getValue();
    }

    public void setTopicName(String queueName) {
        this.topicName.setValue(queueName);
    }
    public StringProperty valueProperty() {
        return value;
    }


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

    public boolean isTransacted() {
        return transacted.get();
    }

    public BooleanProperty transactedProperty() {
        return transacted;
    }

    public void setTransacted(boolean transacted) {
        this.transacted.set(transacted);
    }
}

