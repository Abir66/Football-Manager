package network.client;

import codes.UpdateFromReadThread;
import network.dto.*;
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
                    update.signUpAction((SignUpRespond) o);
                }

                else if (o instanceof LoginRespond) {
                    update.loginAction((LoginRespond) o);
                }

                if(o instanceof UpdateRespond){
                    update.updateFromServerRespond((UpdateRespond) o);
                }

                if(o instanceof PlayerEditInfo){
                    update.editPlayerInfo((PlayerEditInfo) o);
                }

                if (o instanceof CloseGUI){
                    break;
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



