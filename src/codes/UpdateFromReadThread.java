package codes;

import controllers.HomepageController;
import controllers.LoginController;
import controllers.SignUpController;
import data.LocalDatabase;
import javafx.application.Platform;
import network.dto.LoginRespond;
import network.dto.PlayerEditInfo;
import network.dto.SignUpRespond;
import network.dto.UpdateRespond;

public class UpdateFromReadThread {

    private HomepageController homepageController;
    private LoginController loginController;
    private SignUpController signUpController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setSignUpController(SignUpController signUpController) {
        this.signUpController = signUpController;
    }

    public void loginAction(LoginRespond loginRespond){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginController.loginAction(loginRespond);
            }
        });
    }

    public synchronized void updateFromServerRespond(UpdateRespond updateRespond){
        homepageController = LocalDatabase.getInstance().getHomepageController();
        int refresher = 0; //1-> refresh home 2-> refresh market 3->both
        if (updateRespond.getSellOrBuy() == 1){
            //sell
            LocalDatabase.getInstance().addToMarket(updateRespond.getPlayer());
            if (updateRespond.getSellerID()==LocalDatabase.getInstance().getClub().getId()) {
                LocalDatabase.getInstance().removeFromList(updateRespond.getPlayer());
                refresher=1;
            }
        }
        else{
            //buy
            LocalDatabase.getInstance().removeFromMarket(updateRespond.getPlayer());
            if (updateRespond.getBuyerID()==LocalDatabase.getInstance().getClub().getId()){
                LocalDatabase.getInstance().addToList(updateRespond.getPlayer());
                refresher = 1;
            }
        }

        int finalRefresher = refresher;
        homepageController.getHomepageUpdater().refreshGUI(finalRefresher);
    }

    public synchronized void editPlayerInfo(PlayerEditInfo o) {
        homepageController = LocalDatabase.getInstance().getHomepageController();
        LocalDatabase.getInstance().editPlayer(o);
        System.out.println("huh");
        homepageController.getHomepageUpdater().refreshGUI(2);
    }

    public void signInAction(LoginRespond o) {
        Platform.runLater(() -> signUpController.signUpAction(o));
    }


}
