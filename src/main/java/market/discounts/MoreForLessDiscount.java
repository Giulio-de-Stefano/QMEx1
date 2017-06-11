package market.discounts;

import market.Product;

public class MoreForLessDiscount extends DiscountAB {
    private int moreQuantity;
    private int lessQuantity;

    public MoreForLessDiscount(Product product, int moreQuantity, int lessQuantity) {
        super(product, DiscountType.MORE_FOR_LESS);
        this.moreQuantity = moreQuantity;
        this.lessQuantity = lessQuantity;
    }

    public int getMoreQuantity() {
        return moreQuantity;
    }

    public int getLessQuantity() {
        return lessQuantity;
    }
}
