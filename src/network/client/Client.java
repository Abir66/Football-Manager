package network.client;

import network.util.NetworkUtil;

public class Client {
    private NetworkUtil networkUtil;
    private ReadThreadClient readThreadClient;

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public ReadThreadClient getReadThreadClient() {
        return readThreadClient;
    }

    public Client(String serverAddress, int serverPort) {
        try {
            networkUtil = new NetworkUtil(serverAddress, serverPort);
            WriteThreadClient.setNetworkUtil(networkUtil);
            readThreadClient = new ReadThreadClient(networkUtil);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}


