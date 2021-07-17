package network.client;

import network.dto.LogoutRequest;
import network.util.NetworkUtil;

import java.io.IOException;

public class WriteThreadServer {

    public static NetworkUtil networkUtil;
    public static void write(Object o){
        new Thread(() -> {
            try {
                networkUtil.write(o);
                if (o instanceof LogoutRequest) {


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void setNetworkUtil(NetworkUtil networkUtil) {
        WriteThreadServer.networkUtil = networkUtil;
    }
}
