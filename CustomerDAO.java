import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO {

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO Customers (Name, Phone) VALUES (?, ?)";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM Customers WHERE CustomerID = ?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM Customers";
    private static final String SELECT_TOP_CUSTOMERS = "SELECT c.CustomerID, c.Name, count(b.BillID) AS PurchaseCount from Customers c join Bills b on c.CustomerID = b.CustomerID GROUP BY c.CustomerID ORDER BY PurchaseCount DESC LIMIT 5";

    public void addCustomer(Customer customer) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CUSTOMER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if(getCustomerByPhoneNo(customer.getPhone())!=null) {
                System.out.println("Customer with this Phone no. already exists. ");
                return;
            }
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int customerID = rs.getInt(1);
                customer.setCustomerID(customerID);
            }
            System.out.println("Customer Added Successfully");
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByID(int customerID) {
        Customer customer = null;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CUSTOMER_BY_ID)) {
            preparedStatement.setInt(1, customerID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                customer = new Customer(customerID, name, phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomerByPhoneNo(String phoneNo) {
        Customer customer = null;
        String sql = "SELECT * FROM Customers WHERE Phone = ?";
        
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, phoneNo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int customerId = rs.getInt("CustomerID");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                
                customer = new Customer(customerId, name, phone); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_CUSTOMERS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int customerID = rs.getInt("CustomerID");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                customers.add(new Customer(customerID, name, phone));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Returning customer details");
        return customers;
    }
    public void showCustomerAnalytics() {
        String sql = "SELECT c.CustomerID, c.Name, COUNT(b.BillID) AS PurchaseCount, SUM(b.NetAmount) AS TotalSpent " +
                     "FROM Customers c " +
                     "LEFT JOIN Bills b ON c.CustomerID = b.CustomerID " +
                     "GROUP BY c.CustomerID, c.Name";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Customer Analytics:");
            System.out.println("------------------------------");
            while (rs.next()) {
                int customerID = rs.getInt("CustomerID");
                String customerName = rs.getString("Name");
                int purchaseCount = rs.getInt("PurchaseCount");
                double totalSpent = rs.getDouble("TotalSpent");
                System.out.printf("Customer ID: %d | Customer Name: %s | Purchases: %d | Total Spent: %.2f\n",
                        customerID, customerName, purchaseCount, totalSpent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,String>> getFrequentCustomers(){
        List<Map<String,String>> frequentCustomers = new ArrayList<>();

        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_TOP_CUSTOMERS);
            ResultSet rs = preparedStatement.executeQuery()){
                
                while(rs.next()){
                    int customerID = rs.getInt("CustomerID");
                    String customerName = rs.getString("Name");
                    int purchaseCount = rs.getInt("PurchaseCount");

                    Map<String,String> customer = new HashMap<>();
                    customer.put("CustomerID", String.valueOf(customerID));
                    customer.put("Name",customerName);
                    customer.put("PurchaseCount",String.valueOf(purchaseCount));

                    frequentCustomers.add(customer);
                    
                }

            }
        catch ( Exception e){
            e.printStackTrace();
        }

        return frequentCustomers;
    }
    
}
