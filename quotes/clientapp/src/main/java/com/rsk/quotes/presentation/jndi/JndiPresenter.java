package com.rsk.quotes.presentation.jndi;

import com.rsk.quotes.models.CarDetails;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class JndiPresenter implements Initializable {
    @FXML
    Label lblTitle;

    @FXML
    Label lblQueueName;

    @FXML
    TextField queueName;

    @FXML
    Label lblTopicName;

    @FXML
    TextField topicName;

    @FXML
    CheckBox transacted;

    @Inject
    CarDetails carDetails;

    @Inject
    String JMSQueueName;

    @Inject
    String JMSTopicName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblQueueName.setText(resources.getString("queueLabel"));
        lblTopicName.setText(resources.getString("topicLabel"));
        queueName.textProperty().bindBidirectional(carDetails.queueNameProperty());
        queueName.setText(JMSQueueName);
        topicName.textProperty().bindBidirectional(carDetails.topicNameProperty());
        topicName.setText(JMSTopicName);
        transacted.selectedProperty().bindBidirectional(carDetails.transactedProperty());
        lblTitle.setText(resources.getString("title"));
    }
}
