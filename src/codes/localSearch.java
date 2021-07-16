package codes;

import controllers.HomepageController;
import controllers.PlayerBox;
import data.LocalDatabase;
import data.Player;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.List;

public class localSearch {

    HomepageController homepageController;
    LocalDatabase localDatabase;

    void search(int searchOption, String searchString,
                HomepageController homepageController){
        this.homepageController = homepageController;
        localDatabase = homepageController.getLocalDatabase();

        if (searchOption == 0){
            updateGui(localDatabase.getPlayers());
        }

        else if (searchOption==1){
            if (searchString.strip().isEmpty()) return;
            updateGui(localDatabase.searchPlayer(searchString));
        }
        else if (searchOption==2){
            if (searchString.strip().isEmpty()) return;
            updateGui(localDatabase.searchPlayerByPosition(searchString));
        }
        else if (searchOption==3){
            if (searchString.strip().isEmpty()) return;
            updateGui(localDatabase.searchPlayerByCountry(searchString));
        }
        else if (searchOption == 4){
            //SalaryRange
        }

        else if (searchOption == 5){
            updateGui(localDatabase.maxAgePlayers());
        }

        else if (searchOption == 6){
            updateGui(localDatabase.maxHeightPlayers());
        }
        else if (searchOption == 7){
            updateGui(localDatabase.maxSalaryPlayers());
        }
        else if (searchOption == 8){
            //countryWisePlayerCount
        }
        else if (searchOption == 9){
            //TotalSalary
        }

    }


    public void updateGui(List<Player> players){

        GridPane grid = homepageController.getGrid();
        Label notFoundLabel = homepageController.getNotFoundLabel();
        grid.getChildren().clear();

        if (players.isEmpty()){
            notFoundLabel.setVisible(true);
            return;
        }
        notFoundLabel.setVisible(false);

        for (int i = 0; i < players.size(); i++) {
            FXMLLoader fxmlLoader= new FXMLLoader();
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
}
