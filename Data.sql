DROP DATABASE IF EXISTS BillingDB;

CREATE DATABASE BillingDB;

USE BillingDB;

CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    Category VARCHAR(100),
    Price DECIMAL(10, 2) NOT NULL,
    TaxRate DECIMAL(5, 2) NOT NULL,
    StockQuantity INT NOT NULL DEFAULT 0 
);

CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Phone VARCHAR(20) UNIQUE
);

CREATE TABLE Coupons (
    CouponID INT AUTO_INCREMENT PRIMARY KEY,
    CouponCode VARCHAR(50) NOT NULL,
    DiscountPercentage DECIMAL(5,2) NOT NULL,
    ValidTo DATE NOT NULL,
    CustomerID INT,
    IsValid BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_customer
        FOREIGN KEY (CustomerID) 
        REFERENCES Customers(CustomerID)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE Bills (
    BillID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    BillDate DATE NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Discount DECIMAL(10, 2),
    Tax DECIMAL(10, 2),
    NetAmount DECIMAL(10, 2) NOT NULL,
    CouponID INT DEFAULT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON UPDATE CASCADE,
    FOREIGN KEY (CouponID) REFERENCES Coupons(CouponID) ON UPDATE CASCADE
);

CREATE TABLE BillDetails (
    BillDetailID INT AUTO_INCREMENT PRIMARY KEY,
    BillID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    UnitPrice DECIMAL(10, 2) NOT NULL,
    TotalPrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (BillID) REFERENCES Bills(BillID) ON UPDATE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON UPDATE CASCADE
);

CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BillID INT NOT NULL,
    AmountPaid DECIMAL(10, 2) NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL,
    PaymentDate DATE NOT NULL,
    FOREIGN KEY (BillID) REFERENCES Bills(BillID) ON UPDATE CASCADE
);

INSERT INTO Products (ProductName, Category, Price, TaxRate, StockQuantity)
VALUES
    ('Basmati Rice', 'Grocery', 120.00, 5.00, 100),
    ('Mangoes', 'Fruits', 60.00, 5.00, 200),
    ('Paneer', 'Dairy', 180.00, 12.00, 50),
    ('Masoor Dal', 'Pulses', 100.00, 0.00, 150),
    ('Tata Salt', 'Grocery', 20.00, 18.00, 300),
    ('Cashew', 'Nuts', 50.00, 10.00, 100);

INSERT INTO Customers (Name, Phone)
VALUES
    ('Ramesh Kumar', '9876543210'),
    ('Sunita Sharma', '8765432109'),
    ('Amit Gupta', '7654321098'),
    ('Priya Patel', '6543210987'),
    ('Rakesh', '9566991250');

INSERT INTO Coupons (CouponCode, DiscountPercentage, ValidTo, CustomerID, IsValid)
VALUES
    ('SUMMER20', 20.00, '2024-09-30', NULL, TRUE),
    ('FESTIVE10', 10.00, '2024-12-31', 1, TRUE),
    ('NEWUSER15', 15.00, '2024-10-31', 3, TRUE),
    ('FLAT10A', 10.00, '2024-07-23', 2, TRUE),
    ('FLAT10B', 10.00, '2024-07-23', 1, FALSE),
    ('FLAT10C', 10.00, '2024-07-23', 1, TRUE),
    ('FLAT10D', 10.00, '2024-07-23', 1, TRUE),
    ('FLAT5', 5.00, '2024-07-23', 5, TRUE);

INSERT INTO Bills (CustomerID, BillDate, TotalAmount, Discount, Tax, NetAmount, CouponID)
VALUES
    (1, '2024-06-20', 600.00, 50.00, 30.00, 580.00, 2),
    (3, '2024-06-21', 300.00, 0.00, 15.00, 315.00, NULL),
    (2, '2024-06-22', 800.00, 100.00, 40.00, 740.00, 1),
    (1, '2024-06-22', 600.00, 0.00, 0.00, 600.00, NULL),
    (1, '2024-06-22', 1200.00, 0.00, 60.00, 1260.00, NULL),
    (2, '2024-06-23', 1200.00, 0.00, 60.00, 1260.00, NULL),
    (1, '2024-06-23', 1200.00, 0.00, 60.00, 1260.00, NULL),
    (1, '2024-06-23', 300.00, 30.00, 15.00, 285.00, NULL),
    (1, '2024-06-23', 1200.00, 0.00, 60.00, 1260.00, NULL),
    (1, '2024-06-23', 900.00, 0.00, 108.00, 1008.00, NULL),
    (5, '2024-06-23', 600.00, 0.00, 30.00, 630.00, NULL);

INSERT INTO BillDetails (BillID, ProductID, Quantity, UnitPrice, TotalPrice)
VALUES
    (1, 1, 2, 120.00, 240.00),
    (2, 4, 3, 100.00, 300.00),
    (3, 3, 2, 180.00, 360.00),
    (4, 5, 1, 20.00, 20.00),
    (5, 1, 5, 120.00, 600.00),
    (6, 1, 10, 120.00, 1200.00),
    (7, 1, 10, 120.00, 1200.00),
    (8, 1, 10, 120.00, 1200.00),
    (9, 2, 5, 60.00, 300.00),
    (10, 1, 10, 120.00, 1200.00),
    (11, 3, 5, 180.00, 900.00);

INSERT INTO Payments (BillID, AmountPaid, PaymentMethod, PaymentDate)
VALUES
    (1, 580.00, 'UPI', '2024-06-20'),
    (2, 315.00, 'Cash', '2024-06-21'),
    (3, 740.00, 'Card', '2024-06-22'),
    (5, 600.00, 'UPI', '2024-06-22'),
    (6, 1260.00, 'Cash', '2024-06-22'),
    (7, 1260.00, 'Card', '2024-06-23'),
    (8, 1260.00, 'UPI', '2024-06-23'),
    (9, 285.00, 'Card', '2024-06-23'),
    (10, 1260.00, 'Cash', '2024-06-23'),
    (11, 1008.00, 'UPI', '2024-06-23');