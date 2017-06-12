package market.discounts;

import market.Product;

public class NForMoneyDiscount extends DiscountAB {
    private int quantity;
    private double discountedPrice;

    public NForMoneyDiscount(Product product, int quantity, double discountedPrice) {
        super(product, DiscountType.N_FOR_MONEY);
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;

        if (quantity <= 0)
            throw new IllegalArgumentException("The quantity [" + quantity
                    + "] must be positive for product [" + product.getName() + "]");

        if (discountedPrice/quantity >= product.getPrice())
            throw new IllegalArgumentException("The discounted price [" + discountedPrice/quantity
                    + "] should be lower than the product [" + product.getName() + "] full price [" + product.getPrice() + "]");
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }
}
