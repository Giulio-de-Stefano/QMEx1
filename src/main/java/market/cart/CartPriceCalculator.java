package market.cart;

import market.Product;
import market.Purchase;
import market.discounts.DiscountAB;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CartPriceCalculator {

    private final Cart cart;
    private final Set<Purchase> purchases;
    private final Map<String, Double> productName2fullPrice;
    private final Map<String, Double> productName2discountedPrice;
    private final Map<String, Double> productName2savings;
    private final Set<DiscountAB> discounts;

    public CartPriceCalculator(Cart cart) {
        this.cart = cart;
        this.purchases = new HashSet<>();
        this.productName2fullPrice = new HashMap<>();
        this.productName2discountedPrice = new HashMap<>();
        this.productName2savings = new HashMap<>();
        this.discounts = new HashSet<>();
        this.computePurchases();
        this.computeFullPrices();
        this.computeDiscounts();
    }

    private void computePurchases() {
        purchases.clear();
        for (Product product : cart.getProducts().keySet())
            purchases.add(new Purchase(product, cart.getProducts().get(product)));
    }

    private void computeFullPrices() {
        for (Purchase purchase : this.getPurchases())
            productName2fullPrice.put(purchase.getProduct().getName(), purchase.getTotalFullPrice());
    }

    private void computeDiscounts() {
        productName2discountedPrice.clear();
        productName2savings.clear();

        for (DiscountAB discount : discounts)
            for (Purchase purchase : this.getPurchases())
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
        this.computePurchases();
        this.computeDiscounts();
    }

    public void removeDiscount(DiscountAB remDiscount) {
        discounts.remove(remDiscount);
        this.computePurchases();
        this.computeDiscounts();
    }

    public Map<String, Double> getFullPricesMap() {
        return new HashMap<>(productName2fullPrice);
    }

    public Map<String, Double> getDiscountedPricesMap() {
        return new HashMap<>(productName2discountedPrice);
    }

    public Map<String, Double> getSavingsMap() {
        return new HashMap<>(productName2savings);
    }

    public Set<Purchase> getPurchases() {
        return new HashSet<>(purchases);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartPriceCalculator that = (CartPriceCalculator) o;

        if (!cart.equals(that.cart)) return false;
        if (!purchases.equals(that.purchases)) return false;
        if (!productName2fullPrice.equals(that.productName2fullPrice)) return false;
        if (!productName2discountedPrice.equals(that.productName2discountedPrice)) return false;
        return productName2savings.equals(that.productName2savings) && discounts.equals(that.discounts);
    }

    @Override
    public int hashCode() {
        int result = cart.hashCode();
        result = 31 * result + purchases.hashCode();
        result = 31 * result + productName2fullPrice.hashCode();
        result = 31 * result + productName2discountedPrice.hashCode();
        result = 31 * result + productName2savings.hashCode();
        result = 31 * result + discounts.hashCode();
        return result;
    }
}
