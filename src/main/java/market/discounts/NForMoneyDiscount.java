package market.discounts;

import market.Product;

public class NForMoneyDiscount extends DiscountAB {
    private int quantity;
    private double discountedPrice;

    public NForMoneyDiscount(Product product, int quantity, double discountedPrice) {
        super(product, DiscountType.N_FOR_MONEY);
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }
}
