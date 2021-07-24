package controllers;

import codes.Main;
import data.LocalDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import network.client.WriteThreadClient;
import network.dto.LoginRespond;
import network.dto.SignUpRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    private Main main;

    @FXML
    private Button signUp;

    @FXML
    private Button reset;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField password;

    @FXML
    private TextField clubName;

    @FXML
    private PasswordField password2;

    @FXML
    private Button backButton;

    @FXML
    void back(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpAction(ActionEvent event) {
        String userName = clubName.getText().strip();
        String passwordText = password.getText();
        String passwordText2 = password2.getText();
        if (userName.isEmpty() || passwordText.isEmpty() || passwordText2.isEmpty()) return;
        if(!passwordText.equals(passwordText2)) {
            errorLabel.setText("Passwords do not match");
            return;
        }
        WriteThreadClient.write(new SignUpRequest(userName,passwordText));
    }

    @FXML
    void resetAction(ActionEvent event) {
        init();
    }

    public void signUpAction(LoginRespond loginRespond) {
        if (loginRespond.isAccess()) {
            try {
                LocalDatabase.getInstance(loginRespond);
                main.showHomePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        errorLabel.setText("Club with name " + clubName.getText() + " already exists.");
    }

    public void init(Main main) {
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
