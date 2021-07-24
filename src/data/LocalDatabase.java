package data;

import controllers.HomepageController;
import network.dto.LoginRespond;
import network.dto.PlayerEditInfo;
import network.util.NetworkUtil;

import java.util.*;

public class LocalDatabase {

    Club club;
    List<Player> clubPlayers = new ArrayList();
    List<Player> players = new ArrayList();
    List<Player> marketPlayers = new ArrayList<>();
    HomepageController homepageController;


    private static LocalDatabase instance;

    private LocalDatabase() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LocalDatabase getInstance() {
        if (instance == null) {
            instance = new LocalDatabase();
        }
        return instance;
    }


    public static LocalDatabase getInstance(LoginRespond loginRespond) {
        if (instance == null) {
            instance = new LocalDatabase();
        }
        instance.club = loginRespond.getClub();
        instance.clubPlayers = loginRespond.getClub().getPlayers();
        instance.marketPlayers = loginRespond.getMarketList();
        return instance;
    }


    public void setHomepageController(HomepageController homepageController) {
        this.homepageController = homepageController;
    }

    public HomepageController getHomepageController() {
        return homepageController;
    }

    public void setListToShow(int i){
        if (i==1) players = clubPlayers;
        else players = marketPlayers;
    }

    public Club getClub() {
        return club;
    }

    public List<Player> searchPlayer(String playerName) {
        String[] playerNames = playerName.split(",");
        List<Player> l = new ArrayList<>();
        for (String s : playerNames) {
            for (Player p : players) {
                if (s.strip().equalsIgnoreCase(p.getName())) {
                    l.add(p);
                }
            }
        }
        return l;
    }

    public List<Player> searchPlayerByPosition(String position) {
        String[] positions = position.split(",");
        List<Player> l = new ArrayList<>();
        for (String pos : positions) {
            for (Player p : players) {
                if (pos.strip().equalsIgnoreCase(p.getPosition())) {
                    l.add(p);
                }
            }
        }
        return l;
    }

    public List<Player> searchPlayerByCountry(String country) {
        String[] countries = country.split(",");
        List<Player> l = new ArrayList<>();
        for (String c : countries) {
            for (Player p : players) {
                if (c.strip().equalsIgnoreCase(p.getCountry())) {
                    l.add(p);
                }
            }
        }
        return l;
    }


    public List<Player> maxSalaryPlayers() {
        double maxSalary = 0;
        List<Player> tempPlayers = new ArrayList();
        for (Player p : players) {
            if (p.getSalary() > maxSalary) maxSalary = p.getSalary();
        }
        for (Player p : players) {
            if (p.getSalary() == maxSalary) tempPlayers.add(p);
        }
        return tempPlayers;
    }

    public List<Player> maxHeightPlayers() {
        double maxHeight = 0;
        List<Player> tempPlayers = new ArrayList();
        for (Player p : players) {
            if (p.getHeight() > maxHeight) maxHeight = p.getHeight();
        }
        for (Player p : players) {
            if (p.getHeight() == maxHeight) tempPlayers.add(p);
        }
        return tempPlayers;
    }

    public List<Player> maxAgePlayers() {
        int maxAge = 0;
        List<Player> tempPlayers = new ArrayList();
        for (Player p : players) {
            if (p.getAge() > maxAge) maxAge = p.getAge();
        }
        for (Player p : players) {
            if (p.getAge() == maxAge) tempPlayers.add(p);
        }
        return tempPlayers;
    }

    public List<Player> salaryRange(double from, double to){
        List<Player> tempPlayers = new ArrayList();
        for (Player p : players) {
            if(low(from,p.getSalary()) && high(to,p.getSalary())) tempPlayers.add(p);
        }
        return tempPlayers;
    }

    boolean low(double range, double player){
        if (range==-1) return true;
        return range <= player;
    }
    boolean high(double range, double player){
        if (range==-1) return true;
        return range >= player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public double totalSalary(){
        double totalSal = 0;
        for (Player p : players) totalSal += p.getSalary();
        return totalSal * 52;
    }

    public HashMap<String, Integer> countryWiseCount(){
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        for (Player p : players){
            String country = p.getCountry();
            Integer integer = map.get(country);
            if (integer == null) map.put(country, 1);
            else map.put(country, integer + 1);
        }
        return sortByValue(map);
    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void addToList(Player player){
        clubPlayers.add(player);
    }
    public void addToMarket(Player player){
        marketPlayers.add(player);
    }
    public void removeFromList(Player player){
        for (int i = 0; i < clubPlayers.size(); i++) {
            if(clubPlayers.get(i).getId() == player.getId()){
                clubPlayers.remove(i);
                break;
            }
        }
    }
    public void removeFromMarket(Player player){
        for (int i = 0; i < marketPlayers.size(); i++) {
            if(marketPlayers.get(i).getId() == player.getId()){
                marketPlayers.remove(i);
                break;
            }
        }
    }


    public void editPlayer(PlayerEditInfo o) {
        for (var player : clubPlayers) {
            if (player.getId() == o.getId()) player.edit(o);
        }
    }
}
