package network.client;

import codes.UpdateFromReadThread;
import controllers.HomepageController;
import controllers.LoginController;
import network.dto.GetListResponseMessage;
import network.dto.LoginRespond;
import network.dto.Message;
import network.util.NetworkUtil;

import java.io.IOException;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private UpdateFromReadThread update;

    public void setUpdate(UpdateFromReadThread update) {
        this.update = update;
    }

    public ReadThreadClient(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();

                if (o instanceof LoginRespond) {
                    LoginRespond loginRespond = (LoginRespond)o;
                    System.out.println(loginRespond.getClub().getName() + "---" + loginRespond.getClub().getPlayers().size());
                    update.loginAction((LoginRespond) o);
                }

                if (o instanceof String) {
                    // Login Response
                    String s = (String) o;
                    if (s.equals("success")) {
                        System.out.println("Login Successful");

                    } else {
                        System.out.println("Login Unsuccessful");

                    }
                }
                if (o instanceof GetListResponseMessage) {
                    // GetList Response
                    GetListResponseMessage obj = (GetListResponseMessage) o;
                    System.out.println("List of Connected Clients: ");
                    for (String clientName : obj.getClientList()) {
                        System.out.println(clientName);
                    }
                }
                if (o instanceof Message) {
                    // SendOne and Broadcast Response
                    Message obj = (Message) o;
                    System.out.println(obj.getFrom() + ", " + obj.getTo() + ", " + obj.getText());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



