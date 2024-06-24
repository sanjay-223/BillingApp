import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CouponDAO {

    private static final String GET_COUPON_BY_CODE_SQL = "SELECT * FROM Coupons WHERE CouponCode = ? AND CustomerID = ? AND IsValid = TRUE";
    private static final String INVALIDATE_COUPON_SQL = "UPDATE Coupons SET IsValid = FALSE WHERE CouponID = ?";
    private static final String ADD_COUPON_SQL = "INSERT INTO Coupons (CouponCode, DiscountPercentage, ValidTo, CustomerID, IsValid) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_COUPON_BY_CUSTOMER_ID_SQL = "SELECT * FROM Coupons Where CustomerID = ? AND  IsValid = TRUE";

    public Coupon getCouponByCode(String couponCode, int customerID) {
        Coupon coupon = null;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_COUPON_BY_CODE_SQL)) {
            preparedStatement.setString(1, couponCode);
            preparedStatement.setInt(2, customerID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int couponID = rs.getInt("CouponID");
                String code = rs.getString("CouponCode");
                double discountPercentage = rs.getDouble("DiscountPercentage");
                Date validTo = rs.getDate("ValidTo");
                boolean isValid = rs.getBoolean("IsValid");
                coupon = new Coupon(couponID, code, discountPercentage, validTo, customerID, isValid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coupon;
    }

    public List<Coupon> getCouponsByCustomerID(int customerID) {
        List<Coupon> coupons = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_COUPON_BY_CUSTOMER_ID_SQL)) {
            preparedStatement.setInt(1, customerID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int couponID = rs.getInt("CouponID");
                String code = rs.getString("CouponCode");
                double discountPercentage = rs.getDouble("DiscountPercentage");
                Date validTo = rs.getDate("ValidTo");
                boolean isValid = rs.getBoolean("IsValid");
                coupons.add(new Coupon(couponID, code, discountPercentage, validTo, customerID, isValid));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coupons;
    }

    public void invalidateCoupon(int couponID) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INVALIDATE_COUPON_SQL)) {
            preparedStatement.setInt(1, couponID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoupon(Coupon coupon) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(ADD_COUPON_SQL)) {
            preparedStatement.setString(1, coupon.getCouponCode());
            preparedStatement.setDouble(2, coupon.getDiscountPercentage());
            preparedStatement.setDate(3, new java.sql.Date(coupon.getValidTo().getTime()));
            preparedStatement.setInt(4, coupon.getCustomerID());
            preparedStatement.setBoolean(5, coupon.isValid());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
