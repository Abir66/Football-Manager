package network.dto;

import java.io.Serializable;

public class GetListMessage implements Serializable {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
