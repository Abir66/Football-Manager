package network.server;

import network.util.NetworkUtil;
import java.io.IOException;
import network.dto.*;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    WriteThreadServer writeThreadServer;

    public ReadThreadServer(NetworkUtil networkUtil, WriteThreadServer writeThreadServer) {
        this.networkUtil = networkUtil;
        this.writeThreadServer = writeThreadServer;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                System.out.println(o);

                if (o instanceof LoginRequest) {
                    new Thread(() -> {
                        writeThreadServer.login((LoginRequest) o, networkUtil);
                    }).start();
                }

                if (o instanceof SignUpRequest) {
                    new Thread(() -> {
                        writeThreadServer.signUp((SignUpRequest) o, networkUtil);
                    }).start();
                }

                if (o instanceof SellRequest) {
                    new Thread(() -> {
                        writeThreadServer.sell((SellRequest) o);
                    }).start();
                }

                if (o instanceof BuyRequest) {
                    new Thread(() -> {
                        writeThreadServer.buy((BuyRequest) o);
                    }).start();
                }

                if (o instanceof LogoutRequest) {
                    new Thread(() -> {
                        writeThreadServer.logout(networkUtil);
                    }).start();
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
