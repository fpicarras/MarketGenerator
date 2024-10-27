package villager;

import UserInterface.DropdownSearch;
import items.Item;
import items.Trade;
import items.itemList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListTrades {
    private final JPanel panel;
    private final JPanel tradeListPanel;
    private final Villager villager;

    public ListTrades(Villager villager, itemList itemList) {
        int trades_height = 50, button_height = 30, height = 325;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, height));

        this.villager = villager;

        tradeListPanel = new JPanel();
        tradeListPanel.setLayout(new BoxLayout(tradeListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(tradeListPanel);
        scrollPane.setPreferredSize(new Dimension(300, height- trades_height-button_height*2));
        panel.add(scrollPane, BorderLayout.CENTER);

        DropdownSearch b1 = new DropdownSearch(itemList);
        DropdownSearch b2 = new DropdownSearch(itemList);
        DropdownSearch s1 = new DropdownSearch(itemList);

        JPanel tradePanel = new JPanel();
        tradePanel.setLayout(new GridLayout(1, 3));
        tradePanel.setPreferredSize(new Dimension(300, trades_height));

        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new GridLayout(1, 3));
        amountPanel.setPreferredSize(new Dimension(300, button_height));

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout(1, 1));
        confirmPanel.setPreferredSize(new Dimension(300, button_height));

        tradePanel.add(b1.getPanel());
        JTextField b1Quantity = new JTextField();
        amountPanel.add(b1Quantity);
        tradePanel.add(b2.getPanel());
        JTextField b2Quantity = new JTextField();
        amountPanel.add(b2Quantity);
        tradePanel.add(s1.getPanel());
        JTextField s1Quantity = new JTextField();
        amountPanel.add(s1Quantity);
        // Create a button to add the trade
        JButton addTradeButton = new JButton("+");
        confirmPanel.add(addTradeButton);
        addTradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO - Argument validation
                Item buy1 = b1.getSelectedItem();
                Item buy2 = b2.getSelectedItem();
                Item sell = s1.getSelectedItem();
                if(b1Quantity.getText().isEmpty() || s1Quantity.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int buy1Quantity = Integer.parseInt(b1Quantity.getText());
                int buy2Quantity = 0;
                int sellQuantity = Integer.parseInt(s1Quantity.getText());
                Trade trade = new Trade();
                trade.addBuyItem(buy1, buy1Quantity);
                if (buy2 != null && !b2Quantity.getText().isEmpty()) {
                    buy2Quantity = Integer.parseInt(b2Quantity.getText());
                    trade.addBuyItem(buy2, buy2Quantity);
                }
                trade.addSellItem(sell, sellQuantity);
                addTrade(trade);
                // Clear the text fields
                b1Quantity.setText(""); b2Quantity.setText(""); s1Quantity.setText("");
                b1.clear(); b2.clear(); s1.clear();
            }
        });

        JPanel tradePanelWrapper = new JPanel();
        tradePanelWrapper.setLayout(new BorderLayout());
        tradePanelWrapper.add(tradePanel, BorderLayout.PAGE_START);
        tradePanelWrapper.add(amountPanel, BorderLayout.CENTER);
        tradePanelWrapper.add(confirmPanel, BorderLayout.PAGE_END);
        tradePanelWrapper.setPreferredSize(new Dimension(300, trades_height+button_height*2));

        panel.add(tradePanelWrapper, BorderLayout.PAGE_START);
    }

    public JPanel getPanel(){
        return panel;
    }

    protected void addTrade(Trade trade) {
        villager.addTrade(trade);

        JPanel entry = new JPanel(new GridBagLayout());
        entry.setPreferredSize(new Dimension(300, 50)); // Adjusted width
        entry.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Get buy and sell items
        Item buy1 = trade.getBuyItem(0);
        Item buy2 = trade.getBuyItem(1);
        Item sell = trade.getSellItem(0);

        // Add "buy1" label with green color
        gbc.gridx = 0;
        JLabel buy1Label = createItemLabel(buy1, Color.GREEN);
        entry.add(buy1Label, gbc);

        // Add "buy2" label with green color if it exists
        if (buy2 != null) {
            gbc.gridx = 1;
            JLabel buy2Label = createItemLabel(buy2, Color.GREEN);
            entry.add(buy2Label, gbc);
        }

        // Add "sell" label with red color
        gbc.gridx = 2;
        JLabel sellLabel = createItemLabel(sell, Color.RED);
        entry.add(sellLabel, gbc);

        // Add delete button
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tradeListPanel.remove(entry);
                tradeListPanel.revalidate();
                tradeListPanel.repaint();
                villager.removeTrade(trade);
            }
        });
        entry.add(deleteButton, gbc);

        // Add the entry to the trade list panel
        tradeListPanel.add(entry);

        // Call revalidate and repaint after adding the new entry
        tradeListPanel.revalidate();
        tradeListPanel.repaint();
    }

    private JLabel createItemLabel(Item item, Color color) {
        String itemName = item.getName();
        String displayName = itemName.length() > 10 ? itemName.substring(0, 10) + "..." : itemName;
        JLabel label = new JLabel(displayName + " x" + item.getSellQuantity());
        label.setForeground(color);
        label.setToolTipText(itemName);
        //Set as bold
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }
}