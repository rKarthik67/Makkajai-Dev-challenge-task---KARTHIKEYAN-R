package makkajai;

import java.util.Scanner;

public class SalesTaxCalculator {
    public static void main(String[] args) {
        Receipt receipt = new Receipt();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter item (enter 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equals("done")) {
                break;
            }

            String[] parts = input.split(" at ");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);

            boolean isExempt = false;
            if (name.contains("book") || name.contains("food") || name.contains("medical")) {
                isExempt = true;
            }

            boolean isImported = false;
            if (name.contains("imported")) {
                isImported = true;
            }

            Item item = new Item(name, price, isExempt, isImported);
            receipt.addItem(item);
        }
        scanner.close();

        System.out.println("Receipt:");
        for (Item item : receipt.getItems()) {
            System.out.printf("%s: %.2f\n", item.getName(), item.getPrice());
        }

        double salesTax = receipt.getSalesTax();
        System.out.printf("Sales Taxes: %.2f\n", salesTax);

        double total = receipt.getTotal() + salesTax;
        System.out.printf("Total: %.2f\n", total);
    }
}
