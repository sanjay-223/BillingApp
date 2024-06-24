# Billing System Application

## To run the program

1. Run the `Data.sql` file in MySQL Workbench to set up the database.
2. Put your MySQL Username and Password in the `db.properties` file.
3. Open Terminal in the project root folder.

### Compile
    javac *.java

### Run
    java MainApp


# MENU STRUCTURE

Welcome to Billing System

1. Customer Management
    1. Add Customer         -- Adding new customer, requires Name and Phone no.
    2. List All Customers   -- List existing customers
    0. Back to Main Menu

2. Product Management
    1. Add Product          -- Adding a new product
    2. List All Products    -- List existing products
    3. Modify Product       -- To change the details, add quantity of the product
    0. Back to Main Menu

3. Purchase
    1. Select Customer      -- Select the customer by Phone No. who is shopping. If the customer does not exist, add him.
    2. Add Product to Cart  -- Add products to cart by product id
    3. Display Cart         -- Show current cart
    4. Checkout             -- Move to billing
    5. Cancel Purchase      -- Cancel purchase and clear cart
    0. Back to Main Menu

4. Analytics
    1. Show Product Analytics
    2. Show Sales Analytics
    3. Show Customer Analytics
    4. Show Sales per Category
    5. Show Frequent Customers
    6. Total Revenue
    7. Show Bills by Date (Enter * to Show all bills)
    0. Back to Main Menu

0. Exit
