package items;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemList implements itemList{
    private int size = 0;
    private List<Item> l = null;

    public SimpleItemList() {
        l = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<Item> getItems() {
        return l;
    }

    @Override
    public void addItem(Item item) {
        l.add(item);
        size++;
    }

    @Override
    public void removeItem(Item item) {
        l.remove(item);
        size--;
    }

    @Override
    public void updateItem(Item item){
        // Get item index
        int index = l.indexOf(item);
        // Update item
        l.set(index, item);
    }

    @Override
    public void printItems() {
        for (Item i : l) {
            System.out.println(i);
        }
    }

    @Override
    public void sortItems() {
        l.sort(null);
    }

    @Override
    public itemList searchItem(String name) {
        SimpleItemList result = new SimpleItemList();
        for (Item i : l) {
            if (i.getName().toLowerCase().contains(name.toLowerCase())) {
                result.addItem(i);
            }
        }
        return result;
    }

    @Override
    public itemList searchItemById(String id) {
        SimpleItemList result = new SimpleItemList();
        for (Item i : l) {
            if (i.getId().toLowerCase().contains(id.toLowerCase())) {
                result.addItem(i);
            }
        }
        return result;
    }
}
