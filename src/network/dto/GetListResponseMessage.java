package network.dto;

import java.io.Serializable;
import java.util.List;

public class GetListResponseMessage implements Serializable {
    private List<String> clientList;

    public List<String> getClientList() {
        return clientList;
    }

    public void setClientList(List<String> clientList) {
        this.clientList = clientList;
    }
}
