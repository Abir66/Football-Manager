package controllers;


import data.Player;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.client.WriteThreadServer;
import network.dto.SellRequest;

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

        WriteThreadServer.write(new SellRequest(player.getId(), player.getClub().getId(), price));
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void init(Player player){
        this.player = player;
        playerName.setText(player.getName());
    }

}
