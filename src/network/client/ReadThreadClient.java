package network.client;

import codes.UpdateFromReadThread;
import network.dto.LoginRespond;
import network.dto.SignUpRespond;
import network.dto.UpdateRespond;
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

                if (o instanceof SignUpRespond) {
                    LoginRespond login = (SignUpRespond) o;
                    update.signInAction((SignUpRespond) o);
                }
                else if (o instanceof LoginRespond) {
                    LoginRespond login = (LoginRespond) o;
                    update.loginAction(login);
                }
                if(o instanceof UpdateRespond){
                    update.updateFromServerRespond((UpdateRespond) o);
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



