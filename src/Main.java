import javax.swing.*;

import UserInterface.TabsFactory;
import com.formdev.flatlaf.FlatDarculaLaf;
import items.SimpleItemList;
import items.csvImporter;
import villager.VillagerTab;

public class Main {
    private static void getCSV(SimpleItemList itemList){
        csvImporter.importCSV(String.valueOf(Main.class.getResource("/items.csv")).substring(6), itemList);
    }

    public static void main(String[] args) {
        try{
            FlatDarculaLaf.setup();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e){
            e.printStackTrace();
        }

        // Depois, adicionar tabs à factory
        TabsFactory factory = TabsFactory.getInstance();
        SimpleItemList itemList = new SimpleItemList();
        getCSV(itemList);
        factory.addTab(new VillagerTab(itemList));


        SwingUtilities.invokeLater(App::new);
    }
}