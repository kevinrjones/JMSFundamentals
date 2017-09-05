package com.rsk.quotes.presentation.quoteList;

import com.rsk.quotes.models.QuoteResult;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class QuotelistPresenter implements Initializable {

    @Inject
    private ObservableList<QuoteResult> quotes;

    @FXML
    TableView<QuoteResult> quotesTable;

    @FXML
    TableColumn<QuoteResult, String> quotesCompanyColumn;

    @FXML
    TableColumn<QuoteResult, Integer> quotesPriceColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        quotesCompanyColumn.setCellValueFactory(cellData -> cellData.getValue().companyNameProperty());
        quotesPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        quotesTable.setItems(quotes);

    }
}
