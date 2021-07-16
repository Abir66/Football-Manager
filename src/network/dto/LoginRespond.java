package network.dto;

import data.Club;
import data.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginRespond implements Serializable {
    boolean access = false;
    Club club;
    List<Player> marketList = new ArrayList<>();

    public LoginRespond(boolean access, Club club, List<Player> marketList) {
        this.access = access;
        this.club = club;
        System.out.println(club.getPlayers().size());
        this.marketList = marketList;
    }

    public boolean isAccess() {
        return access;
    }

    public Club getClub() {
        return club;
    }

    public List<Player> getMarketList() {
        return marketList;
    }
}
