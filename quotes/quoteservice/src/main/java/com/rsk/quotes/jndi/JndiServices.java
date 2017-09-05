package com.rsk.quotes.jndi;

import com.rsk.quotes.QuoteException;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public interface JndiServices {
    ConnectionFactory getConnectionFactory();

    InitialContext getInitialContext();

    boolean startConnections(String connectionFactoryName) throws NamingException, QuoteException;

    JMSContext getContext();

    Destination getReplyToDestination();

    AcknowledgementType getAcknowledgementType();
    void setAcknowledgementType(AcknowledgementType value);
}
