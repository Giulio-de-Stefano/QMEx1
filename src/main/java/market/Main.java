package market;

import market.cart.Cart;
import market.cart.CartPriceCalculator;
import market.discounts.DiscountAB;
import market.discounts.NForMoneyDiscount;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer();
        Product beans = new Product("Beans", 0.50, true);
        Product chocolate = new Product("Chocolate", 1.75, true);

        Cart cart = new Cart();
        customer.setCart(cart);

        cart.addProduct(beans, 4);
        cart.addProduct(chocolate, 4);

        DiscountAB beansDiscount = new NForMoneyDiscount(beans, 3, 1.00);
        CartPriceCalculator calculator = new CartPriceCalculator(customer.getCart());

        out.println("Full prices: " + calculator.getFullPricesMap());
        out.println("Discounted prices: " + calculator.getDiscountedPricesMap());
        out.println("Savings: " + calculator.getSavingsMap());

        out.println("--- Applying beans discount 3 for £1.00 ---");
        calculator.addDiscount(beansDiscount);
        out.println("Full prices: " + calculator.getFullPricesMap());
        out.println("Discounted prices: " + calculator.getDiscountedPricesMap());
        out.println("Savings: " + calculator.getSavingsMap());

    }
}
