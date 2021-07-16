package codes;

import controllers.HomepageController;
import controllers.LoginController;
import javafx.application.Platform;
import network.dto.LoginRespond;

public class UpdateFromReadThread {

    private HomepageController homepageController;
    private LoginController loginController;

    public HomepageController getHomepageController() {
        return homepageController;
    }

    public void setHomepageController(HomepageController homepageController) {
        this.homepageController = homepageController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

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
        System.out.println("hello World");
    }


}
