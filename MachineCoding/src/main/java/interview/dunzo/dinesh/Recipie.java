package interview.dunzo.dinesh;

import java.util.*;

/**
 * A recipie has the required Ingridients to prepare it
 * it takes timeToPrepare time to make the recipie
 */

import static interview.dunzo.dinesh.Constants.ORDER_PREPARATION_TIME;

public class Recipie {

    private String name;
    private Integer timeToPrepare;//in seconds
    private Map<String, Stock> ingridientsRequired;

    public Recipie(String name, Integer timeToPrepare, List<Stock> stockList) {
        //TODO going without validation going with a hotfix
        this.name = name;
        this.timeToPrepare = timeToPrepare;
        ingridientsRequired = new HashMap<String, Stock>();
        for (Stock stock : stockList) {
            ingridientsRequired.put(stock.getIngridient().getName(), stock);
        }
    }
    //If timeToPrepare is not given use the default value
    public Recipie(String name, List<Stock> stockList) {
        this.name = name;
        this.timeToPrepare = ORDER_PREPARATION_TIME;
        ingridientsRequired = new HashMap<String, Stock>();
        //System.out.println(stockList.get(0).getIngridient().getName());
        for (Stock stock : stockList) {
            ingridientsRequired.put(stock.getIngridient().getName(), stock);
        }
    }

    public String getNameOfRecipie() {
        return name;
    }

    public Set<String> getIngridients() {
        return ingridientsRequired.keySet();
    }

    public Map<String, Stock> getIngridientsRequired() {
        return ingridientsRequired;
    }

    public List<Stock> getMeRecipie() {
        List<Stock> stocks = new ArrayList<Stock>();
        for( String ingridient : ingridientsRequired.keySet()) {
            stocks.add(ingridientsRequired.get(ingridient));
        }
        return stocks;
    }

    public Integer getTimeToPrepare() {
        return timeToPrepare;
    }
}
