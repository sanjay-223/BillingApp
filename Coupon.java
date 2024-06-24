import java.util.Date;

public class Coupon {
    private int couponID;
    private String couponCode;
    private double discountPercentage;
    private Date validTo;
    private int customerID;
    private boolean isValid;

    public Coupon(int couponID, String couponCode, double discountPercentage, Date validTo, int customerID, boolean isValid) {
        this.couponID = couponID;
        this.couponCode = couponCode;
        this.discountPercentage = discountPercentage;
        this.validTo = validTo;
        this.customerID = customerID;
        this.isValid = isValid;
    }

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponID=" + couponID +
                ", couponCode='" + couponCode + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", validTo=" + validTo +
                ", customerID=" + customerID +
                ", isValid=" + isValid +
                '}';
    }
}
