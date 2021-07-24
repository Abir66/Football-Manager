package controllers;

import codes.Main;
import data.Club;
import data.LocalDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Settings {

    private Main main;
    private Club club;

    @FXML
    private Button homeButton;

    @FXML
    private Button marketButton;

    @FXML
    private Button settings;

    @FXML
    private Button marketButton1;

    @FXML
    private Label clubNameLabel;

    @FXML
    void changeClubName(ActionEvent event) {

    }

    @FXML
    void changePassword(ActionEvent event) {

    }

    @FXML
    void deleteAccount(ActionEvent event) {

    }

    @FXML
    void gotoSettings(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void showHome(ActionEvent event) {
        try {
            main.showHomePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showMarket(ActionEvent event) {

    }

    public void init(Main main){
        this.main = main;
        club = LocalDatabase.getInstance().getClub();


    }

}