package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class TotalSalaryDialog {

    @FXML
    private Label clubName;

    @FXML
    private Label salary;

    @FXML
    private Button closeButton;

    public void init(String clubname, double totalSalary){
        this.clubName.setText(clubname);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        salary.setText(formatter.format(totalSalary) + " $");
    }

    @FXML
    void close(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

}
