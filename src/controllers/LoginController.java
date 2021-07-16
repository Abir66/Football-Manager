package controllers;

import codes.Main;
import data.LocalDatabase;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import network.dto.LoginRequest;
import network.dto.LoginRespond;
import network.util.NetworkUtil;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Main main;
    private NetworkUtil networkUtil;

    @FXML
    private MFXButton signIn;

    @FXML
    private MFXButton reset;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField password;

    @FXML
    private TextField clubName;


    @FXML
    void loginAction(ActionEvent event) {
        String userName = clubName.getText();
        String passwordText = password.getText();
        validate(userName, passwordText);
    }

    @FXML
    void resetAction(ActionEvent event) {
        init();
    }

    void validate(String userName, String password) {

        if (userName.isEmpty() && password.isEmpty()) return;
        try {
            LoginRequest loginRequest = new LoginRequest(userName, password);
            networkUtil.write(loginRequest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loginAction(LoginRespond loginRespond){

        if (loginRespond.isAccess()){
            try {
                System.out.println(loginRespond.getClub().getPlayers().size());
                LocalDatabase localDatabase = new LocalDatabase(loginRespond);
                System.out.println("sdfsdf" + loginRespond.getClub().getPlayers().size());
                System.out.println("login controller" + localDatabase.getPlayers().size());
                main.setDatabase(localDatabase);
                main.showHomePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            errorLabel.setText("Credentials do not match. Please try again.");
        }
    }

    public void init(Main main, NetworkUtil networkUtil){
        this.main = main;
        this.networkUtil = networkUtil;
        init();
    }

    void init() {
        clubName.clear();
        password.clear();
        errorLabel.setText(null);
        clubName.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
