package villager;

import items.*;

import java.util.ArrayList;

public class Villager implements Trader {
    private String name = null;
    private String type;
    private String profession;
    private int level;

    private Item helmet = null;
    private Item hands = null;

    private boolean persistence = false;
    private boolean noAI = false;
    private boolean invulnerable = false;
    private boolean silent = false;

    private final ArrayList<Trade> trades;

    private static final String[] types = {"desert", "jungle", "savanna", "snow", "swamp", "taiga"};
    private static final String[] professions = {"unemployed", "nitwit", "armorer", "butcher", "cartographer", "cleric", "farmer", "fisherman",
                                                "fletcher", "leatherworker", "librarian", "mason", "shepherd", "toolsmith", "weaponsmith"};

    public Villager(){
        trades = new ArrayList<Trade>();

        type = "plains";
        profession = "farmer";
        level = 5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        int found = 0;
        for(String s : types){
            if(s.equals(type)){
                found = 1;
                break;
            }
        }
        if(found == 0){
            System.out.println("[ERROR] Invalid profession given: " + type);
            return;
        }
        this.type = type;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        int found = 0;
        for(String s : professions){
            if(s.equals(profession)){
                found = 1;
                break;
            }
        }
        if(found == 0){
            System.out.println("[ERROR] Invalid profession given: " + profession);
            return;
        }
        this.profession = profession;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level > 5 || level < 1){
            System.out.println("[ERROR] Invalid level: value must be in [1, 5] - provided: " + level);
            return;
        }
        this.level = level;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) {
        this.helmet = helmet;
    }

    public Item getHands() {
        return hands;
    }

    public void setHands(Item hands) {
        this.hands = hands;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }

    public void setNoAI(boolean noAI) {
        this.noAI = noAI;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public String[] getTypes(){
        return types;
    }

    public String[] getProfessions(){
        return professions;
    }

    private String tradeToString(Trade t){
        StringBuilder sb = new StringBuilder();
        Item buy = t.getBuyItem(0);
        Item sell = t.getSellItem(0);
        Item buy2 = t.getBuyItem(1);

        sb.append("{buy: ").append(buy.toString());
        if(buy2 != null){
            sb.append(", buy2: ").append(buy2);
        }
        sb.append(", sell: ").append(sell.toString());

        // TODO - Xp and max uses
        sb.append(", rewardExp: 0b, maxUses: 9999999}");

        return sb.toString();
    }

    @Override
    public String genCommand() {
        StringBuilder sb = new StringBuilder();
        String VillagerData = "{profession: "+this.profession+", level: "+ this.level + ", type: " + this.type + "}";
        sb.append("VillagerData: ").append(VillagerData).append(", ");

        // Witchcraft moment
        if(name != null){
            String CustomName = "\"\\\"" + this.name + "\\\"\"";
            sb.append("CustomName: ").append(CustomName).append(", ");
        }

        if(helmet != null){
            String ArmorItems = "[{}, {}, {}, {id: "+ this.helmet.getId() + ", Count: 1}]";
            sb.append("ArmorItems: ").append(ArmorItems).append(", ");
        }

        if(hands != null){
            String HandItems = "[{id: " + this.hands.getId() + ", Count: 1}]";
            sb.append("HandItems: ").append(HandItems).append(", ");
        }

        // Generate the trades
        sb.append("Offers: {Recipes: [");
        int sz = trades.size(), i = 0;
        for(Trade t : this.trades){
            i++;
            sb.append(this.tradeToString(t));
            if(i < sz) sb.append(", ");
        }
        sb.append("]}");

        // Extras
        if(persistence) sb.append(", PersistenceRequired: 1");
        if(noAI) sb.append(", NoAI: 1");
        if(invulnerable) sb.append(", Invulnerable: 1");
        if(silent) sb.append(", Silent: 1");

        return "/give @p villager_spawn_egg{EntityTag: {" + sb + "}}";
    }

    @Override
    public void addTrade(Trade T) {
        this.trades.add(T);
    }

    @Override
    public void removeTrade(int tradeId) {
        this.trades.remove(tradeId);
    }

    public static void main(String[] args){
        Villager v = new Villager();

        Item i1 = new Item("emerald", "Emerald");
        Item i2 = new Item("dirt", "Dirt Block");

        Item i3 = new Item("grass_block", "Grass Block");
        Item i4 = new Item("diamond", "Diamond");

        Trade t = new Trade();
        t.addBuyItem(i1, 64);
        t.addSellItem(i2, 1);
        t.addBuyItem(i3, 56);

        Trade t2 = new Trade();
        t2.addBuyItem(i3, 1);
        t2.addSellItem(i4, 1);

        v.addTrade(t);
        v.addTrade(t2);
        v.setHelmet(i2);
        v.setName("Lucifer!");
        //v.setProfession("Amiga");
        v.setProfession("fletcher");

        System.out.println(v.genCommand());
    }
}
