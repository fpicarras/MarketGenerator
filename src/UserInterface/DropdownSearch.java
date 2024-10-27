package UserInterface;

import items.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

public class DropdownSearch {
    private final JPanel panel;
    private final JTextField searchField;
    private final JTextField itemSelected;
    private final JComboBox<Item> comboBox;
    private final itemList itemList;

    public DropdownSearch(itemList itemList) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Set up search field and combo box
        searchField = new JTextField();
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));  // Height limited to 25 px

        comboBox = new JComboBox<>();
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));  // Same height as searchField

        this.itemList = itemList;

        // Populate the combo box with items
        for (Item item : itemList.getItems()) {
            comboBox.addItem(item);
        }

        // Set custom editor and renderer
        comboBox.setEditable(true);
        comboBox.setEditor(new ComboBoxEditor());
        comboBox.setRenderer(new ComboBoxRenderer());

        // Set up display area for the selected item
        itemSelected = new JTextField();
        itemSelected.setEditable(false);
        itemSelected.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        // Add components to the panel
        panel.add(itemSelected);
        panel.add(comboBox);

        // Add document listener to filter items when typing in the search field
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterItems(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterItems(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterItems(); }
        });

        // Action listener to update the itemSelected field when a new item is chosen
        comboBox.addActionListener(e -> {
            Item selectedItem = (Item) comboBox.getSelectedItem();
            if (selectedItem != null) {
                itemSelected.setText(selectedItem.getName());
            }
        });
    }

    private void filterItems() {
        String filter = searchField.getText();
        comboBox.removeAllItems();
        itemList filteredItems = itemList.searchItem(filter);
        if (filteredItems != null) {
            for (Item item : filteredItems.getItems()) {
                comboBox.addItem(item);
            }
            comboBox.showPopup();
        }
    }

    public Item getSelectedItem() {
        return (Item) comboBox.getSelectedItem();
    }

    public JPanel getPanel() {
        return panel;
    }

    private class ComboBoxEditor extends BasicComboBoxEditor {
        @Override
        public Component getEditorComponent() {
            return searchField;
        }
    }

    private static class ComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Item) {
                setText(((Item) value).getName());
            }
            return c;
        }
    }

    public void clear() {
        searchField.setText("");
        itemSelected.setText("");
        comboBox.removeAllItems();
        for (Item item : itemList.getItems()) {
            comboBox.addItem(item);
        }
    }

    // Test the DropdownSearch class
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dropdown Search Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        SimpleItemList itemList = new SimpleItemList();
        csvImporter.importCSV("items.csv", itemList);

        frame.add(new DropdownSearch(itemList).getPanel());
        frame.setVisible(true);
    }
}
