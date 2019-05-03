package SalesManagement;

public class Sale {

    private int itemsSold;
    private double totalSalesValue;

    public Sale() {
    }

    public void incrementItemsSold() {

    }

    public void incrementTotalSalesValue(double pr) {

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
