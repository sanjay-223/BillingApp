public class Customer {
    private int customerID;
    private String name;
    private String phone;

    public Customer(int customerID, String name, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "customerID=" + customerID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'';
    }


}
