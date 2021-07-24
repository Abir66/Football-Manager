package controllers;

import data.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import java.io.IOException;


public class PlayerBox {

    private Player player;
    double price = 5;

    @FXML
    private Label nameRow;

    @FXML
    private Label clubRow;

    @FXML
    private Button sellButton;

    @FXML
    private Button detailsButton;

    @FXML
    void details(ActionEvent event) {
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/playerDialogue.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            PlayerDialogueController playerDialogueController = fxmlLoader.getController();
            playerDialogueController.init(player, true);
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

        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/confirmSell.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            ConfirmSell controller = fxmlLoader.getController();
            controller.init(player);
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public Player getPlayer() {
        return player;
    }

    public void inti(Player player) {
        this.player = player;
        updatePlayerInfoUI();
    }

    public void updatePlayerInfoUI(){
        nameRow.setText(player.getName());
        clubRow.setText(player.getCountry() + ", " + player.getPosition() + ", " + player.getNumber());
    }
}
