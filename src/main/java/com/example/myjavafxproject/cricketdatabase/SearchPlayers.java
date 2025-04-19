package com.example.myjavafxproject.cricketdatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SearchPlayers {

    private final CricketPlayerDatabase database;


    public SearchPlayers() {
        this.database = CricketPlayerDatabase.getInstance();
    }

    public Map<String, Integer> getCountryWisePlayerCount() 
    {
        Map<String, Integer> countryCount = new HashMap<>();
        for (Player player : database.getPlayers()) {
            int count = countryCount.getOrDefault(player.getCountry(), 0);
            countryCount.put(player.getCountry(), count + 1);
        }
        return countryCount;

//        for (Map.Entry<String, Player> entry : database.getCountryPlayers().entrySet()){
//
//        }


    }

    public List<Player> getPlayersBySalaryRange(int minSalary, int maxSalary) 
    {
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : database.getPlayers()) {
            if (player.getWeeklySalary() >= minSalary && player.getWeeklySalary() <= maxSalary) {
                filteredPlayers.add(player);
            }
        }
        return filteredPlayers;
    }

    public List<Player> getPlayersByPosition(String position) 
    {
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : database.getPlayers()) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                filteredPlayers.add(player);
            }
        }
        return filteredPlayers;
    }

    public List<Player> getPlayersByClubAndCountry(String club, String country) 
    {
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : database.getPlayers()) {
            if (player.getCountry().equalsIgnoreCase(country) &&
                    (club.equalsIgnoreCase("ANY") || player.getClub().equalsIgnoreCase(club))) {
                filteredPlayers.add(player);
            }
        }
        return filteredPlayers;
    }

    public List<Player> searchByName(String name) {
        List<Player> result = new ArrayList<>();
        for (Player player : database.getPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                result.add(player);
            }
        }
        return result;
    }
}
