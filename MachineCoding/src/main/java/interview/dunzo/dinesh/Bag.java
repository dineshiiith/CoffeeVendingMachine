package interview.dunzo.dinesh;

/**
 * A vending machine has a bag for every Ingridient where you can add or subtract Ingridient
 * It looks like Stock class but the functionality is different
 */
public class Bag {
    private Ingridient ingridient;
    private Double quantity;
    private Boolean isLocked;

    public Bag(Ingridient ingridient, Double quantity) {
        if (quantity < 0)  {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.ingridient = ingridient;
        this.quantity = quantity;
        this.isLocked = false;
    }

    public Double getQuantity() {
        return quantity;
    }


    public String getIngridientName() {
        return this.ingridient.getName();
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    public void addQuantity(Integer val) {
        quantity = quantity + val;
    }

    public void subtractQuantity(Double val ){
        if (quantity < val)
            return;
        quantity = quantity - val;

    }

    public Boolean isLocked() {
        return isLocked;
    }

    public void setLock(Boolean status) {
        isLocked = status;
    }
}
