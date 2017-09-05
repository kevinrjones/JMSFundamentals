package com.rsk.quotes.messages;

/**
 * Created by kevinj on 13/05/2017.
 */
public class CarDetailsMessage {

    String price;
    String registration;
    int mileage;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
