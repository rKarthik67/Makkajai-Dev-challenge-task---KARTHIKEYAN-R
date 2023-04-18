import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalesTaxCalculator {
    private final List<Item> items;

    public SalesTaxCalculator() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void printReceipt() {
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Item item : items) {
            BigDecimal itemSalesTax = item.getSalesTax();
            BigDecimal itemCost = item.getPrice().add(itemSalesTax);

            System.out.println(item.getQuantity() + " " + item.getName() + ": " + itemCost);
            totalSalesTax = totalSalesTax.add(itemSalesTax);
            totalCost = totalCost.add(itemCost);
        }

        System.out.println("Sales Taxes: " + totalSalesTax);
        System.out.println("Total: " + totalCost);
    }

    public static void main(String[] args) {
        SalesTaxCalculator calculator = new SalesTaxCalculator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter item details (name, price, quantity):");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                break;
            }

            String[] parts = input.split(" ");
            String name = "";
            BigDecimal price = BigDecimal.ZERO;
            int quantity = 0;

            try {
                quantity = Integer.parseInt(parts[0]);
                price = new BigDecimal(parts[parts.length - 1]);
                name = input.substring(quantity + 1, input.length() - parts[parts.length - 1].length() - 1).trim();
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            Item item = new Item(name, price, quantity);
            calculator.addItem(item);
        }

        calculator.printReceipt();
    }

    private static class Item {
        private final String name;
        private final BigDecimal price;
        private final int quantity;

        public Item(String name, BigDecimal price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getSalesTax() {
            BigDecimal taxRate = BigDecimal.ZERO;
            if (!isExempt()) {
                taxRate = taxRate.add(new BigDecimal("0.1"));
            }

            if (isImported()) {
                taxRate = taxRate.add(new BigDecimal("0.05"));
            }

            BigDecimal taxAmount = price.multiply(taxRate);
            return roundTaxAmount(taxAmount);
        }

        private boolean isExempt() {
            return name.contains("book") || name.contains("chocolate") || name.contains("pill");
        }

        private boolean isImported() {
            return name.contains("imported");
        }

        private BigDecimal roundTaxAmount(BigDecimal taxAmount) {
            BigDecimal roundedTaxAmount = taxAmount.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
            return roundedTaxAmount.setScale(2, RoundingMode.HALF_UP);
        }
    }
}

