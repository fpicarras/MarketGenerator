package UserInterface;

import java.util.ArrayList;
import java.util.Iterator;

public class TabsFactory implements Iterator<Tabs> {
    private static final ArrayList<Tabs> tabs = new ArrayList<>();
    private static final TabsFactory instance = new TabsFactory();

    private TabsFactory(){}

    public static TabsFactory getInstance(){
        return instance;
    }

    public void addTab(Tabs tab){
        tabs.add(tab);
    }

    @Override
    public boolean hasNext() {
        return !tabs.isEmpty();
    }

    @Override
    public Tabs next() {
        if(tabs.isEmpty()){
            return null;
        }
        Tabs tab = tabs.getFirst();
        tabs.removeFirst();
        return tab;
    }

    @Override
    public void remove() {
        tabs.clear();
    }
}
