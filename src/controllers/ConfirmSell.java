package controllers;


import data.LocalDatabase;
import data.Player;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.dto.SellRequest;

import java.io.IOException;

public class ConfirmSell {

    double price = 0;
    private Player player;

    @FXML
    private Label playerName;

    @FXML
    private Label name1;

    @FXML
    private Label name11;

    @FXML
    private TextField priceField;

    @FXML
    private Label errorLabel;

    @FXML
    private MFXButton cancelButton;

    @FXML
    private MFXButton sellButton;

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void sell(ActionEvent event) {
        String priceString = priceField.getText();
        if (priceString.isEmpty()) return;
        try{
            price = Double.parseDouble(priceString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return;
        }

        if (price<0) {
            errorLabel.setVisible(true);
            return;
        }

        try {
            //System.out.println(player.getName() + player.getClub().getName() + player.getClub().getId());
            LocalDatabase.getInstance().getNetworkUtil().write(new SellRequest(player.getId(), player.getClub().getId(), price));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void init(Player player){
        this.player = player;
        playerName.setText(player.getName());
    }

}
