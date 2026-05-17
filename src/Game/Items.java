package Game;

import java.util.ArrayList;

public class Items {

    private ArrayList<String> itemus = new ArrayList<>();

    public Items() {
        initItems(); // auto-load items
    }

    private void initItems() {
        itemus.add("Health Potion");
        itemus.add("Revive Scroll");
        itemus.add("Mana Elixir");
        itemus.add("Fire Stone");
    }

    public ArrayList<String> getItems() {
        return itemus;
    }
}