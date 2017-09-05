package com.rsk.quotes.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuoteResult {

    public QuoteResult() {
    }

    public QuoteResult(String companyName, Integer price) {
        this.companyName = new SimpleStringProperty(companyName);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    private StringProperty companyName;
    private IntegerProperty price;
}
