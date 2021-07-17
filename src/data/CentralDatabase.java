package data;

import network.dto.BuyRequest;
import network.dto.SellRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CentralDatabase {

    private static CentralDatabase instance;

    private CentralDatabase() {
        try {
            readFromInputFile();
            //writeToInputFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CentralDatabase getInstance() {
        if (instance == null) {
            instance = new CentralDatabase();
        }
        return instance;
    }

    HashMap<String, Club> clubMap = new HashMap<>();
    List<Player> players = new ArrayList();
    List<Club> clubs = new ArrayList<>();
    List<Player> marketList = new ArrayList<>();


    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "players.txt";

    public void addPlayer(Player p) {
        p.setId(players.size());
        players.add(p);
    }

    public Club addClub(String clubName) {
        if (clubMap.containsKey(clubName)) return clubMap.get(clubName);
        Club club = new Club(clubName);
        clubMap.put(clubName, club);
        club.setId(clubs.size());
        clubs.add(club);
        return club;
    }

    public void readFromInputFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Player p = new Player();
            Club club = addClub(tokens[4]);

            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setClub(club);
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setSalary(Double.parseDouble(tokens[7]));
            p.setBeingSold(Boolean.parseBoolean(tokens[8]));
            p.setPrice(Double.parseDouble(tokens[9]));
            addPlayer(p);
            if (!p.isBeingSold()) club.addPlayer(p);
            else marketList.add(p);
        }
        br.close();
        //System.out.println(players.size());
    }

    public void writeToInputFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Club club : clubs) {
            for (Player p : club.getPlayers()) {
                bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," +
                        p.getHeight() + "," + p.getClub().getName() + "," + p.getPosition() + "," +
                        p.getNumber() + "," + p.getSalary() + "," + p.isBeingSold() + "," + p.getPrice());
                bw.write("\n");
            }
        }
        bw.close();
    }

    public Club checkClub(String club) {
        return clubMap.get(club);
    }

    public List<Player> getMarketList() {
        return marketList;
    }

    public synchronized Player sell(SellRequest sellRequest) {
        Player player = players.get(sellRequest.getPlayerId());
        if (player.isBeingSold() || player.getClub().getId() != sellRequest.getSellerId()) return null;
        player.setBeingSold(true);
        player.setPrice(sellRequest.getPrice());
        clubs.get(sellRequest.getSellerId()).removePlayer(player);
        marketList.add(player);
        return player;
    }

    public synchronized Player buy(BuyRequest buyRequest){
        Player player = players.get(buyRequest.getPlayerId());
        if(!player.isBeingSold()) return null;
        player.setBeingSold(false);
        clubs.get(buyRequest.getBuyerId()).addPlayer(player);
        marketList.remove(player);
        return player;
    }


}
