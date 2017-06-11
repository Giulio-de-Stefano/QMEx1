package market.discounts;

import market.Product;
import org.testng.annotations.Test;

public class NForMoneyDiscountTest {
    public static Product beans = new Product("beans", 0.50, true);

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*The discounted price.* should be lower.*")
    public void invalidDiscountDiscountedPriceMoreExpensive() {
        new NForMoneyDiscount(beans, 3, 0.75);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*The quantity.* must be positive.*")
    public void invalidDiscountNonPositiveQuantities() {
        new NForMoneyDiscount(beans, 0, 0.25);
        new NForMoneyDiscount(beans, -1, 0.25);
    }
}