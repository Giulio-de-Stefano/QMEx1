package market.discounts;

import market.Product;

public class MoreForLessDiscount extends DiscountAB {
    private int buyQuantity;
    private int payQuantity;

    public MoreForLessDiscount(Product product, int buyQuantity, int payQuantity) {
        super(product, DiscountType.MORE_FOR_LESS);
        this.buyQuantity = buyQuantity;
        this.payQuantity = payQuantity;

        if (buyQuantity <= 0 || payQuantity <= 0)
            throw new IllegalArgumentException("Both quantities (to be paid for [" + payQuantity
                    + "] and to buy + [" + buyQuantity + ") must be positive");

        if (buyQuantity <= payQuantity)
            throw new IllegalArgumentException("The quantity to be bought [" + buyQuantity
                    + "] should be lower than one to be paid for [" + payQuantity + "] for product ["
                    + product.getName() + "]");
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getPayQuantity() {
        return payQuantity;
    }
}
