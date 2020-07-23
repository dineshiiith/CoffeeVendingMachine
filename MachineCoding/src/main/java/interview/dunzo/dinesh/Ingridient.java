package interview.dunzo.dinesh;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Once you construct a Ingridient you cant edit it there are no setters
 */
public class Ingridient {

    static {
        unitTypes = Arrays.asList("ml", "grams", "pack");
    }
    private static List<String> unitTypes;

    private static String getDefaultType() {
        return unitTypes.get(0);
    }

    private static Boolean validName(String word) {
        return  word.matches("[A-z_a-z]+");
    }

    private String name;
    private String unit;// every Ingridient has a unit by default it is ml

    public Ingridient(String name) {
        if (!validName(name)) {
            System.out.println("Invalid name");
            throw new IllegalArgumentException("Name must contain only alphabets");
        }
        this.name = name;
        this.unit = getDefaultType();
    }

    public  Ingridient(String name, String unit) {
        if (!unitTypes.contains(unit)) {
            throw  new IllegalArgumentException("units can only be " + unitTypes.stream().collect(Collectors.joining(" ")));
        }
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
