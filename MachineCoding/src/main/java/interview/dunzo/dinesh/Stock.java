package interview.dunzo.dinesh;

/**
 * Stock is nothing but assume it like a packet assume each recipie(i.e beverage) consists stock of each ingridient
 * Once they are constructed i dont see a point to edit this info so no setters
 */
public class Stock {
    private Ingridient ingridient;
    private Double quantity;
    public Stock(Ingridient ingridient, Double quantity) {
        if (quantity <= 0 ) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.ingridient = ingridient;
        this.quantity = quantity;
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    public Double getQuantity() {
        return quantity;
    }
}
