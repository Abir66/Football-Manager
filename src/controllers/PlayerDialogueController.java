package controllers;

import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PlayerDialogueController {

    private Player player;

    @FXML
    private Label name;

    @FXML
    private Label clubName;

    @FXML
    private Label countryName;

    @FXML
    private Label age;

    @FXML
    private Label height;

    @FXML
    private Label position;

    @FXML
    private Label salary;

    void init(Player player){
        this.player = player;
        updatePlayerUI();
    }

    private void updatePlayerUI() {
        name.setText(player.getName());
        clubName.setText(player.getClub().getName());
        countryName.setText(player.getCountry());
        age.setText(player.getAge() + " years");
        height.setText(player.getHeight() + " meters");
        salary.setText(player.getSalary() + " $");
        position.setText(player.getPosition() + ", " + player.getNumber());
    }

    @FXML
    void close(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

}
