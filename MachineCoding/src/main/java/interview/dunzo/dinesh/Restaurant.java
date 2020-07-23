package interview.dunzo.dinesh;

public enum Restaurant {
    CHAI_POINT("Chai Point"),
    STAR_BUCKS("Star Bucks"),
    COFFEE_DAY("Coffee Day");

    private String restaurantName;

    Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
