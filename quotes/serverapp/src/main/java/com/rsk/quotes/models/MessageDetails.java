package com.rsk.quotes.models;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by user on 5/10/2017.
 */
public class MessageDetails {

    private final StringProperty messageType;
    private final StringProperty messageBody;
    private final StringProperty dateTime;

    public MessageDetails(String messageType, String messageBody, String dateOfMessage) {
        this.messageType = new SimpleStringProperty(messageType);
        this.messageBody = new SimpleStringProperty(messageBody);
        this.dateTime = new SimpleStringProperty(dateOfMessage);
    }

    public String getMessageType() {
        return messageType.get();
    }

    public StringProperty messageTypeProperty() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType.set(messageType);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public StringProperty dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
    }

    public String getMessageBody() {
        return messageBody.get();
    }

    public StringProperty messageBodyProperty() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody.set(messageBody);
    }
}
