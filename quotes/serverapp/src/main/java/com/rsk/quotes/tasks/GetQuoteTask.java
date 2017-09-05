package com.rsk.quotes.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsk.quotes.consumer.ObtainQuote;
import com.rsk.quotes.jndi.JndiServices;
import com.rsk.quotes.messages.QuoteDetails;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.jms.JMSProducer;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by user on 5/8/2017.
 */
public class GetQuoteTask extends Task {
    private ObtainQuote obtainQuote;
    private int quoterId;
    private Consumer<String> runLater;
    private JndiServices jndiServices;
    private String companyName;
    private Random random;

    public GetQuoteTask(JndiServices quotesJndi, ObtainQuote obtainQuote, String companyName, int quoterId, Consumer<String> runLater) {
        super();
        this.jndiServices = quotesJndi;
        this.companyName = companyName;
        System.out.println("ObtainQuote " + quoterId + " created");

        this.obtainQuote = obtainQuote;
        this.quoterId = quoterId;
        this.runLater = runLater;

        random = new Random();
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("ObtainQuote " + quoterId + " started running");

        obtainQuote.listen((message, destination) -> {
            Platform.runLater(() -> {
                runLater.accept(message);
                JMSProducer producer = jndiServices.getContext().createProducer();

                ObjectMapper mapper = new ObjectMapper();

                QuoteDetails quoteDetails = new QuoteDetails(companyName, random.nextInt(400));
                try {
                    String quoteDetailsJson = mapper.writeValueAsString(quoteDetails);
                    producer.send(destination, quoteDetailsJson);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("ObtainQuote " + quoterId + " finished running");
            return null;
        });
        System.out.println("ObtainQuoteTask finished running");
        return null;
    }
}