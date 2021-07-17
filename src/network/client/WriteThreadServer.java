package network.client;

import network.util.NetworkUtil;

import java.io.IOException;

public class WriteThreadServer {

    public static NetworkUtil networkUtil;
    public static void write(Object o){
        new Thread(() -> {
            try {
                networkUtil.write(o);
                //if (o instanceof LogoutRequest) networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //System.out.println(o + " Sent to server..."  );
    }

    public static void setNetworkUtil(NetworkUtil networkUtil) {
        WriteThreadServer.networkUtil = networkUtil;
    }
}
