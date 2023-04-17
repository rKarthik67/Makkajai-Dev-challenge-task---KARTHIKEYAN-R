import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SalesTaxCalculator {

    // OOP principle applied: Encapsulation
    // Private constructor to prevent direct instantiation
    private SalesTaxCalculator() {}

    // OOP principle applied: Inheritance
    // Item is the base class for all items that can be purchased
    private static abstract class Item {
        private String name;
        private BigDecimal price;
        private boolean imported;

        // Constructor for class item
        Item(String name, BigDecimal price, boolean imported) {
            this.name = name;
            this.price = price;
            this.imported = imported;
        }

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public boolean isImported() {
            return imported;
        }

        public void setImported(boolean imported) {
            this.imported = imported;
        }

        // OOP principle applied: Polymorphism
        // Method to calculate sales tax for an item, to be overridden by child classes
        public abstract BigDecimal getSalesTax();
    }

    //  <---- Inheritence from Item for objects and its overrides starts here ----> \\
    // OOP principle applied: Inheritance
    // Book is a subclass of Item and is exempt from sales tax
    private static class Book extends Item {
        Book(String name, BigDecimal price, boolean imported) {
            super(name, price, imported);
        }

        @Override
        public BigDecimal getSalesTax() {
            return BigDecimal.ZERO;
        }
    }

    // OOP principle applied: Inheritance
    // Food is a subclass of Item and is exempt from sales tax
    private static class Food extends Item {
        Food(String name, BigDecimal price, boolean imported) {
            super(name, price, imported);
        }

        @Override
        public BigDecimal getSalesTax() {
            return BigDecimal.ZERO;
        }
    }

    // OOP principle applied: Inheritance
    // MedicalProduct is a subclass of Item and is exempt from sales tax
    private static class MedicalProduct extends Item {
        MedicalProduct(String name, BigDecimal price, boolean imported) {
            super(name, price, imported);
        }

        @Override
        public BigDecimal getSalesTax() {
            return BigDecimal.ZERO;
        }
    }

    // OOP principle applied: Inheritance
    // OtherItem is a subclass of Item and is subject to sales tax
    private static class OtherItem extends Item {
        OtherItem(String name, BigDecimal price, boolean imported) {
            super(name, price, imported);
        }

        @Override
        public BigDecimal getSalesTax() {
            BigDecimal salesTax = getPrice().multiply(new BigDecimal("0.1"));
            if (isImported()) {
                salesTax = salesTax.add(getPrice().multiply(new BigDecimal("0.05")));
            }
            return roundUpToNearest005(salesTax);
        }
    }

    // OOP principle applied: Abstraction
    // Method to round up a BigDecimal to the nearest 0.05
    private static BigDecimal roundUpToNearest005(BigDecimal value) {
        return value.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
    }

    // OOP principle applied: Composition
    // Basket class represents a collection of items that can be purchased
    private static class Basket {
        private List<Item> items;

        Basket() {
            this.items = new ArrayList<>();
        }
        
        // OOP principle applied: Encapsulation
        // Method to add an item to the basket
        public void addItem(Item item) {
            items.add(item);
        }

        // OOP principle applied: Encapsulation
        // Method to calculate the total sales tax for all items in the basket
        public BigDecimal calculateSalesTax() {
            BigDecimal salesTax = BigDecimal.ZERO;
            for (Item item : items) {
                salesTax = salesTax.add(item.getSalesTax());
            }
            return salesTax;
        }

        // OOP principle applied: Encapsulation
        // Method to calculate the total cost for all items in the basket (including sales tax)
        public BigDecimal calculateTotalCost() {
            BigDecimal totalCost = BigDecimal.ZERO;
            for (Item item : items) {
                totalCost = totalCost.add(item.getPrice().add(item.getSalesTax()));
            }
            return totalCost;
        }

        // OOP principle applied: Encapsulation
        // Method to print the receipt details for all items in the basket
        public void printReceipt() {
            for (Item item : items) {
                System.out.println(item.getName() + ": " + item.getPrice().add(item.getSalesTax()));
            }
            System.out.println("Sales Taxes: " + calculateSalesTax());
            System.out.println("Total: " + calculateTotalCost());
        }
    }

    public static void main(String[] args) {
        // Create baskets and add items
        Basket basket1 = new Basket();
        basket1.addItem(new Book("book", new BigDecimal("12.49"), false));
        basket1.addItem(new OtherItem("music CD", new BigDecimal("14.99"), false));
        basket1.addItem(new Food("chocolate bar", new BigDecimal("0.85"), false));

        Basket basket2 = new Basket();
        basket2.addItem(new Food("imported box of chocolates", new BigDecimal("10.00"), true));
        basket2.addItem(new OtherItem("imported bottle of perfume", new BigDecimal("47.50"), true));

        Basket basket3 = new Basket();
        basket3.addItem(new OtherItem("imported bottle of perfume", new BigDecimal("27.99"), true));
        basket3.addItem(new OtherItem("bottle of perfume", new BigDecimal("18.99"), false));
        basket3.addItem(new MedicalProduct("packet of headache pills", new BigDecimal("9.75"), false));
        basket3.addItem(new Food("box of imported chocolates", new BigDecimal("11.25"), true));

        // Print receipts for all baskets
        System.out.println("Output 1:");
        basket1.printReceipt();
        System.out.println();

        System.out.println("Output 2:");
        basket2.printReceipt();
        System.out.println();

        System.out.println("Output 3:");
        basket3.printReceipt();
    }
}
