package items;

import java.util.List;

public interface itemList {
    public int getSize();
    public List<Item> getItems();
    public void addItem(Item item);
    public void removeItem(Item item);
    public void updateItem(Item item);
    public void printItems();
    public void sortItems();
    public itemList searchItem(String name);
    public itemList searchItemById(String id);
}
