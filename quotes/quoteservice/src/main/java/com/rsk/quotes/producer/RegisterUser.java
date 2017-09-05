package com.rsk.quotes.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.rsk.quotes.jndi.JndiServices;
import com.rsk.quotes.messages.UserRegistrationMessage;

import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

public class RegisterUser extends MessageBase {

    public RegisterUser(JndiServices jndiServices, String factoryName, String queueName) {
        super(jndiServices, factoryName, queueName);

        try {
            jndiServices.startConnections(factoryName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(JMSContext context, UserRegistrationMessage userRegistration) {
        try {

            Queue queue = (Queue) jndiServices.getInitialContext().lookup(destinationName);

            ObjectMapper mapper = new ObjectMapper();
            String userRegJson = mapper.writeValueAsString(userRegistration);
            JMSProducer producer = context.createProducer();
            if (!userRegistration.deliveryMode) {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
            if (userRegistration.timeToLive != 0) {
                producer.setTimeToLive(userRegistration.timeToLive);
            }
            producer.send(queue, userRegJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
