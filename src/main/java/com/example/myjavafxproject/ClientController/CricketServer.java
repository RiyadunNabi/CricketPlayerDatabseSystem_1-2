package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.cricketdatabase.CricketPlayerDatabase;
import com.example.myjavafxproject.cricketdatabase.Player;


import java.io.*;
import java.net.*;
import java.util.*;

public class CricketServer {
    private static final int PORT = 44444;

    private static final List<ObjectOutputStream> connectedClients = new ArrayList<>();
    private static final List<Player> soldPlayerList = Collections.synchronizedList(new ArrayList<>());

    private static final Map<String, String> CLUB_CREDENTIALS = new HashMap<>();
    static {
        CLUB_CREDENTIALS.put("Chennai Super Kings", "123");
        CLUB_CREDENTIALS.put("Mumbai Indians", "123");
        CLUB_CREDENTIALS.put("Royal Challengers Bangalore", "123");
        CLUB_CREDENTIALS.put("Kolkata Knight Riders", "123");
        CLUB_CREDENTIALS.put("Delhi Capitals", "123");
        CLUB_CREDENTIALS.put("Punjab Kings", "123");
        CLUB_CREDENTIALS.put("Rajasthan Royals", "123");
        CLUB_CREDENTIALS.put("Sunrisers Hyderabad", "123");
        CLUB_CREDENTIALS.put("Gujarat Titans", "123");
        CLUB_CREDENTIALS.put("Lucknow Super Giants", "123");
    }
    private final CricketPlayerDatabase database = CricketPlayerDatabase.getInstance();
    public static List<Player> getSoldPlayerList() {
        synchronized (soldPlayerList){
            return new ArrayList<>(soldPlayerList);
        }
    }

    public static void main(String[] args) {
        CricketServer server = new CricketServer();
        server.start();
    }

    private void start(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client trying to login!");
                new ClientHandler(clientSocket,database).start();
            }
        }  catch (IOException e) {
            throw new RuntimeException("Server error: " + e.getMessage(), e);
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final CricketPlayerDatabase database;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket, CricketPlayerDatabase database) {
            this.clientSocket = socket;
            this.database=database;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                synchronized (connectedClients) {
                    connectedClients.add(out);
                }

                String message;

                while((message = (String) in.readObject()) != null){
                    if (message.startsWith("LOGIN")) {
                        handleLogin(message);
                    } else if (message.startsWith("SELL_PLAYER")) {
                        handleSellPlayer(message);
                    } else if (message.startsWith("BUY_PLAYER")) {
                        handleBuyPlayer(message);
                    } else if (message.startsWith("FETCH_CLUB_PLAYER")) {
                        handleFetchClubPlayerList(message);
                    } else if (message.startsWith("FETCH_SOLD_PLAYER_LIST")) {
                        handleFetchSoldPlayerList(out);
                    } else if (message.startsWith("LOG_OUT")) {
                        database.saveUpdatedDatabase(database.getPlayers());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected.");
            } finally {
                synchronized (connectedClients) {
                    connectedClients.remove(this);
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        private void handleLogin(String message) throws IOException {
            String[] parts = message.split(",");
            String clubName = parts[1];
            String password = parts[2];

            if (CLUB_CREDENTIALS.containsKey(clubName) && CLUB_CREDENTIALS.get(clubName).equals(password)) {
                out.writeObject("LOGIN_SUCCESS");
                System.out.println("Client logged in: " + clubName);

            } else {
                out.writeObject("LOGIN_FAILED");
                System.out.println("Login failed for: " + clubName);
            }
        }

        private void handleFetchClubPlayerList(String message) throws IOException {
            String[] parts = message.split(",");
            String clubName = parts[1];

            List<Player> clubPlayers = database.getClubPlayer(clubName);

            out.reset();
            out.writeObject(clubPlayers != null ? clubPlayers : Collections.emptyList());

            System.out.println("Club players sent to client: " + clubName + "size :"+ clubPlayers.size());
        }

        private void handleSellPlayer(String message) {
            String[] parts = message.split(",");
            String playerName = parts[1];
            String price = parts[2];
            String sellingClub = parts[3];

            Player playerForSale = database.getPlayers().stream()
                    .filter(player -> player.getName().equalsIgnoreCase(playerName))
                    .findFirst()
                    .orElse(null);

            if (playerForSale != null) {
                synchronized (soldPlayerList) {
                    soldPlayerList.add(playerForSale);
                    playerForSale.setAskingPrice(price);
                    database.getClubPlayer(playerForSale.getClub()).remove(playerForSale);
                    System.out.println("Player " + playerName + " is being sold by " + sellingClub + " for " + price);
                }
                //notifyClients(playerName, price, sellingClub);
            }
        }
        private void handleFetchSoldPlayerList(ObjectOutputStream out) throws IOException {
            synchronized (soldPlayerList) {
                out.writeObject(new ArrayList<>(soldPlayerList));
            }
            System.out.println("Sold players sent to client: " + soldPlayerList+" size: "+soldPlayerList.size());
        }

//        private void notifyClients(String playerName, String price, String sellingClub) {
//            synchronized (connectedClients) {
//                for (ObjectOutputStream client : connectedClients) {
//                    try {
//                        client.writeObject("SELL_LIST_UPDATE," + playerName + "," + price + "," + sellingClub);
//                    } catch (IOException e) {
//                        System.out.println("Error notifying client: " + e.getMessage());
//                    }
//                }
//            }
//        }

        private void handleBuyPlayer(String message) {
            String[] parts = message.split(",");
            String playerName = parts[1];
            String buyerClub = parts[2];

            synchronized (soldPlayerList) {
                Player playerToBuy = soldPlayerList.stream()
                        .filter(player -> player.getName().equalsIgnoreCase(playerName))
                        .findFirst()
                        .orElse(null);

                try {
                    if (playerToBuy != null) {
                        playerToBuy.setClub(buyerClub);

                        soldPlayerList.remove(playerToBuy);

                        database.getClubPlayer(buyerClub).add(playerToBuy);

                        out.writeObject("BUY_SUCCESS");
                        System.out.println("Player " + playerName + " bought by " + buyerClub);
                    } else {
                        out.writeObject("NOT_FOUND_PLAYER_SOLD");
                        System.out.println("Player " + playerName + " not found in sold list.");
                    }
                } catch (IOException e) {
                    System.out.println("Error notifying client: " + e.getMessage());
                }
            }
        }
    }
}
