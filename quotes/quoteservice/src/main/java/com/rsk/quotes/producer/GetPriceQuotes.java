package com.rsk.quotes.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsk.quotes.jndi.JndiServices;
import com.rsk.quotes.messages.CarDetailsMessage;

import javax.jms.*;

public class GetPriceQuotes extends MessageBase {

    public GetPriceQuotes(JndiServices quotesJndi, String factoryName, String destinationName) {

        super(quotesJndi, factoryName, destinationName);

        try {
            jndiServices.startConnections(factoryName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(JMSContext context, Destination tempDest, CarDetailsMessage carDetails) {
        try {
            jndiServices.startConnections(factoryName);
            Topic topic = (Topic) jndiServices.getInitialContext().lookup(destinationName);

            ObjectMapper mapper = new ObjectMapper();
            String userRegJson = mapper.writeValueAsString(carDetails);
            JMSProducer producer = context.createProducer();

            if (tempDest != null) producer.setJMSReplyTo(tempDest);

            producer.send(topic, userRegJson);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
