package com.rsk.quotes.tasks;

import com.rsk.quotes.producer.QuoteResultConsumer;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.function.Consumer;

public class QuoteReplyTask extends Task<Void> {

    private QuoteResultConsumer quoteResultConsumer;
    private Consumer<String> runLater;

    public QuoteReplyTask(QuoteResultConsumer quoteResultConsumer, Consumer<String> runLater) {
        this.quoteResultConsumer = quoteResultConsumer;
        this.runLater = runLater;
    }

    @Override
    protected Void call() throws Exception {

        quoteResultConsumer.listen(message -> {
            Platform.runLater(() -> {
                runLater.accept(message);
            });
            System.out.println("QuoteReplyTask finished running");
            return null;
        });
        System.out.println("QuoteReplyTask finished running");
        return null;

    }
}
