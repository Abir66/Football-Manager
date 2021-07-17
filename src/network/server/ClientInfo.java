package network.server;

import network.util.NetworkUtil;

public class ClientInfo {

    private NetworkUtil networkUtil;
    private boolean isOnline;
    private int clubID;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }




}
