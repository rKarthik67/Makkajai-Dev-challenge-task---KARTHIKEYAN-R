package makkajai;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<Item> items;

    public Receipt() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public double getSalesTax() {
        double salesTax = 0;
        for (Item item : items) {
            if (!item.isExempt()) {
                double taxRate = 0.1;
                if (item.isImported()) {
                    taxRate += 0.05;
                }
                double tax = Math.ceil(taxRate * item.getPrice() * 20) / 20.0;
                salesTax += tax;
            }
        }
        return salesTax;
    }
}
