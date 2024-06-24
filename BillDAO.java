import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private static final String INSERT_BILL_SQL = "INSERT INTO Bills (CustomerID, BillDate, TotalAmount, Discount, Tax, NetAmount, CouponID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_BILL_DETAIL_SQL = "INSERT INTO BillDetails (BillID, ProductID, Quantity, UnitPrice, TotalPrice) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BILL_BY_ID_SQL = "SELECT * FROM Bills WHERE BillID = ?";
    private static final String GET_BILL_DETAILS_BY_BILL_ID_SQL = "SELECT * FROM BillDetails WHERE BillID = ?";
    private static final String GET_TOTAL_REVENUE = "SELECT SUM(NetAmount) as TotalRevenue FROM Bills";
    private static final String GET_ALL_BILLS = "SELECT * FROM Bills";
    private static final String GET_BILLS_BY_DATE = "SELECT * FROM Bills WHERE BillDate = ?";


    public int addBill(int customerID, double totalAmount, double discount, double tax, double netAmount, int couponID) {
        int billID = 0;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_BILL_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
            preparedStatement.setDouble(3, totalAmount);
            preparedStatement.setDouble(4, discount);
            preparedStatement.setDouble(5, tax);
            preparedStatement.setDouble(6, netAmount);
            if (couponID == 0) {
                preparedStatement.setNull(7, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(7, couponID);
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                billID = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billID;
    }

    public void addBillDetail(int billID, CartItem item) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_BILL_DETAIL_SQL)) {
            preparedStatement.setInt(1, billID);
            preparedStatement.setInt(2, item.getProduct().getProductID());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setDouble(4, item.getProduct().getPrice());
            preparedStatement.setDouble(5, item.getQuantity() * item.getProduct().getPrice());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bill getBillByID(int billID) {
        Bill bill = null;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_BILL_BY_ID_SQL)) {
            preparedStatement.setInt(1, billID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                bill = new Bill(
                        rs.getInt("BillID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("BillDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getDouble("Discount"),
                        rs.getDouble("Tax"),
                        rs.getDouble("NetAmount"),
                        rs.getInt("CouponID")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    public List<BillDetail> getBillDetailsByBillID(int billID) {
        List<BillDetail> billDetails = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_BILL_DETAILS_BY_BILL_ID_SQL)) {
            preparedStatement.setInt(1, billID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                BillDetail detail = new BillDetail(
                        rs.getInt("BillDetailID"),
                        rs.getInt("BillID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("UnitPrice"),
                        rs.getDouble("TotalPrice")
                );
                billDetails.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetails;
    }
    public void updateProductQuantity(int productID, int quantity) {
        String sql = "UPDATE Products SET StockQuantity = StockQuantity - ? WHERE ProductID = ?";
        try (Connection conn = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSalesAnalytics() {
        String sql = "SELECT BillDate, SUM(NetAmount) AS TotalSales " +
                     "FROM Bills " +
                     "GROUP BY BillDate";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Sales Analytics:");
            System.out.println("------------------------------");
            while (rs.next()) {
                Date billDate = rs.getDate("BillDate");
                double totalSales = rs.getDouble("TotalSales");
                System.out.printf("Date: %s | Total Sales: %.2f\n", billDate, totalSales);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BILLS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill(
                        resultSet.getInt("BillID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getDate("BillDate"),
                        resultSet.getDouble("TotalAmount"),
                        resultSet.getDouble("Discount"),
                        resultSet.getDouble("Tax"),
                        resultSet.getDouble("NetAmount"),
                        resultSet.getInt("CouponID")
                );
                bills.add(bill);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public List<Bill> getBillsByDate(String date) {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BILLS_BY_DATE)) {

            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill(
                        resultSet.getInt("BillID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getDate("BillDate"),
                        resultSet.getDouble("TotalAmount"),
                        resultSet.getDouble("Discount"),
                        resultSet.getDouble("Tax"),
                        resultSet.getDouble("NetAmount"),
                        resultSet.getInt("CouponID")
                );
                bills.add(bill);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public double getTotalRevenue(){
        double totalRevenue = 0;
        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_TOTAL_REVENUE)){
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    totalRevenue = rs.getDouble("TotalRevenue");
                }
            }
        catch(Exception e){
            e.printStackTrace();
        }
        return totalRevenue;
    }

}
