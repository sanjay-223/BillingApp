import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;

public class ProductDAO {

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO Products (ProductName, Category, Price, TaxRate, StockQuantity) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Products";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Products WHERE ProductID = ?";

    public void addProduct(Product product) {
        try (Connection conn = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PRODUCT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getTaxRate());
            preparedStatement.setInt(5, product.getStockQuantity());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int productID = rs.getInt(1);
                product.setProductID(productID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String category = rs.getString("Category");
                double price = rs.getDouble("Price");
                double taxRate = rs.getDouble("TaxRate");
                int stockQuantity = rs.getInt("StockQuantity");
                products.add(new Product(productID, productName, category, price, taxRate, stockQuantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductByID(int productID) {
        Product product = null;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, productID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String productName = rs.getString("ProductName");
                String category = rs.getString("Category");
                double price = rs.getDouble("Price");
                double taxRate = rs.getDouble("TaxRate");
                int stockQuantity = rs.getInt("StockQuantity");
                product = new Product(productID, productName, category, price, taxRate, stockQuantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }    

    public void updateProductQuantity(int productID, int quantityPurchased) {
        String UPDATE_PRODUCT_QUANTITY_SQL = "UPDATE Products SET StockQuantity = StockQuantity - ? WHERE ProductID = ?";

        try (Connection conn = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PRODUCT_QUANTITY_SQL)) {
            preparedStatement.setInt(1, quantityPurchased);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public void showProductAnalytics() {
        String sql = "SELECT p.ProductName, p.StockQuantity, SUM(bd.Quantity) AS SoldQuantity " +
                     "FROM Products p " +
                     "LEFT JOIN BillDetails bd ON p.ProductID = bd.ProductID " +
                     "GROUP BY p.ProductID";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Product Analytics:");
            System.out.println("------------------------------");
            while (rs.next()) {
                String productName = rs.getString("ProductName");
                int stockQuantity = rs.getInt("StockQuantity");
                int soldQuantity = rs.getInt("SoldQuantity");
                System.out.printf("Product: %s | Current Stock: %d | Sold Quantity: %d\n", productName, stockQuantity, soldQuantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentStockQuantity(int productID) {
        String sql = "SELECT StockQuantity FROM Products WHERE ProductID = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("StockQuantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean updateProduct(Product product) {
        String sql = "UPDATE Products SET ProductName = ?, Category = ?, Price = ?, TaxRate = ?, StockQuantity = ? WHERE ProductID = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getTaxRate());
            preparedStatement.setInt(5, product.getStockQuantity());
            preparedStatement.setInt(6, product.getProductID());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Map<String, Integer> getSalesByCategory() {
        Map<String, Integer> salesByCategory = new HashMap<>();
        String sql = "SELECT p.Category, SUM(bd.Quantity) AS QuantitySold " +
                    "FROM Products p JOIN BillDetails bd ON p.ProductID = bd.ProductID " +
                    "GROUP BY p.Category";
        try (Connection conn = JDBCUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                salesByCategory.put(rs.getString("Category"), rs.getInt("QuantitySold"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesByCategory;
    }

}
