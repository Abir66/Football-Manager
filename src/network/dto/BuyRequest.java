package network.dto;


import java.io.Serializable;

public class BuyRequest implements Serializable {

    int playerId;
    int buyerId;

    public BuyRequest(int playerId, int buyerId) {
        this.playerId = playerId;
        this.buyerId = buyerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getBuyerId() {
        return buyerId;
    }
}
