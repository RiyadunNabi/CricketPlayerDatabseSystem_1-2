package com.example.myjavafxproject.cricketdatabase;

import java.util.*;

public class CricketPlayerDatabase {

    private List<Player> Players;
    private FileOperations fileOperations;
    private SearchPlayers SearchPlayers;
    private SearchClubs SearchClubs;
    private Map<String, List<Player>> ClubPlayers;
    private Map<String, List<Player>> CountryPlayers;
    private static CricketPlayerDatabase instance;

    public CricketPlayerDatabase() {
        fileOperations = new FileOperations();
        Players = fileOperations.loadPlayers();
//        SearchPlayers=new SearchPlayers(Players);
//        SearchClubs=new SearchClubs(Players);
        LoadCountryClub();
    }

    public void LoadCountryClub() {
        ClubPlayers = new HashMap<>();
        CountryPlayers = new HashMap<>();


        for (Player player : Players) {
            CountryPlayers.computeIfAbsent(player.getCountry(), k -> new ArrayList<>()).add(player);
            ClubPlayers.computeIfAbsent(player.getClub(), k -> new ArrayList<>()).add(player);
        }
    }

    public List<Player> getPlayers() {
        return Players;
    }

    public Map<String, List<Player>> getAllClubPlayers() {
        return ClubPlayers;
    }

    public Map<String, List<Player>> getCountryPlayers() {
        return CountryPlayers;
    }

    public List<Player> getClubPlayer(String clubName) {
        for (Map.Entry<String, List<Player>> entry : ClubPlayers.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(clubName)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }




    public static CricketPlayerDatabase getInstance() {
        if (instance == null) {
            instance = new CricketPlayerDatabase();
        }
        return instance;
    }

    public void addNewPlayer(Player newPlayer) {
        Players.add(newPlayer);
        this.LoadCountryClub();
        fileOperations.savePlayers(Players);
    }

    public void saveUpdatedDatabase(List<Player> players){
        fileOperations.savePlayers(players);
    }
}
