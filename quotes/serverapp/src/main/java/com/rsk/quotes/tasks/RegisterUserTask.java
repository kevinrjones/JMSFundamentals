package com.rsk.quotes.tasks;

import com.rsk.quotes.consumer.UserRegistration;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.function.Consumer;

/**
 * Created by user on 5/8/2017.
 */
public class RegisterUserTask extends Task<Void> {

    private UserRegistration userRegistration;
    private Consumer<String> runLater;

    public RegisterUserTask(UserRegistration userRegistration, Consumer<String> runLater) {
        super();
        this.userRegistration = userRegistration;
        this.runLater = runLater;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("RegisterUserTask started running");

        userRegistration.listen(message -> {
            Platform.runLater(() -> {
                runLater.accept(message);
            });
            System.out.println("RegisterUserTask finished running");
            return null;
        });
        System.out.println("RegisterUserTask finished running");
        return null;
    }
}

