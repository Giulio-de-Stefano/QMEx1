package market.calculators;

import market.Product;
import market.discounts.DiscountAB;
import market.discounts.KgAtMoneyDiscount;
import market.discounts.MoreForLessDiscount;
import market.discounts.NForMoneyDiscount;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PurchaseTest {

    public static Product beans = new Product("beans", 0.50, true);
    public static Product chocolate = new Product("chocolate", 1.89, true);
    public static Product potatoes = new Product("potatoes", 0.80, false);

    @DataProvider(name = "moreForLessQuantitiesAndExpectations")
    public static Object[][] moreForLessQuantitiesAndExpectations() {
        return new Object[][]{
                {beans, 4, 3, 2, 1.50, 0.50},
                {beans, 3, 3, 2, 1.00, 0.50},
                {chocolate, 3, 3, 2, chocolate.getPrice() * 2, chocolate.getPrice()},
                {chocolate, 2, 3, 2, chocolate.getPrice() * 2, 0.00},
                {chocolate, 10, 4, 2, chocolate.getPrice() * 6, chocolate.getPrice() * 4},
                {chocolate, 561, 17, 13, chocolate.getPrice() * 429, chocolate.getPrice() * (561 - 429)}
        };
    }

    @DataProvider(name = "nForMoneyQuantitiesAndExpectations")
    public static Object[][] nForMoneyQuantitiesAndExpectations() {
        return new Object[][]{
                {beans, 5, 2, 0.75, 2.00, 0.50},
                {chocolate, 4, 2, 0.90, 1.80, chocolate.getPrice() * 4 - 1.80},
                {chocolate, 7, 3, 4, 8 + chocolate.getPrice(), 3.34},
        };
    }

    @DataProvider(name = "KgAtMoneyQuantitiesAndExpectations")
    public static Object[][] KgAtMoneyQuantitiesAndExpectations() {
        return new Object[][]{
                {potatoes, 1000, 0.60, 0.60, 0.20},
                {potatoes, 500, 0.55, 0.28, 0.12},
                {potatoes, 150, 0.78, 0.12, 0},
        };
    }

    @Test(dataProvider = "moreForLessQuantitiesAndExpectations")
    public void moreForLess(Product product, int purchaseQuantity, int discBuyQuantity, int discPayQuantity,
                            double expDiscountedTotalPrice, double expSavings) {

        Purchase purchase = new Purchase(product, purchaseQuantity);
        DiscountAB discount = new MoreForLessDiscount(product, discBuyQuantity, discPayQuantity);
        purchase.applyDiscount(discount);

        assertEquals(purchase.getTotalDiscountedPrice(), expDiscountedTotalPrice);
        assertEquals(purchase.getSavedAmount(), expSavings);
    }

    @Test(dataProvider = "nForMoneyQuantitiesAndExpectations")
    public void nForMoney(Product product, int purchaseQuantity, int discBuyQuantity, double discountedPrice,
                          double expDiscountedTotalPrice, double expSavings) {

        Purchase purchase = new Purchase(product, purchaseQuantity);
        DiscountAB discount = new NForMoneyDiscount(product, discBuyQuantity, discountedPrice);
        purchase.applyDiscount(discount);

        assertEquals(purchase.getTotalDiscountedPrice(), expDiscountedTotalPrice);
        assertEquals(purchase.getSavedAmount(), expSavings);
    }

    @Test(dataProvider = "KgAtMoneyQuantitiesAndExpectations")
    public void kgAtMoney(Product product, int purchaseQuantity, double discountedPrice,
                          double expDiscountedTotalPrice, double expSavings) {

        Purchase purchase = new Purchase(product, purchaseQuantity);
        DiscountAB discount = new KgAtMoneyDiscount(product, discountedPrice);
        purchase.applyDiscount(discount);

        assertEquals(purchase.getTotalDiscountedPrice(), expDiscountedTotalPrice);
        assertEquals(purchase.getSavedAmount(), expSavings);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void unrelatedDiscountDoesNotApply() {
        Purchase purchase = new Purchase(beans, 5);
        DiscountAB discount = new MoreForLessDiscount(chocolate, 3, 2);
        purchase.applyDiscount(discount);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void doubleDiscountDoesNotApply() {
        Purchase purchase = new Purchase(beans, 5);
        DiscountAB discount = new MoreForLessDiscount(beans, 3, 2);
        DiscountAB discount2 = new NForMoneyDiscount(beans, 10, 1);
        purchase.applyDiscount(discount);
        purchase.applyDiscount(discount2);
    }
}
