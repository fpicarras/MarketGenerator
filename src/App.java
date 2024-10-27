import UserInterface.Tabs;
import UserInterface.TabsFactory;

import javax.swing.*;
import java.util.Objects;

public class App extends JFrame{
    private static JTabbedPane tp = null;

    public App(){
        setTitle("Market Generator");
        setSize(400, 800);

        // Set logo
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("app_logo.png"))).getImage());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Creating the different tabs
        tp = new JTabbedPane();

        // Add the tabs with different panels
        TabsFactory factory = TabsFactory.getInstance();
        while(factory.hasNext()){
            Tabs tab = factory.next();
            tp.addTab(tab.getName(), tab.init());
        }

        // Add the tabbed pane to the frame
        add(tp);
        setVisible(true);
    }
}
