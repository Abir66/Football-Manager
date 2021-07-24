package network.server;

import network.util.NetworkUtil;

import java.io.IOException;

public class ClientInfo {

    private NetworkUtil networkUtil;
    private int clubId = -1;

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public ClientInfo(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.clubId = -1;
    }

    public ClientInfo(NetworkUtil networkUtil, int clubId) {
        this.networkUtil = networkUtil;
        this.clubId = clubId;
    }

    public void write(Object o){
        try {
            networkUtil.write(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
