package network.server;

import data.CentralDatabase;
import network.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, ClientInfo> clientMap;

    Server() {
        clientMap = new HashMap<>();
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
        new ReadThreadServer(clientMap, networkUtil);
    }

    public static void main(String args[]) {
        CentralDatabase.getInstance();
        Server server = new Server();
    }
}
