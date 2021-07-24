package controllers;

import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

    @FXML
    private Button editButton;


    @FXML
    void edit(ActionEvent event) {
//        Node node = (Node) event.getSource();
//        Stage thisStage = (Stage) node.getScene().getWindow();
//        thisStage.close();

        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/playerEdit.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            PlayerEdit playerEdit = fxmlLoader.getController();
            playerEdit.init(player);
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    void init(Player player, boolean showEditButton){
        this.player = player;
        if (!showEditButton) editButton.setVisible(false);
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
