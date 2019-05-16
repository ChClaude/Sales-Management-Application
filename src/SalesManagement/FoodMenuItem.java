package SalesManagement;

/**
 * SalesManagement.FoodMenuItem.java
 *
 * @author CC DE-TCHAMBILA & CK MBUYI
 */
public class FoodMenuItem implements Comparable<FoodMenuItem>{

    private String foodItem;
    private String category;
    private double price;
    private Sale saleInfo;

    public FoodMenuItem() {
    }

    public FoodMenuItem(String foodItem, String category, double price, Sale saleInfo) {
        this.foodItem = foodItem;
        this.category = category;
        this.price = price;
        this.saleInfo = saleInfo;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSaleInfo(Sale saleInfo) {
        this.saleInfo = saleInfo;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public Sale getSaleInfo() {
        return saleInfo;
    }

    @Override
    public String toString() {
        return String.format("FoodMenuItem = {Food item: %s, Category: %s, Price: %.2f, Sale info: %s}",
                foodItem, category, price, saleInfo);
    }

    @Override
    public int compareTo(FoodMenuItem o) {
        return this.getFoodItem().toLowerCase().compareTo(o.getFoodItem().toLowerCase());
    }
}
