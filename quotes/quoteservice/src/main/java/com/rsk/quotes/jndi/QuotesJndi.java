package com.rsk.quotes.jndi;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QuotesJndi implements JndiServices {

    ConnectionFactory connectionFactory = null;
    InitialContext initialContext;
    ThreadLocal<JMSContext> jmsContext = new ThreadLocal<>();
    private Queue queue;

    private AcknowledgementType acknowledgementType;

    public QuotesJndi() {
        acknowledgementType = AcknowledgementType.Auto;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public InitialContext getInitialContext() {
        return initialContext;
    }

    @Override
    public boolean startConnections(String connectionFactoryName) throws NamingException {
        if (initialContext == null) {
            initialContext = new InitialContext();
            connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);
            return true;
        } else {
            return false;
        }
    }

    public JMSContext getContext() {
        if(connectionFactory == null) {
            throw new RuntimeException("Connection has not been started");
        }
        if (jmsContext.get() == null) {
            if(acknowledgementType == AcknowledgementType.DupsOk) {
                jmsContext.set(connectionFactory.createContext(JMSContext.DUPS_OK_ACKNOWLEDGE));
            } else if (acknowledgementType == AcknowledgementType.Client) {
                jmsContext.set(connectionFactory.createContext(JMSContext.CLIENT_ACKNOWLEDGE));
            } else if (acknowledgementType == AcknowledgementType.Transacted) {
                jmsContext.set(connectionFactory.createContext(JMSContext.SESSION_TRANSACTED));
            } else  {
                jmsContext.set(connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE));
            }
        }
        return jmsContext.get();
    }

    @Override
    public Destination getReplyToDestination() {

        if(jmsContext.get() == null) throw new RuntimeException("Must have a JMS jmsContext");

        if(queue == null) {
            queue = jmsContext.get().createTemporaryQueue();
        }
        return queue;
    }

    @Override
    public AcknowledgementType getAcknowledgementType() {
        return acknowledgementType;
    }

    @Override
    public void setAcknowledgementType(AcknowledgementType acknowledgementType) {
        this.acknowledgementType = acknowledgementType;
    }


}
