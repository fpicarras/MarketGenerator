package items;

public interface itemList {
    public void addItem(Item item);
    public void removeItem(Item item);
    public void updateItem(Item item);
    public void printItems();
    public void sortItems();
    public itemList searchItem(String name);
    public itemList searchItemById(String id);
}
