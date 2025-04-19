package com.example.myjavafxproject.cricketdatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {
    private static final String FILE_NAME = "players.txt";
//    private static final String FILE_NAME = "F:/CSE 108/Project/MyJavaFXProject/players.txt";

    public List<Player> loadPlayers() {
        List<Player> Players=new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String country = parts[1];
                int age = Integer.parseInt(parts[2]);
                double height = Double.parseDouble(parts[3]);
                String club = parts[4];
                String position = parts[5];
                Integer jerseyNumber= parts[6].isEmpty() ? -1 : Integer.parseInt(parts[6]);
                int weeklySalary = Integer.parseInt(parts[7]);

                Players.add(new Player(name, country, age, height, club, position, jerseyNumber, weeklySalary));
            }
        } catch (IOException e) {
            System.out.println("Error loading players: " + e.getMessage());
        }
        return Players;
    }

    public void savePlayers(List<Player> Players) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Player player : Players) {
                bw.write(player.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving players: " + e.getMessage());
        }
    }
    
}
