package com.rsk.quotes.consumer;

import com.rsk.quotes.QuoteException;
import com.rsk.quotes.jndi.AcknowledgementType;
import com.rsk.quotes.jndi.JndiServices;
import javafx.util.Pair;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.function.BiFunction;

public class ObtainQuoteAndRegistration {
    private final JndiServices jndiServices;
    private final String topicName;
    private String queueName;

    public ObtainQuoteAndRegistration(JndiServices jndiServices, String factoryName, String topicName, String queueName) throws QuoteException, NamingException {
        this.jndiServices = jndiServices;
        this.topicName = topicName;
        this.queueName = queueName;

        jndiServices.startConnections(factoryName);

    }

    public void listen(BiFunction<Pair<String, String>, Destination, Void> callback) {
        System.out.println("ObtainQuote started listening");
        try  {
            JMSContext context = jndiServices.getConnectionFactory().createContext();
            
            Topic topic = (Topic) jndiServices.getInitialContext().lookup(topicName);
            Queue queue = (Queue) jndiServices.getInitialContext().lookup(queueName);


            // todo: transacted code here
            JMSConsumer topicConsumer = context.createConsumer(topic);
            JMSConsumer queueConsumer = context.createConsumer(queue);
            while (true) {
                TextMessage message = (TextMessage) queueConsumer.receive();
                String registrationBody = message.getText();

                message = (TextMessage) topicConsumer.receive();
                Destination replyTo = message.getJMSReplyTo();

                String quoteBody = message.getText();
                callback.apply(new Pair<>(quoteBody, registrationBody), replyTo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
