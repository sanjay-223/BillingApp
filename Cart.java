import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private ProductDAO productDAO;

    public Cart(ProductDAO productDAO) {
        this.items = new ArrayList<>();
        this.productDAO = productDAO;
    }

    public boolean addProduct(Product product, int quantity) {
        int stockQuantity = productDAO.getProductByID(product.getProductID()).getStockQuantity();
        if(quantity <= 0){
            System.out.println("Minimum Quantity is 1 ");
            return false;
        }
        if (stockQuantity < quantity) {
            System.out.println("Insufficient stock for product: " + product.getProductName());
            return false;
        }
        for (CartItem item : items) {
            if (item.getProduct().getProductID() == product.getProductID()) {
                if (stockQuantity < item.getQuantity() + quantity) {
                    System.out.println("Insufficient stock for product: " + product.getProductName());
                    return false;
                }
                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }
        items.add(new CartItem(product, quantity));
        return true;
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (CartItem item : items) {
            totalAmount += item.getSubtotal();
        }
        return totalAmount;
    }

    public void displayCartDetails() {
        System.out.println("Cart Details:");
        for (CartItem item : items) {
            System.out.printf("Product: %s, Quantity: %d, Unit Price: %.2f, Subtotal: %.2f\n",
                    item.getProduct().getProductName(), item.getQuantity(), item.getProduct().getPrice(), item.getSubtotal());
        }
        double totalAmount = getTotalAmount();
        System.out.printf("Grand Total: %.2f\n\n", totalAmount);

    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart{\n");
        for (CartItem item : items) {
            sb.append(item).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
