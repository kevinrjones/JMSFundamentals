package com.rsk.quotes.presentation.main;

import com.rsk.quotes.models.CarDetails;
import com.rsk.quotes.presentation.cardetails.CardetailsView;
import com.rsk.quotes.presentation.jndi.JndiView;
import com.rsk.quotes.presentation.quoteList.QuotelistView;
import com.rsk.quotes.presentation.userdetails.UserdetailsView;
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
    AnchorPane carDetails;

    @FXML
    AnchorPane userDetails;
    
    @FXML
    AnchorPane quoteList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JndiView jndiView = new JndiView();
        CardetailsView cardetailsView = new CardetailsView();
        UserdetailsView userdetailsView = new UserdetailsView();
        QuotelistView quotelistView = new QuotelistView();

        jndiInput.getChildren().addAll(jndiView.getView());
        carDetails.getChildren().addAll(cardetailsView.getView());
        userDetails.getChildren().addAll(userdetailsView.getView());
        quoteList.getChildren().addAll(quotelistView.getView());
    }

}
