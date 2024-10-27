package villager;

import UserInterface.DropdownSearch;
import UserInterface.Tabs;
import items.itemList;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;

public class VillagerTab extends Tabs {
    private final Villager trader = new Villager();
    private final itemList itemList;

    private JLabel villagerImage;

    private JTextField villagerName = new JTextField();
    private DropdownSearch armor;
    private DropdownSearch item;

    public VillagerTab(itemList itemList) {
        super("Villager");
        this.itemList = itemList;
    }

    @Override
    public JPanel init() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // Left side (VillagerDataMenu) and vertical separator
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(VillagerDataMenu(), BorderLayout.CENTER);
        leftPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST); // Vertical separator

        // Right side (VillagerFrame)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(villagerFrame(), BorderLayout.CENTER);

        // Add left and right panels to main panel
        panel.add(leftPanel);
        panel.add(rightPanel);

        // Bottom part (VillagerTrades) and horizontal separator
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER); // Horizontal separator
        mainPanel.add(new ListTrades(trader, itemList).getPanel(), BorderLayout.SOUTH); // VillagerTrades

        return mainPanel;
    }


    private JPanel VillagerDataMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow components to take available width

        // Set padding and alignment
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        // Villager Type Label and Dropdown
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.insets = new Insets(0, 0, 0, 0); // Padding below the label
        panel.add(new JLabel("Villager Type"), gbc);

        JComboBox<String> villagerType = new JComboBox<>(trader.getTypes());
        villagerType.setMaximumSize(new Dimension(180, 20));
        villagerType.addActionListener(e -> {
            trader.setType((String) villagerType.getSelectedItem());
            setIcon(trader.getType(), trader.getProfession());
        });
        gbc.gridy++;
        panel.add(villagerType, gbc);
        trader.setType(trader.getTypes()[0]);

        // Villager Profession Label and Dropdown
        gbc.gridy++;
        panel.add(new JLabel("Villager Profession"), gbc);

        JComboBox<String> villagerProfession = new JComboBox<>(trader.getProfessions());
        villagerProfession.setMaximumSize(new Dimension(180, 20));
        villagerProfession.addActionListener(e -> {
            trader.setProfession((String) villagerProfession.getSelectedItem());
            setIcon(trader.getType(), trader.getProfession());
        });
        gbc.gridy++;
        panel.add(villagerProfession, gbc);
        trader.setProfession(trader.getProfessions()[0]);

        // Villager Level Label and Dropdown
        gbc.gridy++;
        panel.add(new JLabel("Villager Level"), gbc);

        JComboBox<String> villagerLevel = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        villagerLevel.setMaximumSize(new Dimension(180, 20));
        villagerLevel.addActionListener(e -> trader.setLevel(Integer.parseInt((String) Objects.requireNonNull(villagerLevel.getSelectedItem()))));
        gbc.gridy++;
        panel.add(villagerLevel, gbc);

        // Villager Name Label and Text Field
        gbc.gridy++;
        panel.add(new JLabel("Villager Name"), gbc);

        JTextField villagerName = new JTextField();
        villagerName.setMaximumSize(new Dimension(180, 20));
        gbc.gridy++;
        panel.add(villagerName, gbc);
        this.villagerName = villagerName;

        // Extra Options
        gbc.gridy++;
        panel.add(getExtras(), gbc);

        // Armor Section with DropdownSearch
        JPanel armorPanel = new JPanel();
        armorPanel.setLayout(new GridBagLayout());
        JLabel armorLabel = new JLabel("Armor");
        GridBagConstraints armorGbc = new GridBagConstraints();
        armorGbc.fill = GridBagConstraints.HORIZONTAL;
        armorGbc.weightx = 1.0;
        armorGbc.gridx = 0;
        armorGbc.gridy = 0;
        armorPanel.add(armorLabel, armorGbc);

        DropdownSearch armor = new DropdownSearch(itemList.searchItemById("minecraft"));
        armorGbc.gridy++;
        armorPanel.add(armor.getPanel(), armorGbc);
        armorPanel.setMaximumSize(new Dimension(180, 50));
        this.armor = armor;

        gbc.gridy++;
        panel.add(armorPanel, gbc);

        // Item Section with DropdownSearch
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout());
        JLabel itemLabel = new JLabel("Item");
        GridBagConstraints itemGbc = new GridBagConstraints();
        itemGbc.fill = GridBagConstraints.HORIZONTAL;
        itemGbc.weightx = 1.0;
        itemGbc.gridx = 0;
        itemGbc.gridy = 0;
        itemPanel.add(itemLabel, itemGbc);

        DropdownSearch item = new DropdownSearch(itemList.searchItemById("minecraft"));
        itemGbc.gridy++;
        itemPanel.add(item.getPanel(), itemGbc);
        itemPanel.setMaximumSize(new Dimension(180, 50));
        this.item = item;

        gbc.gridy++;
        panel.add(itemPanel, gbc);

        return panel;
    }

    private JPanel villagerFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200, 400));

        // Set padding and alignment
        //panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Villager Image
        this.villagerImage = new JLabel();
        setIcon(trader.getType(), trader.getProfession());


        // Position the image in the grid (row 0, column 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1; // Allow the image to take available space in X direction
        gbc.anchor = GridBagConstraints.CENTER; // Center the image in the grid cell
        panel.add(villagerImage, gbc);

        // Copy to clipboard button
        JButton copyButton = new JButton("Copy Command");
        copyButton.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.weighty = 1; // Allow the button to take available space in Y direction
        gbc.anchor = GridBagConstraints.CENTER; // Center the button in the grid cell
        panel.add(copyButton, gbc);

        // Button action listener
        copyButton.addActionListener(e -> {
            // Generate the command and copy it to the clipboard
            System.out.println(trader.getHands() + "-" + trader.getHelmet());
            if (!villagerName.getText().isEmpty()) {
                trader.setName(villagerName.getText());
            }
            if (armor.getSelectedItem() != null) {
                trader.setHelmet(armor.getSelectedItem());
            }
            if (item.getSelectedItem() != null) {
                trader.setHands(item.getSelectedItem());
            }

            String command = trader.genCommand();
            StringSelection stringSelection = new StringSelection(command);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        return panel;
    }

    private void setIcon(String type, String profession){
        // Load image from resources
        String name = "/images/" + type + "_" + profession + ".png";
        System.out.println(name);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(name)));
        villagerImage.setIcon(icon);
    }

    private JPanel getExtras(){
        JPanel panel = new  JPanel(new GridLayout(2, 2));
        panel.setPreferredSize(new Dimension(180, 50));

        // Persistence
        JCheckBox persistence = new JCheckBox("Persistence");
        persistence.addActionListener(e -> trader.setPersistence(persistence.isSelected()));
        panel.add(persistence);

        // No AI
        JCheckBox noAI = new JCheckBox("No AI");
        noAI.addActionListener(e -> trader.setNoAI(noAI.isSelected()));
        panel.add(noAI);

        // Invulnerable
        JCheckBox invulnerable = new JCheckBox("Invulnerable");
        invulnerable.addActionListener(e -> trader.setInvulnerable(invulnerable.isSelected()));
        panel.add(invulnerable);

        // Silent
        JCheckBox silent = new JCheckBox("Silent");
        silent.addActionListener(e -> trader.setSilent(silent.isSelected()));
        panel.add(silent);

        return panel;
    }
}
