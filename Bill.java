import java.util.Date;

public class Bill {
    private int billID;
    private int customerID;
    private Date billDate;
    private double totalAmount;
    private double discount;
    private double tax;
    private double netAmount;
    private int couponID;

    public Bill(int billID, int customerID, Date billDate, double totalAmount, double discount, double tax, double netAmount, int couponID) {
        this.billID = billID;
        this.customerID = customerID;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.tax = tax;
        this.netAmount = netAmount;
        this.couponID = couponID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("BillID: %d CustomerID: %d BillDate: %s TotalAmount: %.2f Discount: %.2f Tax: %.2f NetAmount: %.2f CouponID: %d ", 
                                billID, customerID, billDate, totalAmount, discount, tax, netAmount, couponID));
        
        return sb.toString();
    }
    
}
