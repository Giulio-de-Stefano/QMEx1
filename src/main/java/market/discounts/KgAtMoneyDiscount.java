package market.discounts;

import market.Product;

public class KgAtMoneyDiscount extends NForMoneyDiscount {

    private static int KG_IN_GRAMS = 1000;

    public KgAtMoneyDiscount(Product product, double discountedPrice) {
        super(product, KG_IN_GRAMS, discountedPrice);
    }
}
