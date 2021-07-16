package controllers;

import data.Player;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerBox implements Initializable {

    private Player player;

    @FXML
    private Label nameRow;

    @FXML
    private Label clubRow;

    @FXML
    private MFXButton sellButton;

    @FXML
    private MFXButton detailsButton;

    @FXML
    void details(ActionEvent event) {
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/playerDialogue.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            PlayerDialogueController playerDialogueController = fxmlLoader.getController();
            playerDialogueController.init(player);
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sell(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        updatePlayerInfoUI();
    }

    public void updatePlayerInfoUI(){
        nameRow.setText(player.getName());
        clubRow.setText(player.getClub().getName() + ", " + player.getPosition() + ", " + player.getNumber());
    }
}