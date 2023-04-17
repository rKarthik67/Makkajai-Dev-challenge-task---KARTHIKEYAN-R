package com.kindsonthegenius.friendsapi;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReceiptPrinter {

    // Constants for tax rates
    private static final BigDecimal BASIC_SALES_TAX_RATE = new BigDecimal("0.10");
    private static final BigDecimal IMPORT_DUTY_TAX_RATE = new BigDecimal("0.05");

    public static void main(String[] args) {
        // Input 1
        System.out.println("Output 1:");
        printReceipt(new Item[] {
            new Item("book", new BigDecimal("12.49")),
            new Item("music CD", new BigDecimal("14.99")),
            new Item("chocolate bar", new BigDecimal("0.85"))
        });

        // Input 2
        System.out.println("\nOutput 2:");
        printReceipt(new Item[] {
            new Item("imported box of chocolates", new BigDecimal("10.00")),
            new Item("imported bottle of perfume", new BigDecimal("47.50"))
        });

        // Input 3
        System.out.println("\nOutput 3:");
        printReceipt(new Item[] {
            new Item("imported bottle of perfume", new BigDecimal("27.99")),
            new Item("bottle of perfume", new BigDecimal("18.99")),
            new Item("packet of headache pills", new BigDecimal("9.75")),
            new Item("box of imported chocolates", new BigDecimal("11.25"))
        });
    }

    // Method to print the receipt for a given list of items
    private static void printReceipt(Item[] items) {
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        
        for (Item item : items) {
            BigDecimal itemPrice = item.getPrice();
            BigDecimal itemSalesTax = calculateSalesTax(item);
            
            totalSalesTax = totalSalesTax.add(itemSalesTax);
            totalPrice = totalPrice.add(itemPrice.add(itemSalesTax));
            
            System.out.println(item.getQuantity() + " " + item.getName() + ": " + formatPrice(itemPrice.add(itemSalesTax)));
        }
        
        System.out.println("Sales Taxes: " + formatPrice(totalSalesTax));
        System.out.println("Total: " + formatPrice(totalPrice));
    }

    // Method to calculate the sales tax for a given item
    private static BigDecimal calculateSalesTax(Item item) {
        BigDecimal salesTax = BigDecimal.ZERO;
        BigDecimal itemPrice = item.getPrice();
        
        if (!item.isExemptFromBasicSalesTax()) {
            salesTax = salesTax.add(itemPrice.multiply(BASIC_SALES_TAX_RATE));
        }
        
        if (item.isImported()) {
            salesTax = salesTax.add(itemPrice.multiply(IMPORT_DUTY_TAX_RATE));
        }
        
        return roundSalesTax(salesTax);
    }

    // Method to round the sales tax to the nearest 0.05
    private static BigDecimal roundSalesTax(BigDecimal salesTax) {
        return salesTax.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
    }

    // Method to format a price with 2 decimal places
    private static String formatPrice(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_UP).toString();
    }

    // Class to represent an item
    private static class Item {
        private String name;
        private BigDecimal price;
        private int quantity = 1;

        public Item(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        public boolean isExemptFromBasicSalesTax() {
            return name.contains("book") || name.contains("food") || name.contains("medical");
        }

        public boolean isImported() {
            return name.contains("imported");
        }
    }
}

