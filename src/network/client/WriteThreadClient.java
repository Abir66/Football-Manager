package network.client;

import network.dto.GetListMessage;
import network.dto.LoginMessage;
import network.dto.Message;
import network.dto.RegisterMessage;
import network.util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable {

    private Thread thr;
    private NetworkUtil networkUtil;
    private String clientName;
    private boolean isLoggedIn;

    public WriteThreadClient(NetworkUtil networkUtil) {
        System.out.println("im in baby");
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
        new ReadThreadClient(networkUtil);
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. GetList");
                System.out.println("4. SendOne");
                System.out.println("5. Broadcast");
                System.out.print("Your option: ");
                int option = input.nextInt();
                input.nextLine();
                if (option == 1) {
                    System.out.println("Enter your name for register: ");
                    String name = input.nextLine();
                    if (name.equalsIgnoreCase("ALL")) {
                        System.out.println("Invalid Name");
                        continue;
                    }
                    System.out.println("Enter your password for register: ");
                    String password = input.nextLine();
                    RegisterMessage registerMessage = new RegisterMessage();
                    registerMessage.setName(name);
                    registerMessage.setPassword(password);
                    networkUtil.write(registerMessage);
                } else if (option == 2) {
                    System.out.println("Enter your name for login: ");
                    String name = input.nextLine();
                    System.out.println("Enter your password for login: ");
                    String password = input.nextLine();
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.setName(name);
                    loginMessage.setPassword(password);
                    clientName = name;
                    networkUtil.write(loginMessage);
                } else if (option == 3) {
                    if (!isLoggedIn) {
                        System.out.println("Please login first");
                        continue;
                    }
                    GetListMessage getListMessage = new GetListMessage();
                    getListMessage.setName(clientName);
                    networkUtil.write(getListMessage);
                } else if (option == 4) {
                    if (!isLoggedIn) {
                        System.out.println("Please login first");
                        continue;
                    }
                    System.out.println("Enter the name of the network.client to send messsage: ");
                    String cname = input.nextLine();
                    System.out.println("Enter the message: ");
                    String text = input.nextLine();
                    Message message = new Message();
                    message.setFrom(clientName);
                    message.setTo(cname);
                    message.setText(text);
                    networkUtil.write(message);
                } else if (option == 5) {
                    if (!isLoggedIn) {
                        System.out.println("Please login first");
                        continue;
                    }
                    System.out.println("Enter the message to send to all clients: ");
                    String text = input.nextLine();
                    Message message = new Message();
                    message.setFrom(clientName);
                    message.setTo("ALL");
                    message.setText(text);
                    networkUtil.write(message);
                } else {
                    System.out.println("Invalid Option");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



