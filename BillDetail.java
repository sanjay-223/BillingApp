public class BillDetail {
    private int billDetailID;
    private int billID;
    private int productID;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public BillDetail(int billDetailID, int billID, int productID, int quantity, double unitPrice, double totalPrice) {
        this.billDetailID = billDetailID;
        this.billID = billID;
        this.productID = productID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public int getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(int billDetailID) {
        this.billDetailID = billDetailID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("BillDetailID: %d, ProductID: %d, Quantity: %d, UnitPrice: %.2f, TotalPrice: %.2f", 
                            billDetailID, productID, quantity, unitPrice, totalPrice);
    }

}
