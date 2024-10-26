package villager;

import items.Trade;

public interface Trader {
    public String genCommand();

    public void addTrade(Trade T);

    public void removeTrade(int tradeId);
}
