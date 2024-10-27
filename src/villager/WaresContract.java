package villager;

import items.Item;
import items.Trade;

public class WaresContract implements Trader{
    private Trade trade;

    private String title = null;
    private String buyerName = null;
    private String message = null;
    private String backsideMessage = null;

    private int experience = 0;
    private int expiresInSeconds = -1;
    private int ordered = -1;

    public WaresContract(Trade trade) {
        this.trade = trade;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBacksideMessage(String backsideMessage) {
        this.backsideMessage = backsideMessage;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setExpiresInSeconds(int expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    @Override
    public String genCommand() {
        StringBuilder nbt = new StringBuilder();

        // If there is no trade set
        if(trade == null) return null;

        nbt.append("requested: ").append(trade.getBuyItemList()).append(", ").append("payment: ").append(trade.getSellItemList()).append(", ");
        nbt.append("ordered: ").append(ordered);

        // Extras
        if(experience > 0) nbt.append(", experience: ").append(experience);
        if(expiresInSeconds > -1) nbt.append(", expiresInSeconds: ").append(expiresInSeconds);
        if(title != null) nbt.append(", title: '{\"text\":\"").append(title).append("\"}'");
        if(buyerName != null) nbt.append(", buyerName: '{text: \"").append(buyerName).append("\"}'");
        if(message != null) nbt.append(", message: '{text: \"").append(message).append("\"}'");
        if(backsideMessage != null) nbt.append(", backsideMessage: '{text: \"").append(backsideMessage).append("\"}'");

        return "/give @p wares:sealed_delivery_agreement{"+ nbt +"}";
    }

    @Override
    public void addTrade(Trade T) {
        trade = T;
    }

    @Override
    public void removeTrade(int tradeId) {
        trade = null;
    }

    public static void main(String[] args){
        Item i1 = new Item("emerald", "Emerald");
        Item i2 = new Item("dirt", "Dirt Block");

        Trade t = new Trade();
        t.addBuyItem(i1, 64);
        t.addBuyItem(i1, 64);
        t.addSellItem(i2, 1);

        WaresContract wc = new WaresContract(t);
        wc.addTrade(t);
        wc.setBacksideMessage("Olá Mãe!");
        wc.setBuyerName("João Eduardo");
        wc.setMessage("Olá Mundo!");
        wc.setTitle("Contrato de Venda");
        System.out.println(wc.genCommand());
    }
}
