package items;

public class Item implements Comparable<Item>{
    private final String id;
    private final String name;
    // This price is unitary
    private final double price;
    // This quantity is the quantity of items to sell
    private int sellQuantity;

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
        this.price = 0.0;
        this.sellQuantity = 0;
    }

    public Item(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellQuantity = 0;
    }

    public Item(String id, String name, double price, int sellQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellQuantity = sellQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(int qty){ this.sellQuantity = qty; }

    public Item cloneItem(){
        return new Item(this.id, this.name, this.price, this.sellQuantity);
    }

    @Override
    public int compareTo(Item o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "{id: " + this.id + ", count: " + this.sellQuantity + "}";
    }
}
