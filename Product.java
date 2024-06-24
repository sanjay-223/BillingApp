public class Product {
    private int productID;
    private String productName;
    private String category;
    private double price;
    private double taxRate;
    private int stockQuantity;

    public Product(int productID, String productName, String category, double price, double taxRate, int stockQuantity) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.taxRate = taxRate;
        this.stockQuantity = stockQuantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return  "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", taxRate=" + taxRate +
                ", stockQuantity=" + stockQuantity;
    }
}
