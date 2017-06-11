package market.discounts;

import market.Product;

public abstract class DiscountAB {
    private Product product;
    private DiscountType type;

    public DiscountAB(Product product, DiscountType type) {
        this.product = product;
        this.type = type;
    }

    public DiscountType getType() {
        return type;
    }

    public Product getProduct() {
        return product;
    }
}
