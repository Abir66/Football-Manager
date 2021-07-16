package network.server;

import network.util.NetworkUtil;

import java.io.IOException;
import java.util.*;

import network.dto.*;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<String, ClientInfo> clientMap;
    ServerRespond serverRespond = new ServerRespond();


    public ReadThreadServer(HashMap<String, ClientInfo> map, NetworkUtil networkUtil) {
        this.clientMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                System.out.println(o);

                if(o instanceof LoginRequest){
                    networkUtil.write(serverRespond.checkLogin((LoginRequest)o));
                }

                if (o instanceof RegisterMessage) {
                    RegisterMessage obj = (RegisterMessage) o;
                    ClientInfo clientInfo = new ClientInfo();
                    clientInfo.setPassword(obj.getPassword());
                    clientInfo.setOnline(false);
                    clientInfo.setNetworkUtil(networkUtil);
                    clientMap.put(obj.getName(), clientInfo);
                }
                if (o instanceof LoginMessage) {
                    LoginMessage obj = (LoginMessage) o;
                    ClientInfo clientInfo = clientMap.get(obj.getName());
                    if (clientInfo != null) {
                        if (clientInfo.getPassword().equals(obj.getPassword())) {
                            clientInfo.setOnline(true);
                            networkUtil.write("success");
                        } else {
                            networkUtil.write("failure");
                        }
                    }
                }
                if (o instanceof GetListMessage) {
                    List<String> clientList = new ArrayList<>();
                    GetListMessage obj = (GetListMessage) o;
                    Iterator<String> iterator = clientMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String name = iterator.next();
                        ClientInfo clientInfo = clientMap.get(name);
                        if (!name.equals(obj.getName()) && clientInfo.isOnline()) {
                            clientList.add(name);
                        }
                    }
                    GetListResponseMessage getListResponseMessage = new GetListResponseMessage();
                    getListResponseMessage.setClientList(clientList);
                    networkUtil.write(getListResponseMessage);
                }
                if (o instanceof Message) {
                    Message obj = (Message) o;
                    if (obj.getTo().equals("ALL")) {
                        // Broadcast
                        Iterator<String> iterator = clientMap.keySet().iterator();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            ClientInfo clientInfo = clientMap.get(name);
                            if (!name.equals(obj.getFrom()) && clientInfo.isOnline()) {
                                clientInfo.getNetworkUtil().write(obj);
                            }
                        }
                    } else {
                        // SendOne
                        ClientInfo clientInfo = clientMap.get(obj.getTo());
                        if (clientInfo != null) {
                            clientInfo.getNetworkUtil().write(obj);
                        }
                    }
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



