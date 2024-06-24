import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDAO {

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO Payments (BillID, AmountPaid, PaymentMethod, PaymentDate) VALUES (?, ?, ?, CURDATE())";

    public void addPayment(int billID, double amountPaid, String paymentMethod) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PAYMENT_SQL)) {
            preparedStatement.setInt(1, billID);
            preparedStatement.setDouble(2, amountPaid);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
