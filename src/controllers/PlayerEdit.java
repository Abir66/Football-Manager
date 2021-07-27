package controllers;

import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.client.WriteThreadClient;
import network.dto.PlayerEditInfo;

import static javafx.collections.FXCollections.observableArrayList;

public class PlayerEdit {

    private Player player;
    private int playerNumber;
    private double playerSalary;
    private double playerHeight;
    private int playerAge;
    @FXML
    private TextField name;

    @FXML
    private TextField country;

    @FXML
    private TextField age;

    @FXML
    private TextField height;

    @FXML
    private TextField number;

    @FXML
    private TextField salary;

    @FXML
    private ComboBox<String> position;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void save(ActionEvent event) {
        if(validate() && checkDuplicate()){
            PlayerEditInfo p = new PlayerEditInfo();
            p.setName(name.getText());
            p.setCountry(country.getText());
            p.setId(player.getId());
            p.setAge(playerAge);
            p.setSalary(playerSalary);
            p.setHeight(playerHeight);
            p.setNumber(playerNumber);
            if (position.getSelectionModel().isEmpty()) p.setPosition(player.getPosition());
            else p.setPosition(position.getValue());
            p.setClubId(player.getClub().getId());
            WriteThreadClient.write(p);
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    boolean checkDuplicate(){
        if (!name.getText().strip().equalsIgnoreCase(player.getName())) return true;
        if(!country.getText().strip().equalsIgnoreCase(player.getCountry())) return true;
        if (!position.getSelectionModel().isEmpty() && !position.getValue().equals(player.getPosition())) return true;
        if (playerNumber != player.getNumber()) return true;
        if (playerAge != player.getAge()) return true;
        if(playerHeight != player.getHeight()) return true;
        if (playerSalary != player.getSalary()) return true;
        return false;
    }

    boolean validate(){
        //salary
        String salaryString = salary.getText();
        if (salaryString.isEmpty()) return false;
        try{
            playerSalary = Double.parseDouble(salaryString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerSalary<0) {
            errorLabel.setVisible(true);
            return false;
        }

        //height
        String heightString = height.getText();
        if (heightString.isEmpty()) return false;
        try{
            playerHeight = Double.parseDouble(heightString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerHeight<0) {
            errorLabel.setVisible(true);
            return false;
        }

        //height
        String ageString = age.getText();
        if (ageString.isEmpty()) return false;
        try{
            playerAge = Integer.parseInt(ageString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerAge<0) {
            errorLabel.setVisible(true);
            return false;
        }

        String numberString = number.getText();
        if (numberString.isEmpty()) return false;
        try{
            playerNumber = Integer.parseInt(numberString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerNumber<0) {
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    void iniData(){
        name.setText(player.getName());
        country.setText(player.getCountry());
        position.setPromptText(player.getPosition());
        age.setText(String.valueOf(player.getAge()));
        height.setText(String.valueOf(player.getHeight()));
        salary.setText(String.valueOf(player.getSalary()));
        number.setText(String.valueOf(player.getNumber()));
        position.setItems(observableArrayList("Goalkeeper", "Defender", "Midfielder", "Forward"));
    }

    public void init(Player player){
        this.player = player;
        iniData();
    }

}
