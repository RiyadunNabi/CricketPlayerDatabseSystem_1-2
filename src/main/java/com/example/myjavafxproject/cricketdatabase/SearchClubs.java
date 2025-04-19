package com.example.myjavafxproject.cricketdatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchClubs {
//    private List<Player> players;

//    public SearchClubs(List<Player> players) {
//        this.players = players;
//    }
    private final CricketPlayerDatabase database;


    public SearchClubs() {
        this.database = CricketPlayerDatabase.getInstance();
    }

    public List<Player> getMaxHeightPlayersInClub(String club) {
        double maxHeight = 0;
        List<Player> tallestPlayers = new ArrayList<>();

        for (Player player : database.getPlayers()) {
            if (player.getClub().equalsIgnoreCase(club)) {
                if (player.getHeight() > maxHeight) {
                    maxHeight = player.getHeight();
                    tallestPlayers.clear();
                    tallestPlayers.add(player);
                } else if (player.getHeight() == maxHeight) {
                    tallestPlayers.add(player);
                }
            }
        }
        return tallestPlayers;
    }

    public List<Player> getMaxAgePlayersInClub(String club) {
        int maxAge = 0;
        List<Player> oldestPlayers = new ArrayList<>();

        for (Player player : database.getPlayers()) {
            if (player.getClub().equalsIgnoreCase(club)) {
                if (player.getAge() > maxAge) {
                    maxAge = player.getAge();
                    oldestPlayers.clear();
                    oldestPlayers.add(player);
                } else if (player.getAge() == maxAge) {
                    oldestPlayers.add(player);
                }
            }
        }
        return oldestPlayers;
    }

    public List<Player> getMaxSalaryPlayersInClub(String club) {
        int maxSalary = 0;
        List<Player> highestPaidPlayers = new ArrayList<>();

        for (Player player : database.getPlayers()) {
            if (player.getClub().equalsIgnoreCase(club)) {
                if (player.getWeeklySalary() > maxSalary) {
                    maxSalary = player.getWeeklySalary();
                    highestPaidPlayers.clear();
                    highestPaidPlayers.add(player);
                } else if (player.getWeeklySalary() == maxSalary) {
                    highestPaidPlayers.add(player);
                }
            }
        }
        return highestPaidPlayers;
    }

    public int getTotalYearlySalaryOfClub(String club) {
        int total = 0;
        for (Player player : database.getPlayers()) {
            if (player.getClub().equalsIgnoreCase(club)) {
                total += player.getWeeklySalary();
            }
        }
        return total;
    }

    public List<Player> getPlayersInClub(String club) {
        List<Player> playersInClub = new ArrayList<>();
        for (Player player : database.getPlayers()) {
            if (player.getClub().equalsIgnoreCase(club)) {
                playersInClub.add(player);
            }
        }
        return playersInClub;
    }
}
