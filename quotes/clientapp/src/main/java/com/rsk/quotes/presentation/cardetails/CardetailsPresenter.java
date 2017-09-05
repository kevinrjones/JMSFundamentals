package com.rsk.quotes.presentation.cardetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsk.quotes.jndi.AcknowledgementType;
import com.rsk.quotes.jndi.JndiServices;
import com.rsk.quotes.messages.CarDetailsMessage;
import com.rsk.quotes.messages.QuoteDetails;
import com.rsk.quotes.messages.UserRegistrationMessage;
import com.rsk.quotes.models.QuoteResult;
import com.rsk.quotes.models.UserDetails;
import com.rsk.quotes.producer.GetPriceQuotes;
import com.rsk.quotes.models.CarDetails;
import com.rsk.quotes.presentation.IntegerFilter;
import com.rsk.quotes.producer.QuoteResultConsumer;
import com.rsk.quotes.producer.RegisterUser;
import com.rsk.quotes.tasks.QuoteReplyTask;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * Created by kevinj on 02/05/2017.
 */
public class CardetailsPresenter implements Initializable {
    @FXML
    Label lblRegistration;

    @FXML
    Label lblValue;

    @FXML
    Label lblMileage;

    @FXML
    TextField registration;

    @FXML
    TextField value;

    @FXML
    TextField mileage;

    @Inject
    CarDetails carDetails;

    @Inject
    UserDetails userDetails;

    @FXML
    Button getQuotes;

    @FXML
    Button getQuotesAndRegister;

    @Inject
    JndiServices quotesJndi;

    @Inject
    ExecutorService executorService;

    @Inject
    private ObservableList<QuoteResult> quotes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TextFormatter<Integer> formatter = new TextFormatter<>(
                new IntegerStringConverter(), // Standard converter form JavaFX
                0,
                new IntegerFilter());

        lblRegistration.setText(resources.getString("registration"));
        lblValue.setText(resources.getString("value"));
        lblMileage.setText(resources.getString("mileage"));

        mileage.setTextFormatter(formatter);


        QuoteResultConsumer quoteResultConsumer = new QuoteResultConsumer(quotesJndi, "jms/__defaultConnectionFactory");
        QuoteReplyTask registerUserTask = new QuoteReplyTask(quoteResultConsumer, this::quoteResult);
        executorService.submit(registerUserTask);

    }

    public void quoteResult(String message) {

        System.out.println("addRegistration: " + message);

        ObjectMapper mapper = new ObjectMapper();
        try {
            QuoteDetails quoteDetails = mapper.readValue(message, QuoteDetails.class);
            QuoteResult quoteResult = new QuoteResult(quoteDetails.getCompanyName(), quoteDetails.getPrice());
            quotes.add(quoteResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getQuotes(ActionEvent actionEvent) {
        CarDetailsMessage carDetailsMessage = new CarDetailsMessage();

        carDetailsMessage.setMileage(Integer.valueOf(mileage.getText()));
        carDetailsMessage.setPrice(value.getText());
        carDetailsMessage.setRegistration(registration.getText());

        try {
            GetPriceQuotes priceQuotes = new GetPriceQuotes(quotesJndi, "jms/__defaultConnectionFactory", this.carDetails.getTopicName());
            priceQuotes.sendMessage(quotesJndi.getContext(), quotesJndi.getReplyToDestination(), carDetailsMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void getQuotesAndRegister(ActionEvent actionEvent) {

        if(carDetails.isTransacted()) {
            try {
                if (carDetails.isTransacted()) {
                    quotesJndi.setAcknowledgementType(AcknowledgementType.Transacted);
                }

                UserRegistrationMessage userRegistrationMessage = new UserRegistrationMessage();
                userRegistrationMessage.name = userDetails.getUserName();
                userRegistrationMessage.password = userDetails.getPassword();


                RegisterUser registerUser = new RegisterUser(quotesJndi, "jms/__defaultConnectionFactory", carDetails.getQueueName());
                registerUser.sendMessage(quotesJndi.getContext(), userRegistrationMessage);

                CarDetailsMessage carDetailsMessage = new CarDetailsMessage();
                carDetailsMessage.setMileage(Integer.valueOf(mileage.getText()));
                carDetailsMessage.setPrice(value.getText());
                carDetailsMessage.setRegistration(registration.getText());
                GetPriceQuotes priceQuotes = new GetPriceQuotes(quotesJndi, "jms/__defaultConnectionFactory", this.carDetails.getTopicName());
                priceQuotes.sendMessage(quotesJndi.getContext(), quotesJndi.getReplyToDestination(), carDetailsMessage);

                Random r = new Random();
                if(r.nextBoolean()) {
                    System.out.println("committed");
                    quotesJndi.getContext().commit();
                } else {
                    System.out.println("rollback");
                    quotesJndi.getContext().rollback();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
