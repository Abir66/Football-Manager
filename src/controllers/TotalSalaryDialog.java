package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class TotalSalaryDialog {

    @FXML
    private Label clubName;

    @FXML
    private Label salary;

    public void init(String clubname, double totalSalary){
        this.clubName.setText(clubname);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        salary.setText(formatter.format(totalSalary) + " $");
    }

}
