package items;

import java.util.ArrayList;

public class Trade {
    private final ArrayList<Item> sellItem;
    private final ArrayList<Item> buyItem;

    public Trade() {
        this.sellItem = new ArrayList<>();
        this.buyItem = new ArrayList<>();
    }

    public void addBuyItem(Item i, int qty){
        Item n = i.cloneItem();
        n.setSellQuantity(qty);

        buyItem.add(n);
    }

    public void addSellItem(Item i, int qty){
        Item n = i.cloneItem();
        n.setSellQuantity(qty);

        sellItem.add(n);
    }

    public Item getBuyItem(int idx){
        if(idx >= buyItem.size()) return null;
        return buyItem.get(idx);
    }

    public Item getSellItem(int idx){
        if(idx >= sellItem.size()) return null;
        return sellItem.get(idx);
    }

    public String getBuyItemList(){
        StringBuilder sb = new StringBuilder("[");
        int i = 0, sz = buyItem.size();
        for(Item item : buyItem){
            i++;
            sb.append(item);
            if(i < sz) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public String getSellItemList(){
        StringBuilder sb = new StringBuilder("[");
        int i = 0, sz = sellItem.size();
        for(Item item : sellItem){
            i++;
            sb.append(item);
            if(i < sz) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("Trade:\n\tSelling:\n");
        for(Item i : sellItem){
            str.append("\t+ ").append(i.getSellQuantity()).append("x ").append(i.getName()).append("\n");
        }
        str.append("\tFor:\n");
        for(Item i : buyItem){
            str.append("\t- ").append(i.getSellQuantity()).append("x ").append(i.getName()).append("\n");
        }
        return str.toString();
    }
}
