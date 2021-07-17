package network.client;

import codes.UpdateFromReadThread;
import network.dto.LoginRespond;
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
            int x = 0;
            while (true) {
                Object o = networkUtil.read();

                if (o instanceof LoginRespond) {
                    LoginRespond log = (LoginRespond) o;
                    update.loginAction((LoginRespond) o);
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



