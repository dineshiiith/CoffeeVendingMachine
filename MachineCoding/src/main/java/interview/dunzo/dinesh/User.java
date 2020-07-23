package interview.dunzo.dinesh;

/**
 * This class is for testing basically a user is a thread he tries to order the item(i.e beverage in this case);
 */
public class User implements Runnable {

    private VendingMachine vendingMachine;
    private String beverage;

    public User(VendingMachine vendingMachine, String beverage) {
        this.vendingMachine = vendingMachine;
        this.beverage = beverage;
    }

    @Override
    public void run() {
        vendingMachine.order(beverage);//Ordering is done here
    }
}
