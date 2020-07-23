package interview.dunzo.dinesh;


import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static interview.dunzo.dinesh.Constants.FETCHING_FROM_BAG_TIME;
/**
 * A vending machine can be any MachineType like it can serve coffee or sanwiches
 * A vending machine belongs to a Restaurant
 */
public class VendingMachine {
    private MachineType machineType;
    private Restaurant restaurant;

    Map<String, Recipie> recipies; //String is the name of recipe(i.e beverages in this specific case)
    Map<String, Bag> bags;//String is the name of the Ingredient(i.e raw material)
    private Integer servingSize;
    private Integer currentSize;



    public VendingMachine(MachineType machineType, Restaurant restaurant, Integer servingSize) {
        if (servingSize <= 0) {
            throw  new IllegalArgumentException("serving size should be positive");
        }

        this.machineType = machineType;
        this.restaurant = restaurant;
        this.servingSize = servingSize;
        this.currentSize = 0;
        this.recipies = new HashMap<String, Recipie>();
        this.bags = new HashMap<String, Bag>();
    }

    public MachineType getMachineType() {
        return machineType;
    }

    public Restaurant  getRestaurant() {
        return restaurant;
    }

    public Integer servingSize() {
        return servingSize;
    }

    //Add recepies only once
    public void addRecipies(Map<String, Map<String, Double>> recipiesMap) {

        if (recipies.size() != 0)
            return;

        for(String recipieName: recipiesMap.keySet()) {

            List<Stock> stockList = new ArrayList<Stock>();
            for(String ingridient : recipiesMap.get(recipieName).keySet()) {
                Ingridient i = new Ingridient(ingridient);
                Double quantity = recipiesMap.get(recipieName).get(ingridient);
                stockList.add(new Stock(i, quantity));
            }
            Recipie r = new Recipie(recipieName, stockList);
            recipies.put(recipieName, r);
        }

    }

    //Add bags only once
    public void addBags(Map<String, Double> bagInfo) {

        if (bags.size() != 0) {
            return;
        }

        for(String ingridient : bagInfo.keySet()) {
            Ingridient i = new Ingridient(ingridient);
            Double quantity = bagInfo.get(ingridient);
            Bag b = new Bag(i, quantity);
            bags.put(ingridient, b);
        }

    }

    //Using String for recipieName can come with a better Class but this is suffice for our use case
    private  Food prepareFood(String recipieName)  {

        Recipie r = recipies.get(recipieName);
        List<Stock> ingridients = r.getMeRecipie();
        String user = Thread.currentThread().getName();

        List<String> requiredIngredients = ingridients.stream().map(stock -> stock.getIngridient().getName()).collect(Collectors.toList());
        //waiting until all bags are available
        Map<String, Double> itemsServed = new HashMap<String, Double>();
        synchronized (this) {
            while (!areBagsAvailable(requiredIngredients)) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    return new Food("No Food due to Internal system crash");
                }
            }


            //Checking whether all ingridients to make the recipie are present
            for (Stock stock : ingridients) {

                if (stock.getQuantity() > bags.get(stock.getIngridient().getName()).getQuantity()) {
                    System.out.println("Hey sorry " + user + " ingridients for " + recipieName + " unavailable at the momemt!");
                    return new Food("No Food");
                }
            }
            lockRequiredBags(requiredIngredients);



            //Machine collecting resources from boxes and it takes  FETCHING_FROM_BAG_TIME seconds for it
            try {
                System.out.println("Bags locked for the user: " + user + " are: " + requiredIngredients + " for " + FETCHING_FROM_BAG_TIME + " seconds");
                Thread.sleep(FETCHING_FROM_BAG_TIME*1000);
            } catch (InterruptedException ex) {}

            for (Stock stock : ingridients) {
                bags.get(stock.getIngridient().getName()).subtractQuantity(stock.getQuantity());//Decreasing quantity from bag
                itemsServed.put(stock.getIngridient().getName(), stock.getQuantity());//Adding that to food
            }
            unlockRequiredBags(requiredIngredients);
            System.out.println();
        }



            try {
                System.out.println("Hey " + user + " your ingridients were fetched from bag and it takes " + r.getTimeToPrepare() + " seconds to make prepare your food");
                Thread.sleep(r.getTimeToPrepare()*1000);//Mimicking taking of items from bags
            } catch (InterruptedException ex) {}

            //only notifyAll() is enough in the block but i want to print all the values at final point in one single
            //place so we can view what happened
            synchronized (this) {

                System.out.println("User: " + user + " unlocked these bags " + requiredIngredients);
                this.notifyAll();
                System.out.println("Hey " + user + " you have been serverd " + recipieName + " with ingridients: " + itemsServed);
                String ingridientsTally = "";
                String lockStatus = "";
                for (String ingridient : bags.keySet()) {
                    ingridientsTally = ingridientsTally + ingridient + " : " + bags.get(ingridient).getQuantity() + ", ";
                    lockStatus = lockStatus + ingridient + " : " + bags.get(ingridient).isLocked() + ", ";
                }
                System.out.println("Bags quantity after serving " + user + " : " + ingridientsTally);
                System.out.println("Lock status after serving " + user + " is " + lockStatus);
                System.out.println();
                return new Food(r.getNameOfRecipie(), itemsServed);
            }

    }

    public Food order(String recipieName) {

        String user = Thread.currentThread().getName();

        //If invalid recipieName  is sent then no need to procees him
        if (!recipies.containsKey(recipieName)) {
            System.out.println("Recepie Name not found");
            throw new IllegalArgumentException("Recipie not found");
        }

        //If any Ingridient of the recipie does not exist in bags then return
        Recipie r = recipies.get(recipieName);
        List<Stock> ingridients = r.getMeRecipie();
        for (Stock stock : ingridients) {
            if (!bags.containsKey(stock.getIngridient().getName())) {
                System.out.println(user + " your order can't be processed since " + stock.getIngridient().getName() + " does not exist");
                System.out.println();
                return new Food("No Food");
            }
         }
        //Wait if you are serving
        synchronized (this) {
            while (currentSize ==  servingSize) {
                try {
                    System.out.println("Hey "+ user + " you need to wait queue is full " + "counter size: " + currentSize);
                    System.out.println();
                    this.wait();
                } catch (InterruptedException ex) {}
            }
            currentSize++;
            System.out.println(user + " you got a counter " + " people at counter including you " + currentSize);//you will be served if ingridients are present
            System.out.println();
        }

        Food food = prepareFood(recipieName);
        synchronized (this) {
            currentSize--;
            this.notifyAll();
        }
        return food;
    }

    private Boolean areBagsAvailable(List<String> ingridients) {
        return !ingridients.stream().anyMatch((ingridient) -> bags.get(ingridient).isLocked());
    }

    //Locking the bags
    private void lockRequiredBags(List<String> ingridients) {
        for(String ingridient : ingridients) {
            bags.get(ingridient).setLock(true);
        }
    }

    //Unlocking the bags
    private void unlockRequiredBags(List<String> ingridients) {
        for(String ingridient : ingridients) {
            bags.get(ingridient).setLock(false);
        }
    }


}
