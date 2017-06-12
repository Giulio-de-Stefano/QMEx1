package market.cart;

import market.Product;
import market.Purchase;
import market.discounts.DiscountAB;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CartPriceCalculator {

    private Cart cart;
    private Map<String, Double> productName2discountedPrice;
    private Map<String, Double> productName2savings;
    private Set<DiscountAB> discounts;

    public CartPriceCalculator(Cart cart) {
        this.cart = cart;
        productName2discountedPrice = new HashMap<>();
        productName2savings = new HashMap<>();
        discounts = new HashSet<>();
        this.computeDiscounts();
    }

    private void computeDiscounts() {
        Set<Purchase> purhcases = new HashSet<>();
        productName2discountedPrice.clear();
        productName2savings.clear();

        for (Product product : cart.getProducts().keySet())
            purhcases.add(new Purchase(product, cart.getProducts().get(product)));

        for (DiscountAB discount : discounts)
            for (Purchase purchase : purhcases)
                if (purchase.getProduct().equals(discount.getProduct())) {
                    purchase.applyDiscount(discount);
                    String name = purchase.getProduct().getName();
                    productName2discountedPrice.put(name, purchase.getTotalDiscountedPrice());
                    productName2savings.put(name, purchase.getSavedAmount());
                    break;
                }
    }

    public void addDiscount(DiscountAB newDiscount) {
        discounts.add(newDiscount);
        this.computeDiscounts();
    }

    public void removeDiscount(DiscountAB remDiscount) {
        discounts.remove(remDiscount);
        this.computeDiscounts();
    }

    public Map<String, Double> getDiscountedPricesMap() {
        return new HashMap<>(productName2discountedPrice);
    }

    public Map<String, Double> getSavingsMap() {
        return new HashMap<>(productName2savings);
    }
}
