package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CountryWise {

    @FXML
    private Label countryName;

    @FXML
    private Label playerCount;

    public void init(String country, int count) {
        countryName.setText(country);
        playerCount.setText(count + " " + "Players");
    }

}
