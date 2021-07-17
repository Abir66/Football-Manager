package codes;

import controllers.HomepageController;
import controllers.LoginController;
import data.LocalDatabase;
import javafx.application.Platform;
import network.dto.LoginRespond;
import network.dto.UpdateRespond;

public class UpdateFromReadThread {

    private HomepageController homepageController;
    private LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void loginAction(LoginRespond loginRespond){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                loginController.loginAction(loginRespond);
            }
        });
        System.out.println("login Successfull");
    }

    public synchronized void updateFromServerRespond(UpdateRespond updateRespond){
        homepageController = LocalDatabase.getInstance().getHomepageController();
        int refresher = 1; //1-> refresh home 2-> refresh market 3->both
        if (updateRespond.getSellOrBuy() == 1){
            //sell
            refresher = 2;
            LocalDatabase.getInstance().addToMarket(updateRespond.getPlayer());
            if (updateRespond.getSellerID()==LocalDatabase.getInstance().getClub().getId()) {
                LocalDatabase.getInstance().removeFromList(updateRespond.getPlayer());
                refresher=3;
            }
        }
        else{
            //buy
            refresher = 1;
            LocalDatabase.getInstance().removeFromMarket(updateRespond.getPlayer());
            if (updateRespond.getBuyerID()==LocalDatabase.getInstance().getClub().getId()){
                LocalDatabase.getInstance().addToList(updateRespond.getPlayer());
                refresher = 3;
            }
        }

        int finalRefresher = refresher;
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                homepageController.getHomepageUpdater().refreshGUI(finalRefresher);
            }
        });
    }


}
