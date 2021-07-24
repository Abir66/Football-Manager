package network.server;

import data.CentralDatabase;
import data.Club;
import data.Player;
import network.dto.*;
import network.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteThreadServer {


    private List<ClientInfo> clientList = new ArrayList<>();

    public synchronized void login(LoginRequest request, NetworkUtil networkUtil) {
        Club club = CentralDatabase.getInstance().checkClub(request.getClubName());
        try {
            if (club == null || !club.getPassword().equals(request.getPassword())) {
                networkUtil.write(new LoginRespond(false, null, new ArrayList<Player>()));
            }
            else {
                LoginRespond loginRespond = new LoginRespond(true, club, CentralDatabase.getInstance().getMarketList());
                networkUtil.write(loginRespond);
                clientList.add(new ClientInfo(networkUtil, club.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void signUp(SignUpRequest request, NetworkUtil networkUtil) {
        Club club = CentralDatabase.getInstance().signUpClub(request.getClubName(), request.getPassword());
        try {
            if (club == null || !club.getPassword().equals(request.getPassword())) {
                networkUtil.write(new SignUpRespond(false, null, new ArrayList<Player>()));
            }
            else {
                LoginRespond loginRespond = new SignUpRespond(true, club, CentralDatabase.getInstance().getMarketList());
                networkUtil.write(loginRespond);
                clientList.add(new ClientInfo(networkUtil, club.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sell(SellRequest sellRequest) {
        Player player = CentralDatabase.getInstance().sell(sellRequest);
        if (player == null) return;
        UpdateRespond updateRespond = new UpdateRespond(player, 1, player.getClub().getId(), -1);
        for (var client : clientList) {
            client.write(updateRespond);
        }
    }

    public synchronized void buy(BuyRequest buyRequest) {
        Player player = CentralDatabase.getInstance().buy(buyRequest);
        if (player == null) return;
        UpdateRespond updateRespond = new UpdateRespond(player, 2, -1, player.getClub().getId());
        for (var client : clientList) {
            client.write(updateRespond);
        }
    }

    public synchronized void editPlayer(PlayerEditInfo o) {
        PlayerEditInfo p = CentralDatabase.getInstance().editPlayer(o);
        if(p == null) return;
        for (var client : clientList) {
            if (client.getClubId()==p.getClubId()) client.write(p);
        }
    }

    public synchronized void logout(NetworkUtil networkUtil) {
        clientList.removeIf(client -> client.getNetworkUtil() == networkUtil);
    }


}
