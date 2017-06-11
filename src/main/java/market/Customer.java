package market;

public class Customer {

    private static int global_id = 0;
    private final int id;
    private final Cart cart;

    public Customer() {
        this.id = global_id++;
        this.cart = new Cart();
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }
}
