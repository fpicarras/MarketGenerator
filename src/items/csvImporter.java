package items;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

import items.Item;
import items.SimpleItemList;

public class csvImporter {
    private itemList itemList;

    public csvImporter(String file, itemList itemList) {
        Item i;

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] line;
            // Skip the header
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[1];
                String name = line[3];
                i = new Item(id, name);
                itemList.addItem(i);
            }
            itemList.sortItems();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Read the file and add the items to the itemList
        }
    }

    public static void main(String[] args) {
        SimpleItemList s = new SimpleItemList();
        csvImporter csvImporter = new csvImporter("items.csv", s);
        //s.printItems();

        SimpleItemList s2 = (SimpleItemList) s.searchItem("emerald");
        s2.printItems();
    }
}
