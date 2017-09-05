package com.rsk.quotes.consumer;

import com.rsk.quotes.QuoteException;
import com.rsk.quotes.jndi.AcknowledgementType;
import com.rsk.quotes.jndi.JndiServices;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.function.Function;

public class UserRegistration {
    private final JndiServices jndiServices;
    private final String queueName;

    public UserRegistration(JndiServices jndiServices, String factoryName,  String queueName)  throws QuoteException, NamingException {
        this.jndiServices = jndiServices;
        this.queueName = queueName;

        jndiServices.startConnections(factoryName);

    }

    public void listen(Function<String, Void> callback) {
        System.out.println("UserRegistrationMessage started listening");
        try {
            JMSContext context = jndiServices.getContext();
            Queue queue = (Queue) jndiServices.getInitialContext().lookup(queueName);

            JMSConsumer consumer = context.createConsumer(queue);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                String body = message.getText();
                callback.apply(body);
                if(jndiServices.getAcknowledgementType() == AcknowledgementType.Client) {
                    message.acknowledge();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
