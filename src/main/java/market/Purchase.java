package market;

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
    private double savedAmount;
    private boolean discountApplied;

    public Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        if (product.isCountable())
            this.totalFullPrice = quantity * product.getPrice();
        else
            this.totalFullPrice = (double) quantity / 1000 * product.getPrice();

        this.savedAmount = 0;
        this.discountApplied = false;
        totalDiscountedPrice = totalFullPrice;
    }

    public void applyDiscount(DiscountAB discount) {
        if (!discount.getProduct().getName().equals(this.product.getName()))
            throw new UnsupportedOperationException("Can't apply a discount for [" + discount.getProduct().getName()
                    + "] to [" + this.getProduct().getName() + "]");

        if (discountApplied)
            throw new UnsupportedOperationException("A discount has already been applied to product ["
                    + this.getProduct().getName() + "]");

        switch (discount.getType()) {
            case N_FOR_MONEY:
                this.applyNForMoneyDiscount((NForMoneyDiscount) discount);
                break;

            case MORE_FOR_LESS:
                this.applyMoreForLessDiscount((MoreForLessDiscount) discount);
                break;

            default:
                throw new IllegalArgumentException("Unknown discount type [" + discount.getType() + "]");
        }

        discountApplied = true;
    }

    private void applyNForMoneyDiscount(NForMoneyDiscount discount) {
        int discountQuantity = discount.getQuantity();
        double discountPrice = discount.getDiscountedPrice();

        double discountRatio;
        double eligibleQuantity;
        final int GRAMS_IN_KG = 1000;

        if (product.isCountable()) {
            discountRatio = discountPrice / discountQuantity;
            eligibleQuantity = Math.floorDiv(quantity, discountQuantity) * discountQuantity;
        } else {
            discountRatio = discountPrice / ((double) discountQuantity / GRAMS_IN_KG);
            eligibleQuantity = (double) quantity / (double) GRAMS_IN_KG; // the whole weight qualifies for a @KG discount
        }

        double eligibleQuantityPrice = eligibleQuantity * discountRatio;

        if (product.isCountable())
            totalDiscountedPrice = eligibleQuantityPrice + (quantity - eligibleQuantity) * this.product.getPrice();
        else
            totalDiscountedPrice = eligibleQuantityPrice;

        totalDiscountedPrice = (new BigDecimal(totalDiscountedPrice).setScale(2, RoundingMode.HALF_EVEN)).doubleValue();

        savedAmount += (totalFullPrice - totalDiscountedPrice);
        savedAmount = (new BigDecimal(savedAmount).setScale(2, RoundingMode.HALF_EVEN)).doubleValue();
    }

    private void applyMoreForLessDiscount(MoreForLessDiscount discount) {
        int buyQuantity = discount.getBuyQuantity();
        int payQuantity = discount.getPayQuantity();
        double discountRatio = (double) payQuantity / (double) buyQuantity;
        double eligibleQuantity = Math.floorDiv(quantity, buyQuantity) * buyQuantity;
        double eligibleQuantityPrice = eligibleQuantity * product.getPrice() * discountRatio;

        totalDiscountedPrice = eligibleQuantityPrice + (quantity - eligibleQuantity) * this.product.getPrice();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (quantity != purchase.quantity) return false;
        if (Double.compare(purchase.totalFullPrice, totalFullPrice) != 0) return false;
        if (Double.compare(purchase.totalDiscountedPrice, totalDiscountedPrice) != 0) return false;
        if (Double.compare(purchase.savedAmount, savedAmount) != 0) return false;
        if (discountApplied != purchase.discountApplied) return false;
        return product.equals(purchase.product);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = product.hashCode();
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(totalFullPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDiscountedPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(savedAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (discountApplied ? 1 : 0);
        return result;
    }
}
