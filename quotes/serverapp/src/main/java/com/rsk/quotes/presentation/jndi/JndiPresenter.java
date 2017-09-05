package com.rsk.quotes.presentation.jndi;

import com.rsk.quotes.QuoteException;
import com.rsk.quotes.consumer.ObtainQuote;
import com.rsk.quotes.jndi.AcknowledgementType;
import com.rsk.quotes.jndi.JndiServices;
import com.rsk.quotes.models.JMSDetails;
import com.rsk.quotes.models.MessageDetails;
import com.rsk.quotes.consumer.UserRegistration;
import com.rsk.quotes.tasks.GetQuoteTask;
import com.rsk.quotes.tasks.RegisterUserTask;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.inject.Inject;
import javax.naming.NamingException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class JndiPresenter implements Initializable {

    String[] companies = new String[]{"CarQuote", "Giraffe", "RCA", "Viava"}      ;

    @Inject
    ExecutorService executorService;

    @FXML
    Label lblTitle;

    @FXML
    Label lblQueueName;

    @FXML
    RadioButton radioAuto;

    @FXML
    RadioButton radioClient;

    @FXML
    RadioButton radioDuplicate;

    @FXML
    TextField queueName;

    @FXML
    Label lblTopicName;

    @FXML
    TextField topicName;

    @FXML
    Button btnListen;

    @Inject
    private int numberOfProviders;

    @Inject
    JMSDetails jmsDetails;

    @Inject
    String JMSQueueName;

    @Inject
    String JMSTopicName;

    @Inject
    JndiServices quotesJndi;

    @Inject
    private ObservableList<MessageDetails> messageDetails;

    UserRegistration userRegistration;

    ObtainQuote obtainQuote;

    @FXML
    private void startListeners(ActionEvent evt) throws QuoteException, NamingException {
        if(radioClient.isSelected()) {
            quotesJndi.setAcknowledgementType(AcknowledgementType.Client);
        }
        if(radioDuplicate.isSelected()) {
            quotesJndi.setAcknowledgementType(AcknowledgementType.DupsOk);
        }

        userRegistration = new UserRegistration(quotesJndi, "jms/__defaultConnectionFactory", queueName.getText());
        RegisterUserTask registerUserTask = new RegisterUserTask(userRegistration, this::addRegistration);
        executorService.submit(registerUserTask);



        obtainQuote = new ObtainQuote(quotesJndi, "jms/__defaultConnectionFactory", topicName.getText());

        for (int ndx = 0; ndx < numberOfProviders; ndx++) {
            GetQuoteTask getQuoteTask = new GetQuoteTask(quotesJndi, obtainQuote, companies[ndx] , ndx, this::addQuote);
            executorService.submit(getQuoteTask);
        }

        btnListen.setDisable(true);
    }

    private void addQuote(String quoteMessage) {
        System.out.println("addQuote: " + quoteMessage);
        MessageDetails details = new MessageDetails("AddQuote", new Date().toString(), quoteMessage);
        messageDetails.add(details);
    }

    public void addRegistration(String message) {
        System.out.println("addRegistration: " + message);
        MessageDetails details = new MessageDetails("RegisterUser", new Date().toString(), message);
        messageDetails.add(details);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblQueueName.setText(resources.getString("queueLabel"));
        lblTopicName.setText(resources.getString("topicLabel"));

        ToggleGroup ackGroup = new ToggleGroup();
        radioAuto.setText(resources.getString("autoLabel"));
        radioClient.setText(resources.getString("clientLabel"));
        radioDuplicate.setText(resources.getString("duplicateLabel"));
        radioAuto.setToggleGroup(ackGroup);
        radioClient.setToggleGroup(ackGroup);
        radioDuplicate.setToggleGroup(ackGroup);
        radioAuto.setSelected(true);

        btnListen.setText(resources.getString("startLabel"));

        queueName.textProperty().bindBidirectional(jmsDetails.queueNameProperty());
        queueName.setText(JMSQueueName);
        topicName.textProperty().bindBidirectional(jmsDetails.topicNameProperty());
        topicName.setText(JMSTopicName);
        lblTitle.setText(resources.getString("title"));

        System.out.println("Number of providers = " + numberOfProviders);

    }

}
