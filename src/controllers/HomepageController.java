package controllers;

import codes.HomepageUpdater;
import codes.Main;
import data.Club;
import data.LocalDatabase;
import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import network.client.WriteThreadClient;
import network.dto.LogoutRequest;

import java.util.ArrayList;
import java.util.List;

public class HomepageController{


    List<Player> players = new ArrayList<>();
    private int searchOption = 0;
    private String searchString;
    private Main main;
    HomepageUpdater homepageUpdater;
    Club club;
    int list = 0; //1 -> club players, 2 -> market players

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button marketButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Button nameSearch;

    @FXML
    private Button positionSearch;

    @FXML
    private Button countrySearch;

    @FXML
    private Button salarySearch;

    @FXML
    private Button maxAge;

    @FXML
    private Button maxHeight;

    @FXML
    private Button maxSalary;

    @FXML
    private Button countryWiseButton;

    @FXML
    private Button totalSalaryButton;

    @FXML
    private Label clubNameLabel;

    @FXML
    private TextField textField;

    @FXML
    private Button searchIcon;

    @FXML
    private Label messageLabel;

    @FXML
    private Button cancelSearch;

    @FXML
    private Label notFoundLabel;

    @FXML
    private TextField fromSalary;

    @FXML
    private TextField toSalary;

    @FXML
    private Label middleLable;

    @FXML
    private Label dollarLabel;

    @FXML
    private Label fromLabel;

    void showFromTo(boolean show){
        if(show)showTextBox(false);
        fromLabel.setVisible(show);
        middleLable.setVisible(show);
        dollarLabel.setVisible(show);
        toSalary.setVisible(show);
        fromSalary.setVisible(show);
        toSalary.clear();
        fromSalary.clear();
        searchIcon.setVisible(show);
        cancelSearch.setVisible(show);
        if(show) fromSalary.requestFocus();
        if(show) cancelSearch.setLayoutY(96);
        else cancelSearch.setLayoutY(55);
    }

    void showTextBox(boolean show){
        if(show) showFromTo(false);
        textField.setVisible(show);
        textField.clear();
        searchIcon.setVisible(show);
        cancelSearch.setVisible(show);
        if(show) textField.requestFocus();
        if(show) cancelSearch.setLayoutY(96);
        else cancelSearch.setLayoutY(55);
    }

    void search(){
        homepageUpdater.updateGUI(searchOption,searchString);
    }

    @FXML
    void searchPressed(ActionEvent event) {
        if (searchOption==4) searchString = fromSalary.getText()+","+toSalary.getText();
        else searchString = textField.getText();
        search();
    }

    @FXML
    void nameSearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type player name");
        searchOption = 1;
    }

    @FXML
    void positionSearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type player postion");
        searchOption = 2;
    }

    @FXML
    void countrySearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type the country name");
        searchOption = 3;
    }

    @FXML
    void salarySearchPressed(ActionEvent event) {
        messageLabel.setVisible(false);
        showFromTo(true);
        searchOption = 4;
    }

    @FXML
    void maxAgePressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 5;
        search();
    }

    @FXML
    void maxHeightPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 6;
        search();
    }

    @FXML
    void maxSalaryPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 7;
        search();
    }

    @FXML
    void countryWisePressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 8;
        search();
    }

    @FXML
    void totalSalaryPressed(ActionEvent event) {
        if(list!=1) return;
        searchOption = 9;
        search();
    }

    @FXML
    void cancelSearchPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(false);
        messageLabel.setVisible(false);
        searchOption = 0;
        search();
    }

    @FXML
    void showHome(ActionEvent event) {
        if(list==1) return;
        list = 1;
        totalSalaryButton.setVisible(true);
        clubNameLabel.setText(club.getName());
        homepageUpdater.updateGUI(list);
    }

    @FXML
    void showMarket(ActionEvent event) {
        if(list==2) return;
        list = 2;
        totalSalaryButton.setVisible(false);
        clubNameLabel.setText("Marketplace");
        homepageUpdater.updateGUI(list);
    }

    @FXML
    void logOut(ActionEvent event) {
        WriteThreadClient.write(new LogoutRequest());
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init(Main main){
        this.main = main;
        club = LocalDatabase.getInstance().getClub();
        homepageUpdater = new HomepageUpdater(this);
        LocalDatabase.getInstance().setHomepageController(this);
        showHome(new ActionEvent());
    }


    public Label getNotFoundLabel() { return notFoundLabel; }

    public GridPane getGrid() { return grid; }

    synchronized public void setMessageLabel(String message){
        if(message==null) {
            messageLabel.setVisible(false);
            return;
        }
        messageLabel.setText(message);
        messageLabel.setVisible(true);
    }

    public HomepageUpdater getHomepageUpdater() {
        return homepageUpdater;
    }
}
