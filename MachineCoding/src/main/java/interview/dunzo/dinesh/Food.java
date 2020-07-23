package interview.dunzo.dinesh;


import java.util.Map;

/**
 * The vending machine outputs the Food when it cooks
 * It has name of the recipi(beverage) and a map of Ingridients and quanities used
 * You can see food class like the real food a vending machine gives
 */
public class Food {
    private String name;
    Map<String, Double> itemsServed;

    public Food(String name, Map<String, Double> itemsServed) {
        this.name = name;
        this.itemsServed = itemsServed;
    }

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getItemsServed() {
        return itemsServed;
    }

}
