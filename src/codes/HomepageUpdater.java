package codes;

import controllers.*;
import data.LocalDatabase;
import data.Player;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HomepageUpdater {

    HomepageController homepageController;
    LocalDatabase localDatabase;
    LoginController loginController;

    void search(int searchOption, String searchString, HomepageController homepageController) {
        this.homepageController = homepageController;
        localDatabase = homepageController.getLocalDatabase();

        if (searchOption == 0) {
            updateGUI(localDatabase.getPlayers());
        }

        else if (searchOption == 1) {
            if (searchString.strip().isEmpty()) return;
            updateGUI(localDatabase.searchPlayer(searchString));
        }
        else if (searchOption == 2) {
            if (searchString.strip().isEmpty()) return;
            updateGUI(localDatabase.searchPlayerByPosition(searchString));
        }
        else if (searchOption == 3) {
            if (searchString.strip().isEmpty()) return;
            updateGUI(localDatabase.searchPlayerByCountry(searchString));
        }
        else if (searchOption == 4) {
            //SalaryRange
            double from = -1, to = -1;
            String[] s = searchString.split(",");
            String fromString = "" , toString = "";
            if (s.length >= 1) fromString = s[0];
            if (s.length >= 2) toString = s[1];
            if (fromString.isEmpty() && toString.isEmpty()) return;
            if (!fromString.isEmpty()) {
                try {
                    from = Double.parseDouble(s[0]);
                } catch (Exception e) {
                    homepageController.setMessageLabel("Please enter valid numbers");
                }
            }
            if (!toString.isEmpty()) {
                try {
                    to = Double.parseDouble(s[1]);
                } catch (Exception e) {
                    homepageController.setMessageLabel("Please enter valid numbers");
                }
            }
            if (to < from && to != -1) {
                homepageController.setMessageLabel("Please enter valid numbers");
                return;
            }
            if (!(to == -1 && from == -1)) updateGUI(localDatabase.salaryRange(from, to));
        }

        else if (searchOption == 5) {
            updateGUI(localDatabase.maxAgePlayers());
        }

        else if (searchOption == 6) {
            updateGUI(localDatabase.maxHeightPlayers());
        }
        else if (searchOption == 7) {
            updateGUI(localDatabase.maxSalaryPlayers());
        }
        else if (searchOption == 8) {
            updateGUI(localDatabase.countryWiseCount());
        }

        else if (searchOption == 9) {
            //TotalSalary
            double totalSalary = localDatabase.totalSalary();
            FXMLLoader fxmlLoader= new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/fxml/totalSalaryDialog.fxml"));
            try {
                DialogPane dialogPane = fxmlLoader.load();
                TotalSalaryDialog totalSalaryDialog = fxmlLoader.getController();
                totalSalaryDialog.init("ClubNameDeoyaLagbe",totalSalary);
                Dialog dialog = new Dialog();
                dialog.setDialogPane(dialogPane);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGUI(HomepageController homepageController) {
        search(0, "", homepageController);
    }

    public void updateGUI(int searchOption, String searchString, HomepageController homepageController) {
        search(searchOption, searchString, homepageController);
    }

    public void updateGUI(List<Player> players) {
        //System.out.println(players.size());
        GridPane grid = homepageController.getGrid();
        Label notFoundLabel = homepageController.getNotFoundLabel();
        grid.getChildren().clear();
        if (players.isEmpty()) {
            notFoundLabel.setVisible(true);
            return;
        }
        notFoundLabel.setVisible(false);

        for (int i = 0; i < players.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/fxml/playerBox.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                grid.add(anchorPane,0,i+1);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
            PlayerBox playerBox = fxmlLoader.getController();
            playerBox.setPlayer(players.get(i));
        }
    }

    //country wise count
    public void updateGUI(HashMap<String , Integer> countryWiseCount) {
        GridPane grid = homepageController.getGrid();
        Label notFoundLabel = homepageController.getNotFoundLabel();
        grid.getChildren().clear();
        if (countryWiseCount.isEmpty()) {
            notFoundLabel.setVisible(true);
            return;
        }
        notFoundLabel.setVisible(false);
        int i = 0;
        for ( HashMap.Entry<String, Integer> entry : countryWiseCount.entrySet()) {
            String country = entry.getKey();
            int count = entry.getValue();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/fxml/countryWise.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                grid.add(anchorPane,0,i+1);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
            CountryWise countryDialog = fxmlLoader.getController();
            countryDialog.init(country,count);
            i++;
        }
    }

    public HomepageUpdater(HomepageController homepageController, LocalDatabase localDatabase) {
        this.homepageController = homepageController;
        this.localDatabase = localDatabase;
    }
}
