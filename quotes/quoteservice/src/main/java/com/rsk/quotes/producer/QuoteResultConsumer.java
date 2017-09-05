package com.rsk.quotes.producer;

import com.rsk.quotes.QuoteException;
import com.rsk.quotes.jndi.JndiServices;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;
import java.util.function.Function;

/**
 * Created by kevinj on 13/05/2017.
 */
public class QuoteResultConsumer {
    private final JndiServices jndiServices;

    public QuoteResultConsumer(JndiServices jndiServices, String factoryName)  {
        this.jndiServices = jndiServices;

        try {
            jndiServices.startConnections(factoryName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void listen(Function<String, Void> callback) {
        System.out.println("UserRegistrationMessage started listening");
        try {

            JMSContext context = jndiServices.getContext();
            Destination destination = jndiServices.getReplyToDestination();

            JMSConsumer consumer = context.createConsumer(destination);
            while (true) {
                String body = consumer.receiveBody(String.class);
                callback.apply(body);
                System.out.println(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}