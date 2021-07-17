package codes;

import controllers.*;
import data.Club;
import data.LocalDatabase;
import data.Player;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HomepageUpdater {

    HomepageController homepageController;
    LocalDatabase localDatabase;
    int list = 1;
    int searchOption = 0;
    String searchString = null;
    Club club;

    public void setList(int list) {
        this.list = list;
        localDatabase.setListToShow(list);
    }

    void search() {

        if (searchOption == 0) {
            new Thread(() -> { updateGUI(localDatabase.getPlayers()); }).start();
        }

        else if (searchOption == 1) {
            if (searchString.strip().isEmpty()) return;
            new Thread(() -> { updateGUI(localDatabase.searchPlayer(searchString)); }).start();
        }

        else if (searchOption == 2) {
            if (searchString.strip().isEmpty()) return;
            new Thread(() -> { updateGUI(localDatabase.searchPlayerByPosition(searchString)); }).start();
        }

        else if (searchOption == 3) {
            if (searchString.strip().isEmpty()) return;
            new Thread(() -> { updateGUI(localDatabase.searchPlayerByCountry(searchString)); }).start();
        }

        else if (searchOption == 4) {
            //SalaryRange
            double from = -1, to = -1;
            String[] s = searchString.split(",");
            String fromString = "", toString = "";
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
            if (!(to == -1 && from == -1)) {
                homepageController.setMessageLabel(null);
                updateGUI(localDatabase.salaryRange(from, to));
            }
        }

        else if (searchOption == 5) {
            new Thread(() -> { updateGUI(localDatabase.maxAgePlayers()); }).start();
        }

        else if (searchOption == 6) {
            new Thread(() -> { updateGUI(localDatabase.maxHeightPlayers()); }).start();
        }

        else if (searchOption == 7) {
            new Thread(() -> { updateGUI(localDatabase.maxSalaryPlayers()); }).start();
        }

        else if (searchOption == 8) {
            new Thread(() -> { updateGUI(localDatabase.countryWiseCount()); }).start();
        }

        else if (searchOption == 9) {
            //TotalSalary
            double totalSalary = localDatabase.totalSalary();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/fxml/totalSalaryDialog.fxml"));
            try {
                DialogPane dialogPane = fxmlLoader.load();
                TotalSalaryDialog totalSalaryDialog = fxmlLoader.getController();
                totalSalaryDialog.init(localDatabase.getClub().getName(), totalSalary);
                Dialog dialog = new Dialog();
                dialog.setDialogPane(dialogPane);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGUI(int list) {
        setList(list);
        searchOption = 0;
        search();
    }

    public void updateGUI(int searchOption, String searchString) {
        this.searchOption = searchOption;
        this.searchString = searchString;
        search();
    }

    public synchronized void updateGUI(List<Player> players) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane grid = homepageController.getGrid();
                Label notFoundLabel = homepageController.getNotFoundLabel();
                grid.getChildren().clear();
                if (players.isEmpty()) {
                    notFoundLabel.setVisible(true);
                    return;
                }
                notFoundLabel.setVisible(false);

                String address = null;
                if (list == 1) address = "/views/fxml/playerBox.fxml";
                else if (list == 2) address = "/views/fxml/marketPlayerBox.fxml";

                for (int i = 0; i < players.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource(address));

                    try {
                        AnchorPane anchorPane = fxmlLoader.load();
                        grid.add(anchorPane, 0, i + 1);
                        GridPane.setMargin(anchorPane, new Insets(10));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (list == 1) {
                        PlayerBox playerBox = fxmlLoader.getController();
                        playerBox.inti(players.get(i));
                    }
                    else {
                        MarketPlayerBox marketPlayerBox = fxmlLoader.getController();
                        marketPlayerBox.inti(players.get(i), club);
                    }
                }
            }
        });
    }


    //country wise count
    public synchronized void updateGUI(HashMap<String, Integer> countryWiseCount) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane grid = homepageController.getGrid();
                Label notFoundLabel = homepageController.getNotFoundLabel();
                grid.getChildren().clear();
                if (countryWiseCount.isEmpty()) {
                    notFoundLabel.setVisible(true);
                    return;
                }
                notFoundLabel.setVisible(false);
                int i = 0;
                for (HashMap.Entry<String, Integer> entry : countryWiseCount.entrySet()) {
                    String country = entry.getKey();
                    int count = entry.getValue();

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/views/fxml/countryWise.fxml"));
                    try {
                        AnchorPane anchorPane = fxmlLoader.load();
                        grid.add(anchorPane, 0, i + 1);
                        GridPane.setMargin(anchorPane, new Insets(10));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CountryWise countryDialog = fxmlLoader.getController();
                    countryDialog.init(country, count);
                    i++;
                }
            }
        });
    }

    public HomepageUpdater(HomepageController homepageController) {
        this.homepageController = homepageController;
        this.localDatabase = LocalDatabase.getInstance();
        club = localDatabase.getClub();
    }

    public void refreshGUI(int refresherId) {
        if (list == 2 || list == 1 && refresherId == 1) search();
    }

}
