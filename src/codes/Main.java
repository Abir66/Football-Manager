package codes;

import controllers.HomepageController;
import controllers.LoginController;
import data.LocalDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.client.Client;
import network.client.ReadThreadClient;
import network.dto.LogoutRequest;
import network.util.NetworkUtil;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private Client client;
    private NetworkUtil networkUtil;
    private ReadThreadClient readThreadClient;
    private UpdateFromReadThread updater = new UpdateFromReadThread();


    public Stage getStage() {
        return stage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        String serverAddress = "127.0.0.1";
        int serverPort = 44444;
        client = new Client(serverAddress, serverPort);
        networkUtil = client.getNetworkUtil();
        readThreadClient = client.getReadThreadClient();
        readThreadClient.setUpdate(updater);

        stage = primaryStage;
        stage.setOnCloseRequest(e->closeProgram());
        showLoginPage();
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/fxml/login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.init(this);
        updater.setLoginController(controller);

        // Set the primary stage
        stage.setTitle("Football Manager - Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showHomePage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/fxml/homepage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HomepageController controller = loader.getController();
        controller.init(this);

        // Set the primary stage
        stage.setTitle("Football Manager - " + LocalDatabase.getInstance().getClub().getName());
        stage.setScene(new Scene(root));
        stage.show();
    }

    void closeProgram(){
        try {
            networkUtil.write(new LogoutRequest());
            networkUtil.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.close();
        System.out.println("Exited successfully");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
