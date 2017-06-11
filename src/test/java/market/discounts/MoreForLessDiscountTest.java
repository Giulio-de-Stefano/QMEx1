package market.discounts;

import market.Product;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MoreForLessDiscountTest {

    public static Product beans = new Product("beans", 0.50, true);

    @DataProvider(name = "nonPositiveQuantities")
    public static Object[][] nonPositiveQuantities() {
        return new Object[][]{
                {-2, 3},
                {3, 0},
                {3, -1},
                {-4, -1},
                {0, 0},
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*The quantity to be bought.* should be lower than one to be paid for.*")
    public void invalidDiscountBuyLessForSameOrMore() {
        new MoreForLessDiscount(beans, 3, 4);
    }

    @Test(dataProvider = "nonPositiveQuantities",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Both quantities.* must be positive.*")
    public void invalidDiscountNonPositiveQuantities(int buyQuantity, int payQuantity) {
        new MoreForLessDiscount(beans, buyQuantity, payQuantity);
    }
}
