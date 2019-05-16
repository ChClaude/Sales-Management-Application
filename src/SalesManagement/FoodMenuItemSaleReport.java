package SalesManagement;

public class FoodMenuItemSaleReport {

    private String itemName;
    private int salesCount;
    private double total;


    public FoodMenuItemSaleReport(FoodMenuItem foodMenuItem) {

        itemName = foodMenuItem.getFoodItem();
        salesCount = foodMenuItem.getSaleInfo().getItemsSold();
        total = foodMenuItem.getSaleInfo().getTotalSalesValue();

    }

    public String getItemName() {
        return itemName;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public double getTotal() {
        return total;
    }
}
