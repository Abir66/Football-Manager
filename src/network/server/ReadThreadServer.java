package network.server;

import network.util.NetworkUtil;
import java.io.IOException;
import java.util.*;
import network.dto.*;


public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    ServerRespond serverRespond;
    private List<NetworkUtil> clientList;


    public ReadThreadServer(List<NetworkUtil> clientList, NetworkUtil networkUtil, ServerRespond serverRespond) {
        this.clientList = clientList;
        this.networkUtil = networkUtil;
        this.serverRespond = serverRespond;
        this.thr = new Thread(this);
        thr.start();
    }

    public void stopThread(){
        thr.interrupt();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                System.out.println(o);

                if(o instanceof LoginRequest){
                    LoginRespond loginRespond = serverRespond.checkLogin((LoginRequest)o);
                    if (loginRespond.isAccess()) clientList.add(networkUtil);
                    networkUtil.write(serverRespond.checkLogin((LoginRequest)o));
                }

                if (o instanceof SellRequest){
                    new Thread(() -> {
                        serverRespond.sell((SellRequest)o);
                    }).start();
                }

                if (o instanceof BuyRequest){
                    new Thread(() -> {
                        serverRespond.buy((BuyRequest)o);
                    }).start();
                }

                if (o instanceof LogoutRequest){
                    clientList.remove(networkUtil);
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
