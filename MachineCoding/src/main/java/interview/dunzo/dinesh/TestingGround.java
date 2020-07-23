package interview.dunzo.dinesh;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestingGround {


    public static void main(String[] args) throws IOException {
        /**
         * each function written under is a test each testCase explained in that function i am commenting each function
         * to test a function uncomment it and execute this main function
         */
        //testQueingMeachanism();
        //testQueingMechanismWhenIngridientsNotEnoughThenRejectSome();
        //testWhenIngridientSpecifiedIsnotthereinTheVendorMachine();
        //testWhenOrdersHaveNoBagsInCommon();
        //testQueingMechanismWhenLargePeopleCame();
        //testRandomTest();


    }

    private static void testRandomTest() throws IOException {
        /**
         * This is a randomTest of 8 people
         * user8 definitely get rejection since green_tea has an ingridient that vending machine dont know
         * user 7 ordered ale which does not depend on any person so it runs parallel and he gets the order
         * user5 user6 ordered khaas for which they wait for resource sharing but enough resources are present so both definitely gets the order
         * user1, user2, user3, user4 there are no enough ingridients to serve them so they getting their order is probable
         * Check number of users who got their order if serially processed it should take (FETCHING_FROM_BAG_TIME + ORDER_PREPARATION_TIME)*no_of_user_who_got_their_order
         * but it takes significantly less time.
         * make the ORDER_PREPARATION_TIME and FETCHING_FROM_BAG_TIME big like 10 and 5 respectively you can see the differnece
         */

        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "hot_tea");
        User user2 = new User(vendingMachine, "black_tea");
        User user3 = new User(vendingMachine, "hot_coffee");
        User user4 = new User(vendingMachine, "hot_coffee");
        User user5 = new User(vendingMachine, "khaas");
        User user6 = new User(vendingMachine, "khaas");
        User user7 = new User(vendingMachine, "ale");
        User user8 = new User(vendingMachine, "green_tea");


        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");
        Thread thread4 = new Thread(user4, "Vijay");
        Thread thread5 = new Thread(user5, "Ramu");
        Thread thread6 = new Thread(user6, "Ravi");
        Thread thread7 = new Thread(user7, "Laxman");
        Thread thread8 = new Thread(user8, "Bharat");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
    }


    private static void testWhenOrdersHaveNoBagsInCommon() throws IOException {
        /**
         * Here two users are buying khass and hot_tea  and ale which have ingridients independent with each other
         * in this case machine can acquire lock for khass and hot_tea and ale  simulataneously and make their orders
         * so this code runs parallel so this code if ran serial needs to take 3*(FETCHING_FROM_BAG_TIME + ORDER_PREPARATION_TIME)
         * but significantly runs faster than that
         */
        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "khaas");
        User user2 = new User(vendingMachine, "hot_tea");
        User user3 = new User(vendingMachine, "ale");

        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static void testWhenIngridientSpecifiedIsnotthereinTheVendorMachine() throws IOException {
        /**
         * Here green tea has an ingridient green_mixture, in this case the machine should reject the people who are
         * trying to order It;
         * Here 3 people came and ordered green_tea since no ingridient it dont need to wait 5 seconds to get the ingridients
         * it can simply reject this order so the time it takes to execute this is almost 0 seconds
         */
        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "green_tea");
        User user2 = new User(vendingMachine, "green_tea");
        User user3 = new User(vendingMachine, "green_tea");

        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");

        thread1.start();
        thread2.start();
        thread3.start();
    }



    private static void testQueingMechanismWhenLargePeopleCame() throws IOException {
        /**
         * Here 8 people came and ordered Khas
         * Only 3 people can get the counter
         * Since all are fighing for the same resource it takes  definitly 8*FETCHING_FROM_BAG_TIME(i gave 2) + ORDER_PREPARATION_TIME(i gave 5 )
         * and less 8*(FETCHING_TIME + ORDER_PREPARATION_TIME)
         *
         */
        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "khaas");
        User user2 = new User(vendingMachine, "khaas");
        User user3 = new User(vendingMachine, "khaas");
        User user4 = new User(vendingMachine, "khaas");
        User user5 = new User(vendingMachine, "khaas");
        User user6 = new User(vendingMachine, "khaas");
        User user7 = new User(vendingMachine, "khaas");
        User user8 = new User(vendingMachine, "khaas");

        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");
        Thread thread4 = new Thread(user4, "Vijay");
        Thread thread5 = new Thread(user5, "Ramu");
        Thread thread6 = new Thread(user6, "Ravi");
        Thread thread7 = new Thread(user7, "Laxman");
        Thread thread8 = new Thread(user8, "Bharat");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
    }

    private static void testQueingMechanismWhenIngridientsNotEnoughThenRejectSome() throws IOException {
        /**
         * The test here is there are five people trying to order hot_tea
         * There are ingridients sufficient for only 3 people
         * So that means two people should not be served hot_tea
         * The first three people who are assiged the counter gets to have hot_tea
         * the first three people are fighiting for the same resource
         * so they need to wait other people have to wait when the machine is fetching its ingridients
         * but id dont take more than 3*(FETCHING_FROM_BAG_TIME + ORDER_PREPARATION_TIME)
         */
        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "hot_tea");
        User user2 = new User(vendingMachine, "hot_tea");
        User user3 = new User(vendingMachine, "hot_tea");
        User user4 = new User(vendingMachine, "hot_tea");
        User user5 = new User(vendingMachine, "hot_tea");

        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");
        Thread thread4 = new Thread(user4, "Vijay");
        Thread thread5 = new Thread(user5, "Ramu");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

    private static void testQueingMeachanism() throws IOException {
        /**
         * The test here is there are five people trying to order masala tea
         * There are enough ingridients for all 5 people
         * when 5 people trying to order it only 3 people can get the counter and wait for their resources to unlock
         * and other 2 people have to wait
         */
        VendingMachine vendingMachine = makeVendingMachine();
        User user1 = new User(vendingMachine, "masala_tea");
        User user2 = new User(vendingMachine, "masala_tea");
        User user3 = new User(vendingMachine, "masala_tea");
        User user4 = new User(vendingMachine, "masala_tea");
        User user5 = new User(vendingMachine, "masala_tea");

        Thread thread1 = new Thread(user1, "Dinesh");
        Thread thread2 = new Thread(user2, "Rajesh");
        Thread thread3 = new Thread(user3, "Ajay");
        Thread thread4 = new Thread(user4, "Vijay");
        Thread thread5 = new Thread(user5, "Ramu");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }


















    private static VendingMachine makeVendingMachine() throws IOException {
        JSONObject json = fetchDataFromFile("src/main/java/interview/dunzo/dinesh/data.json");
        JSONObject machine = json.getJSONObject("machine");
        Integer servingSize = getOutlets(machine);
        VendingMachine vendingMachine = new VendingMachine(MachineType.BEVERAGES, Restaurant.COFFEE_DAY, servingSize);
        vendingMachine.addRecipies(fetchRecipiesMap(machine));
        vendingMachine.addBags(fetchBagMap(machine));
        return vendingMachine;
    }

    private static JSONObject fetchDataFromFile(String filePath)  throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(content);
    }

    private static Integer getOutlets(JSONObject machine) {
        JSONObject outlets = machine.getJSONObject("outlets");
        Integer servingSize = outlets.getInt("count_n");
        return servingSize;
    }

    private static Map<String, Double> fetchBagMap(JSONObject machine) {

        JSONObject total_items_quantity = machine.getJSONObject("total_items_quantity");
        Map<String, Double>map = new HashMap<String, Double>();

        for(String ingridient : total_items_quantity.keySet()) {
            Double quantity = Double.valueOf(total_items_quantity.getInt(ingridient));
            map.put(ingridient, quantity);
        }

        return map;

    }

    private static Map<String, Map<String, Double>> fetchRecipiesMap(JSONObject machine) {

        Map<String, Map<String, Double>> map  = new HashMap<String, Map<String, Double>>();
        JSONObject beverages = machine.getJSONObject("beverages");
        for(String beverage : beverages.keySet()) {
            JSONObject recipie = beverages.getJSONObject(beverage);
            Map<String, Double> recipieMap = new HashMap<String, Double>();
            for (String ingridient : recipie.keySet()) {
                Double quantity = Double.valueOf(recipie.getInt(ingridient));
                recipieMap.put(ingridient, quantity);
            }
            map.put(beverage, recipieMap);
        }

        return map;
    }
}
