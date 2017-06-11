package market.discounts;

public enum DiscountType {
    MORE_FOR_LESS("MORE_FOR_LESS"),
    N_FOR_MONEY("N_FOR_MONEY");

    String type;

    DiscountType(String type) {
        this.type = type;
    }
}
