package com.example.myjavafxproject.ClientController;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 44444;

    public ClientConnection() throws IOException {
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(String message) throws IOException {
        out.writeObject(message);
    }

    public Object receiveMessage() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public void close() throws IOException {
        socket.close();
    }
}

