package com.rsk.quotes.consumer;

import com.rsk.quotes.QuoteException;
import com.rsk.quotes.jndi.AcknowledgementType;
import com.rsk.quotes.jndi.JndiServices;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ObtainQuote {
    private final JndiServices jndiServices;
    private final String topicName;

    public ObtainQuote(JndiServices jndiServices, String factoryName, String topicName) throws QuoteException, NamingException {
        this.jndiServices = jndiServices;
        this.topicName = topicName;

        jndiServices.startConnections(factoryName);

    }

    public void listen(BiFunction<String, Destination, Void> callback) {
        System.out.println("ObtainQuote started listening");
        try  {
            JMSContext context = jndiServices.getConnectionFactory().createContext();
            
            Topic queue = (Topic) jndiServices.getInitialContext().lookup(topicName);

            JMSConsumer consumer = context.createConsumer(queue);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                Destination replyTo = message.getJMSReplyTo();

                String body = message.getText();
                callback.apply(body, replyTo);
                if(jndiServices.getAcknowledgementType() == AcknowledgementType.Client) {
                    message.acknowledge();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
