package com.rsk.quotes.presentation.main;

import com.rsk.quotes.presentation.jndi.JndiView;
import com.rsk.quotes.presentation.messages.MessagesView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPresenter implements Initializable {
    @FXML
    AnchorPane jndiInput;

    @FXML
    AnchorPane messagesOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JndiView jndiView = new JndiView();

        jndiInput.getChildren().addAll(jndiView.getView());

        MessagesView messagesView = new MessagesView();

        messagesOutput.getChildren().addAll(messagesView.getView());
    }

}
