package com.rsk.quotes.producer;

import com.rsk.quotes.jndi.JndiServices;

/**
 * Created by kevinj on 13/05/2017.
 */
public class MessageBase {
    protected JndiServices jndiServices;
    protected String factoryName;
    protected String destinationName;

    public MessageBase(JndiServices jndiServices, String factoryName, String destinationName) {
        this.jndiServices = jndiServices;
        this.factoryName = factoryName;
        this.destinationName = destinationName;
    }
}
