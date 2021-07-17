package network.server;

import data.CentralDatabase;
import data.Club;
import data.Player;
import network.dto.*;
import network.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerRespond {


    private List<NetworkUtil> clientList = new ArrayList<>();

    public ServerRespond(List<NetworkUtil> clientList) {
        this.clientList = clientList;
    }

    public LoginRespond checkLogin(LoginRequest request){
        Club club = CentralDatabase.getInstance().checkClub(request.getClubName());
        if (club == null || !club.getPassword().equals(request.getPassword())){
            return new LoginRespond(false,null,new ArrayList<Player>());
        }
        else {
            return new LoginRespond(true, club,CentralDatabase.getInstance().getMarketList());
        }
    }

    public synchronized void sell(SellRequest sellRequest){
        Player player = CentralDatabase.getInstance().sell(sellRequest);
        if (player==null) return;
        UpdateRespond updateRespond = new UpdateRespond(player,1,player.getClub().getId(),-1);
        for (NetworkUtil client : clientList) {
            try {
                client.write(updateRespond);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void buy(BuyRequest buyRequest){
        Player player = CentralDatabase.getInstance().buy(buyRequest);
        if (player==null) return;
        UpdateRespond updateRespond = new UpdateRespond(player,2, -1, player.getClub().getId());
        for (NetworkUtil client : clientList) {
            try {
                client.write(updateRespond);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
