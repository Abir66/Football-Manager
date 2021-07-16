package network.server;

import data.CentralDatabase;
import data.Club;
import data.Player;
import network.dto.LoginRequest;
import network.dto.LoginRespond;
import java.util.ArrayList;

public class ServerRespond {

    public LoginRespond checkLogin(LoginRequest request){
        Club club = CentralDatabase.getInstance().checkClub(request.getClubName());
        if (club == null || !club.getPassword().equals(request.getPassword())){
            return new LoginRespond(false,new Club(),new ArrayList<Player>());
        }
        else {
            return new LoginRespond(true, club,CentralDatabase.getInstance().getMarketList());
        }
    }

}
