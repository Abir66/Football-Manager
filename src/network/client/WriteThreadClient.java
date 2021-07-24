package network.client;

import network.util.NetworkUtil;
import java.io.IOException;

public class WriteThreadClient {

    public static NetworkUtil networkUtil;
    public static void write(Object o){
        new Thread(() -> {
            try {
                networkUtil.write(o);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void setNetworkUtil(NetworkUtil networkUtil) {
        WriteThreadClient.networkUtil = networkUtil;
    }
}
