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
import network.client.WriteThreadServer;
import network.dto.LoginRequest;
import network.dto.LoginRespond;
import network.util.NetworkUtil;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Main main;


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
        LoginRequest loginRequest = new LoginRequest(userName, password);
        WriteThreadServer.write(loginRequest);
    }

    public void loginAction(LoginRespond loginRespond){

        if (loginRespond.isAccess()){
            try {
                LocalDatabase localDatabase = LocalDatabase.getInstance(loginRespond);
                main.showHomePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            errorLabel.setText("Credentials do not match. Please try again.");
        }
    }

    public void init(Main main){
        this.main = main;
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
