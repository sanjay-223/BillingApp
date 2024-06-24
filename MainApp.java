import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final BillDAO billDAO = new BillDAO();
    private static final CouponDAO couponDAO = new CouponDAO();
    private static final PaymentDAO paymentDAO = new PaymentDAO();
    private static final Cart cart = new Cart(productDAO);
    private static int currentCustomerID = 0;

    public static void main(String[] args) {
        displayMainMenu();

        boolean exit = false;
        while (!exit) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayCustomerMenu();
                    handleCustomerMenu();
                    break;
                case 2:
                    displayProductMenu();
                    handleProductMenu();
                    break;
                case 3:
                    displayPurchaseMenu();
                    handlePurchaseMenu();
                    break;
                case 4:
                    displayAnalyticsMenu();
                    handleAnalyticsMenu();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again:");
                    break;
            }

            if (!exit) {
                displayMainMenu();
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\nWelcome to Billing System");
        System.out.println("1. Customer Management");
        System.out.println("2. Product Management");
        System.out.println("3. Purchase");
        System.out.println("4. Analytics");
        System.out.println("0. Exit");
        System.out.println("\n");
        System.out.println("Enter your choice:");
    }

    private static void displayCustomerMenu() {
        System.out.println("\nCustomer Management");
        System.out.println("1. Add Customer");
        System.out.println("2. List All Customers");
        System.out.println("0. Back to Main Menu");
        System.out.println("\n");
        System.out.println("Enter your choice:");
    }

    private static void handleCustomerMenu() {
        boolean back = false;
        while (!back) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    listAllCustomers();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again:");
                    break;
            }

            if (!back) {
                displayCustomerMenu();
            }
        }
    }

    private static void displayProductMenu() {
        System.out.println("\nProduct Management");
        System.out.println("1. Add Product");
        System.out.println("2. List All Products");
        System.out.println("3. Modify Product");
        System.out.println("0. Back to Main Menu");
        System.out.println("\n");
        System.out.println("Enter your choice:");
    }

    private static void handleProductMenu() {
        boolean back = false;
        while (!back) {
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    listAllProducts();
                    break;
                case 3:
                    modifyProduct();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again:");
                    break;
            }

            if (!back) {
                displayProductMenu();
            }
        }
    }

    private static void displayPurchaseMenu() {
        System.out.println("\nPurchase");
        System.out.println("1. Select Customer");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. Display Cart");
        System.out.println("4. Checkout");
        System.out.println("5. Cancel Purchase");
        System.out.println("0. Back to Main Menu");
        System.out.println("\n");
        System.out.println("Enter your choice:");
    }

    private static void handlePurchaseMenu() {
        boolean back = false;
        while (!back) {
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    selectCustomer();
                    break;
                case 2:
                    addProductToCart();
                    break;
                case 3:
                    displayCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    cancelPurchase();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again:");
                    break;
            }

            if (!back) {
                displayPurchaseMenu();
            }
        }
    }

    private static void displayAnalyticsMenu() {
        System.out.println("\nAnalytics");
        System.out.println("1. Show Product Analytics");
        System.out.println("2. Show Sales Analytics");
        System.out.println("3. Show Customer Analytics");
        System.out.println("4. Show Sales per Category");
        System.out.println("5. Show Frequent Customers");
        System.out.println("6. Total Revenue");
        System.out.println("7. Show Bills by Date (Enter * to Show all bills)");
        System.out.println("0. Back to Main Menu");
        System.out.println("\n");
        System.out.println("Enter your choice:");
    }

    private static void handleAnalyticsMenu() {
        boolean back = false;
        while (!back) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    showProductAnalytics();
                    break;
                case 2:
                    showSalesAnalytics();
                    break;
                case 3:
                    showCustomerAnalytics();
                    break;
                case 4:
                    showSalesByCategory();
                    break;
                case 5:
                    showFrequentCustomers();
                    break;
                case 6:
                    showTotalRevenue();
                    break;
                case 7:
                    showBillsByDate();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again:");
                    break;
            }

            if (!back) {
                displayAnalyticsMenu();
            }
        }
    }


    private static void addCustomer() {
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer phone:");
        String phone = scanner.nextLine();

        Customer newCustomer = new Customer(0, name, phone);
        customerDAO.addCustomer(newCustomer);
    }

    private static void listAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("List of Customers:");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private static void addProduct() {
        System.out.println("Enter product name:");
        String productName = scanner.nextLine();
        System.out.println("Enter product category:");
        String category = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = scanner.nextDouble();
        System.out.println("Enter tax rate:");
        double taxRate = scanner.nextDouble();
        System.out.println("Enter stock quantity:");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine(); 

        Product newProduct = new Product(0, productName, category, price, taxRate, stockQuantity); // ProductID will be auto-generated
        productDAO.addProduct(newProduct);
        System.out.println("Product added successfully!");
    }

    private static void listAllProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("List of Products:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private static void selectCustomer() {
        if(currentCustomerID!=0){
            System.out.println("Changing Cusotomer will clear the cart.");
            System.out.println("Do you wish to continue?(Yes/No) :");
            String option = scanner.nextLine();
            if(option.equalsIgnoreCase("yes"))
                return;
        }
        System.out.println("Enter customer Phone no.:");
        String customerPhone = scanner.next();
        scanner.nextLine(); 

        Customer customer = customerDAO.getCustomerByPhoneNo(customerPhone);
        if (customer != null) {
            currentCustomerID = customer.getCustomerID();
            System.out.println("Customer selected: " + customer);
            cart.clear();
        } else {
            System.out.println("Customer not found.");
            System.out.println("Do you wish to Register new customer? (Yes/No)");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("yes"))
                addCustomer();
        }
        
    }

    private static void addProductToCart() {
        if (currentCustomerID == 0) {
            System.out.println("No customer selected. Please select a customer first.");
            return;
        }
        listAllProducts();
        System.out.println();
        System.out.println("Enter product ID to add to cart:");
        int productID = scanner.nextInt();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        Product product = productDAO.getProductByID(productID);
        if (product != null) {
            if(cart.addProduct(product, quantity)){
            System.out.println("Product added to cart successfully!");
            }   
            displayCart();
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void displayCart() {
        System.out.println("Current Cart:");
        System.out.println(cart);
    }

    private static void checkout() {
        if (currentCustomerID == 0) {
            System.out.println("No customer selected. Please select a customer first.");
            return;
        }
        if (cart.getItems().size() == 0) {
            System.out.println("No Items added to the cart. Please Add items first");
            return;
        }
    
        double totalAmount = 0.0;
        double tax = 0.0;
        for (CartItem item : cart.getItems()) {
            double itemTotal = item.getQuantity() * item.getProduct().getPrice();
            totalAmount += itemTotal;
            tax += itemTotal * (item.getProduct().getTaxRate() / 100);
        }
    
        double discount = 0.0;
        int couponID = 0;
        
        cart.displayCartDetails();

        displayCustomerCoupons(currentCustomerID);
        System.out.println("Do you have a coupon? (yes/no)");
        String hasCoupon = scanner.nextLine();
        if (hasCoupon.equalsIgnoreCase("yes")) {
            System.out.println("Enter coupon code:");
            String couponCode = scanner.nextLine();
    
            
            Coupon coupon = couponDAO.getCouponByCode(couponCode, currentCustomerID);
            if (coupon != null) {
                double discountPercentage = coupon.getDiscountPercentage();
                discount = (discountPercentage / 100) * totalAmount;
    
                couponDAO.invalidateCoupon(coupon.getCouponID());
    
                System.out.println("Coupon applied successfully. Discount applied: " + discount);
            } else {
                System.out.println("Invalid coupon code or coupon expired. No discount applied.");
            }
        }

        double netAmount = totalAmount - discount + tax;
    
        System.out.println("Select payment method:");
        System.out.println("1. UPI");
        System.out.println("2. Cash");
        System.out.println("3. Card");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); 
    
        String paymentMethod;
        switch (paymentChoice) {
            case 1:
                paymentMethod = "UPI";
                break;
            case 2:
                paymentMethod = "Cash";
                break;
            case 3:
                paymentMethod = "Card";
                break;
            default:
                System.out.println("Invalid payment method. Defaulting to UPI.");
                paymentMethod = "UPI";
                break;
        }
    
        int billID = billDAO.addBill(currentCustomerID, totalAmount, discount, tax, netAmount, couponID);
    
        for (CartItem item : cart.getItems()) {
            billDAO.addBillDetail(billID, item);
            productDAO.updateProductQuantity(item.getProduct().getProductID(),item.getQuantity());
        }
    
        paymentDAO.addPayment(billID, netAmount, paymentMethod);
    
        if (netAmount > 1000) {
            String newCouponCode = "FLAT10";
            Coupon newCoupon = new Coupon(0, newCouponCode, 10.0,getNextMonthDate(), currentCustomerID, true);
            couponDAO.addCoupon(newCoupon);
            System.out.println("Congratulations! You've received a new 10% discount coupon for your next purchase. Coupon Code: " + newCouponCode);
        } else if (netAmount > 500) {
            String newCouponCode = "FLAT5";
            Coupon newCoupon = new Coupon(0, newCouponCode, 5.0,getNextMonthDate(), currentCustomerID, true);
            couponDAO.addCoupon(newCoupon);
            System.out.println("Congratulations! You've received a new 5% discount coupon for your next purchase. Coupon Code: " + newCouponCode);
        }
    
        cart.clear();
    
        System.out.println("Checkout complete. Bill ID: " + billID + "\n");

        displayBill(billID);
    }   
    

    private static Date getNextMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    private static void cancelPurchase() {
        cart.clear();
        currentCustomerID = 0;
        System.out.println("Purchase canceled. Cart has been cleared.");
    }
    
    private static void displayBill(int billID) {
        Bill bill = billDAO.getBillByID(billID);
        List<BillDetail> billDetails = billDAO.getBillDetailsByBillID(billID);
        
        System.out.println("\n");
        System.out.println("Bill ID: " + bill.getBillID() +
                ", Customer ID: " + bill.getCustomerID() +
                ", Bill Date: " + bill.getBillDate() +
                ", Total Amount: " + bill.getTotalAmount() +
                ", Discount: " + bill.getDiscount() +
                ", Tax: " + bill.getTax() +
                ", Net Amount: " + bill.getNetAmount() +
                (bill.getCouponID() != 0 ? ", Coupon ID: " + bill.getCouponID() : ""));
    
        System.out.println("\n");
        System.out.println("Product Details:");
        System.out.println("Product ID | Product Name | Quantity | Unit Price | Total Price");
    
        for (BillDetail detail : billDetails) {
            Product product = productDAO.getProductByID(detail.getProductID());
            String productName = product != null ? product.getProductName() : "Unknown";
            System.out.println(detail.getProductID() + " | " + productName + " | " + detail.getQuantity() + " | " + detail.getUnitPrice() + " | " + detail.getTotalPrice());
        }
    }

    private static void modifyProduct() {
        System.out.println("Enter the Product ID of the product you want to modify:");
        int productID = scanner.nextInt();
        scanner.nextLine();
    
        Product product = productDAO.getProductByID(productID);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
    
        System.out.println("Enter new product name (or press Enter to keep unchanged):");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            product.setProductName(newName);
        }
    
        System.out.println("Enter new category (or press Enter to keep unchanged):");
        String newCategory = scanner.nextLine();
        if (!newCategory.isEmpty()) {
            product.setCategory(newCategory);
        }
    
        System.out.println("Enter new price (or press Enter to keep unchanged):");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) {
            try {
                product.setPrice(Double.parseDouble(newPrice));
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Keeping the old price.");
            }
        }
    
        System.out.println("Enter new tax rate (or press Enter to keep unchanged):");
        String newTaxRate = scanner.nextLine();
        if (!newTaxRate.isEmpty()) {
            try {
                product.setTaxRate(Double.parseDouble(newTaxRate));
            } catch (NumberFormatException e) {
                System.out.println("Invalid tax rate format. Keeping the old tax rate.");
            }
        }
    
        System.out.println("Enter new stock quantity to add (or press Enter to keep unchanged):");
        String newStockQuantity = scanner.nextLine();
        if (!newStockQuantity.isEmpty()) {
            try {
                int additionalStock = Integer.parseInt(newStockQuantity);
                int currentStock = productDAO.getCurrentStockQuantity(productID);
                product.setStockQuantity(currentStock + additionalStock);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock quantity format. Keeping the old stock quantity.");
            }
        }
    
        boolean isUpdated = productDAO.updateProduct(product);
        if (isUpdated) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Failed to update product.");
        }
    }
    
    
    public static  void showProductAnalytics() {
        productDAO.showProductAnalytics();
    }
    
    public static void showCustomerAnalytics(){
        customerDAO.showCustomerAnalytics();
    }

    public static void showSalesAnalytics(){
        billDAO.showSalesAnalytics();
    }

    private static void showSalesByCategory() {
        System.out.println("Sales by Category:");
        Map<String, Integer> salesByCategory = productDAO.getSalesByCategory();
        for (Map.Entry<String, Integer> entry : salesByCategory.entrySet()) {
            System.out.printf("Category: %s, Quantity Sold: %d\n", entry.getKey(), entry.getValue());
        }
    }

    private static void showFrequentCustomers(){
        System.out.println("Frequent Customers: ");
        List<Map<String,String>> frequentCustomers = customerDAO.getFrequentCustomers();

        for(Map<String,String> customer:frequentCustomers){
            int customerID = Integer.parseInt(customer.get("CustomerID"));
            String customerName = customer.get("Name");
            int purchaseCount = Integer.parseInt(customer.get("PurchaseCount"));

            System.out.printf("Customer ID: %d, Name: %s, Purchase Count: %d%n", customerID, customerName, purchaseCount);

        }
    }

    private static void showTotalRevenue(){
        double totalRevenue = billDAO.getTotalRevenue();
        
        System.out.println("Total Revenue: "+totalRevenue);
    }

    private static void displayCustomerCoupons(int currentCustomerID){
        List<Coupon> coupons = couponDAO.getCouponsByCustomerID(currentCustomerID);

        System.out.println("\nAvailable Coupons:\n");

        if(coupons.size()==0)
            System.out.println("You don't have any Coupons");
        for(Coupon coupon:coupons){
            System.out.println("Coupon code: " + coupon.getCouponCode() + " Discount Percentage: "+coupon.getDiscountPercentage());
        }
        System.out.println();
    }

    private static void showAllBills() {
        List<Bill> bills = billDAO.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
        } else {
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    }

    private static void showBillsByDate() {
        System.out.println("Enter the date (YYYY-MM-DD) (* to Show all bills):");
        String date = scanner.nextLine();
        if(date.equals("*")){
            showAllBills();
            return;
        }
        List<Bill> bills = billDAO.getBillsByDate(date);
        if (bills.isEmpty()) {
            System.out.println("No bills found for the given date.");
        } else {
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    }

}