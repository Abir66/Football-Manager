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
    private WriteThreadServer writeThreadServer = new WriteThreadServer();

    Server() {
        try {
            serverSocket = new ServerSocket(44444);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("network.network.server.Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(networkUtil , writeThreadServer);
    }

    public static void main(String args[]) {
        CentralDatabase.getInstance();
        Server server = new Server();
    }
}
