package network.server;

import data.CentralDatabase;
import network.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private List<NetworkUtil> clientList = new ArrayList<>();
    private ServerRespond serverRespond;

    Server() {
        serverRespond = new ServerRespond(clientList);
        try {
            serverSocket = new ServerSocket(44444);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("network.server.Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(clientList, networkUtil , serverRespond);
    }

    public static void main(String args[]) {
        CentralDatabase.getInstance();
        Server server = new Server();
    }
}
