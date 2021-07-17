package network.dto;

import data.Player;
import java.io.Serializable;

public class UpdateRespond implements Serializable {

    Player player;
    int sellOrBuy; //sell - > 1, Buy - >2
    int sellerID;
    int buyerID;

    public UpdateRespond(Player player, int sellOrBuy, int sellerID, int buyerID) {
        this.player = new Player(player);
        this.sellOrBuy = sellOrBuy;
        this.sellerID = sellerID;
        this.buyerID = buyerID;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSellOrBuy() {
        return sellOrBuy;
    }

    public int getSellerID() {
        return sellerID;
    }

    public int getBuyerID() {
        return buyerID;
    }
}
