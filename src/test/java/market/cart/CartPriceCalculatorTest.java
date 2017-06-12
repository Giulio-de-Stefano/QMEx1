package market.cart;

import market.Product;
import market.discounts.DiscountAB;
import market.discounts.MoreForLessDiscount;
import market.discounts.NForMoneyDiscount;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartPriceCalculatorTest {

    private static Product beans = new Product("beans", 0.50, true);
    private static Product chocolate = new Product("chocolate", 1.75, true);
    private static Product potatoes = new Product("potatoes", 0.80, false);

    private static int GRAMS_IN_KG = 1000;

    @Test
    public void emptyMaps_whenCartEmpty() {
        Cart cart = new Cart();
        CartPriceCalculator calculator = new CartPriceCalculator(cart);

        assertEquals(calculator.getSavingsMap().size(), 0);
        assertEquals(calculator.getDiscountedPricesMap().size(), 0);
        assertEquals(calculator.getFullPricesMap().size(), 0);
    }

    @Test
    public void correctFullPrices_whenAddingProducts() {
        Cart cart = new Cart();
        cart.addProduct(beans, 3);
        cart.addProduct(chocolate, 2);
        cart.addProduct(potatoes, 500);

        CartPriceCalculator calculator = new CartPriceCalculator(cart);

        double expBeansPrice = beans.getPrice() * cart.getProductQuantity(beans);
        double expChocolatePrice = chocolate.getPrice() * cart.getProductQuantity(chocolate);
        double expPotatoesPrice = (potatoes.getPrice() * cart.getProductQuantity(potatoes)) / GRAMS_IN_KG;
        Map actualPrices = calculator.getFullPricesMap();

        assertEquals(actualPrices.get("beans"), expBeansPrice);
        assertEquals(actualPrices.get("chocolate"), expChocolatePrice);
        assertEquals(actualPrices.get("potatoes"), expPotatoesPrice);
    }

    @Test
    public void correctFullPrices_whenApplyingDiscounts() {
        Cart cart = new Cart();
        cart.addProduct(beans, 3);
        cart.addProduct(chocolate, 2);
        cart.addProduct(potatoes, 500);

        CartPriceCalculator calculator = new CartPriceCalculator(cart);

        double expBeansPrice = beans.getPrice() * cart.getProductQuantity(beans);
        double expChocolatePrice = chocolate.getPrice() * cart.getProductQuantity(chocolate);
        double expPotatoesPrice = (potatoes.getPrice() * cart.getProductQuantity(potatoes)) / GRAMS_IN_KG;

        // adding a discount does not change full prices
        DiscountAB beansDiscount = new MoreForLessDiscount(beans, 3, 2);
        calculator.addDiscount(beansDiscount);
        Map actualPrices = calculator.getFullPricesMap();

        assertEquals(actualPrices.get("beans"), expBeansPrice);
        assertEquals(actualPrices.get("chocolate"), expChocolatePrice);
        assertEquals(actualPrices.get("potatoes"), expPotatoesPrice);
    }

    @Test
    public void correctSavingsAndDiscountedPrices_whenApplyingDiscounts() {
        Cart cart = new Cart();
        cart.addProduct(beans, 3);
        cart.addProduct(chocolate, 2);
        cart.addProduct(potatoes, 500);

        CartPriceCalculator calculator = new CartPriceCalculator(cart);
        DiscountAB beansDiscount = new MoreForLessDiscount(beans, 3, 2);

        double expBeansSavings = 0.50;
        double expBeansDiscountedPrice = 1.00;

        // adding a discount does not change full prices
        calculator.addDiscount(beansDiscount);
        Map actualSavings = calculator.getSavingsMap();
        Map actualDiscountedPrices = calculator.getDiscountedPricesMap();

        assertEquals(actualSavings.get("beans"), expBeansSavings);
        assertEquals(actualSavings.get("chocolate"), null);
        assertEquals(actualSavings.get("potatoes"), null);

        assertEquals(actualDiscountedPrices.get("beans"), expBeansDiscountedPrice);
        assertEquals(actualDiscountedPrices.get("chocolate"), null);
        assertEquals(actualDiscountedPrices.get("potatoes"), null);
    }

    @Test
    public void stateResets_whenAddingAndRemovingDiscount() {
        Cart cart = new Cart();
        cart.addProduct(beans, 3);
        cart.addProduct(chocolate, 2);
        cart.addProduct(potatoes, 500);

        DiscountAB beansDiscount = new MoreForLessDiscount(beans, 3, 2);

        CartPriceCalculator calculator = new CartPriceCalculator(cart);
        CartPriceCalculator calculatorClone = new CartPriceCalculator(cart);

        calculator.addDiscount(beansDiscount);
        calculator.removeDiscount(beansDiscount);

        assertTrue(calculator.equals(calculatorClone));
    }

    @Test
    public void fullPriceEqualsDiscountPlusSavings() {
        Cart cart = new Cart();
        cart.addProduct(beans, 3);
        cart.addProduct(chocolate, 4);

        CartPriceCalculator calculator = new CartPriceCalculator(cart);
        DiscountAB beansDiscount = new MoreForLessDiscount(beans, 3, 2);
        DiscountAB chocolateDiscount = new NForMoneyDiscount(chocolate, 3, 2.00);

        calculator.addDiscount(beansDiscount);
        calculator.addDiscount(chocolateDiscount);

        Map<String, Double> fullPricesMap = calculator.getFullPricesMap();
        Map<String, Double> discountedPricesMap = calculator.getDiscountedPricesMap();
        Map<String, Double> savingsMap = calculator.getSavingsMap();

        double totalDiscountedPrice = discountedPricesMap.values().stream().mapToDouble(Number::doubleValue).sum();
        double totalSavings = savingsMap.values().stream().mapToDouble(Number::doubleValue).sum();
        double fullPrice = fullPricesMap.values().stream().mapToDouble(Number::doubleValue).sum();

        assertEquals(totalDiscountedPrice + totalSavings, fullPrice);
    }
}
