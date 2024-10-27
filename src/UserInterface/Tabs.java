package UserInterface;

import items.itemList;

import javax.swing.*;

public abstract class Tabs {
    private final String name;

    public Tabs(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract JPanel init();
}
