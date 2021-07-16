package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {

    String name;
    List<Player> players = new ArrayList();
    int id;
    String password = "1234";

    public Club() {

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Club(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setClub(this);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
