package com.rsk.quotes;

import com.airhacks.afterburner.injection.Injector;
import com.rsk.quotes.jndi.QuotesJndi;
import com.rsk.quotes.presentation.main.MainView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class App extends Application {

    ExecutorService executorService;

    @Override
    public void start(Stage primaryStage) throws Exception {

        executorService = Executors.newScheduledThreadPool(10,
                runnable -> {
                    Thread t = Executors.defaultThreadFactory().newThread(runnable);
                    t.setDaemon(true);
                    return t;
                });

        Map<Object, Object> injectedValues = new HashMap<>();

        injectedValues.put("messageDetails", FXCollections.observableArrayList());
        injectedValues.put("quotesJndi", new QuotesJndi());
        injectedValues.put("executorService", executorService);
        injectedValues.put("numberOfProviders", 4); //todo: read from configuration

        Injector.setConfigurationSource(injectedValues::get);

        MainView appView = new MainView();

        Scene scene = new Scene(appView.getView());
        primaryStage.setTitle("Quote Manager");
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().addAll(uri);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
