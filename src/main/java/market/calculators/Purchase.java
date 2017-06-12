package market.calculators;

import market.Product;
import market.discounts.DiscountAB;
import market.discounts.MoreForLessDiscount;
import market.discounts.NForMoneyDiscount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Purchase {
    private final Product product;
    private final int quantity;
    private final double totalFullPrice;
    private double totalDiscountedPrice;
    private double savedAmount = 0;

    public Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        if (product.isCountable())
            this.totalFullPrice = quantity * product.getPrice();
        else
            this.totalFullPrice = (double) quantity / 1000 * product.getPrice();
    }

    // TODO refactor into smaller methods
    // TODO check that no 2 discounts are applied on the same product (do it in CartPriceCalculator)
    public void applyDiscount(DiscountAB discount) {
        if (!discount.getProduct().getName().equals(this.product.getName()))
            return;

        final int GRAMS_IN_KG = 1000;
        double eligibleQuantity;
        double eligibleQuantityPrice;
        double discountRatio;

        switch (discount.getType()) {
            case N_FOR_MONEY:
                int discountQuantity = ((NForMoneyDiscount) discount).getQuantity();
                double discountPrice = ((NForMoneyDiscount) discount).getDiscountedPrice();

                if (product.isCountable())
                    discountRatio = discountPrice / discountQuantity;
                else
                    discountRatio = discountPrice / ((double) discountQuantity / GRAMS_IN_KG);

                if (product.isCountable())
                    eligibleQuantity = Math.floorDiv(quantity, discountQuantity) * discountQuantity;
                else
                    eligibleQuantity = (double) quantity / (double) GRAMS_IN_KG; // the whole weight qualifies for a @KG discount

                eligibleQuantityPrice = eligibleQuantity * discountRatio;
                break;

            case MORE_FOR_LESS:
                int buyQuantity = ((MoreForLessDiscount) discount).getBuyQuantity();
                int payQuantity = ((MoreForLessDiscount) discount).getPayQuantity();
                discountRatio = (double) payQuantity / (double) buyQuantity;
                eligibleQuantity = Math.floorDiv(quantity, buyQuantity) * buyQuantity;
                eligibleQuantityPrice = eligibleQuantity * product.getPrice() * discountRatio;
                break;

            default:
                throw new IllegalArgumentException("Unknown discount type [" + discount.getType() + "]");
        }

        if (product.isCountable())
            totalDiscountedPrice = eligibleQuantityPrice + (quantity - eligibleQuantity) * this.product.getPrice();
        else
            totalDiscountedPrice = eligibleQuantityPrice;

        totalDiscountedPrice = (new BigDecimal(totalDiscountedPrice).setScale(2, RoundingMode.HALF_EVEN)).doubleValue();

        savedAmount += (totalFullPrice - totalDiscountedPrice);
        savedAmount = (new BigDecimal(savedAmount).setScale(2, RoundingMode.HALF_EVEN)).doubleValue();
    }

    public Product getProduct() {
        return product;
    }

    public double getTotalFullPrice() {
        return totalFullPrice;
    }

    public double getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }

    public double getSavedAmount() {
        return savedAmount;
    }
}
