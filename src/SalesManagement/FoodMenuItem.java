package SalesManagement;

/**
 * SalesManagement.FoodMenuItem.java
 *
 * @author CC DE-TCHAMBILA & CK MBUYI
 */
public class FoodMenuItem implements Comparable<FoodMenuItem>{

    private String fooItem;
    private String category;
    private double price;
    private Sale saleInfo;

    public FoodMenuItem() {
    }

    public FoodMenuItem(String fooItem, String category, double price, Sale saleInfo) {
        this.fooItem = fooItem;
        this.category = category;
        this.price = price;
        this.saleInfo = saleInfo;
    }

    public void setFooItem(String fooItem) {
        this.fooItem = fooItem;
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

    public String getFooItem() {
        return fooItem;
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
                fooItem, category, price, saleInfo);
    }

    @Override
    public int compareTo(FoodMenuItem o) {
        return this.getFooItem().toLowerCase().compareTo(o.getFooItem().toLowerCase());
    }
}
