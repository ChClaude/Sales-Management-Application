package SalesManagement;

public class Sale {

    private int itemsSold;
    private double totalSalesValue;

    public Sale() {
    }

    public void incrementItemsSold() {
        ++itemsSold;
    }

    public void decrementItemsSold() {
        if (itemsSold > 1)
            --itemsSold;
    }

    public void incrementTotalSalesValue(double price) {
        totalSalesValue = getItemsSold() * price;
    }

    public int getItemsSold() {
        return itemsSold;
    }

    public double getTotalSalesValue() {
        return totalSalesValue;
    }

    @Override
    public String toString() {
        return String.format("Sale = {Items sold: %d, Total sales: %.2f}", itemsSold, totalSalesValue);
    }
}
