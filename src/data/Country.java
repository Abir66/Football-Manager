package data;

import java.util.ArrayList;
import java.util.List;

public class Country {
    String name;
    List<Player> players = new ArrayList();

    public Country(String s){
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player){
        if(players.contains(player)) return;
        players.add(player);
    }

    public void showPlayerInfo(){
        for(Player p : players) p.showPlayerInfo();
    }

    public int playerCount(){
        return players.size();
    }

}
