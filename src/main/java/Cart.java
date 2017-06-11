import java.util.HashMap;
import java.util.Map;

public class Cart {

    public static int global_id = 0;
    private final int id;
    private final Map<Product, Number> product2quantity;

    public Cart() {
        this.id = global_id++;
        product2quantity = new HashMap<>();
    }

    public void addProduct(Product newProduct, Number moreQuantity) {
        if (moreQuantity.doubleValue() <= 0)
            throw new IllegalArgumentException("Quantity to be added [" + moreQuantity + "] must be positive");

        if (newProduct.isCountable() && moreQuantity.doubleValue() != moreQuantity.intValue())
            throw new IllegalArgumentException("Quantity to be added [" + moreQuantity + "] must be an integer for countable products");

        if (newProduct.isCountable())
            product2quantity.put(newProduct, getProductQuantity(newProduct).intValue() + moreQuantity.intValue());
        else
            product2quantity.put(newProduct, getProductQuantity(newProduct).doubleValue() + moreQuantity.doubleValue());
    }

    public Number getProductQuantity(Product product) {
        Number quantity = product2quantity.get(product);
        return quantity == null ? 0 : quantity;
    }

    public int getUniqueProductCount() {
        return product2quantity.size();
    }

    // multiple occurrences of a product are counted separately
    public int getRepeatedProductCount() {
        int size = 0;

        for (Product product : product2quantity.keySet()) {
            if (product.isCountable())
                size += product2quantity.get(product).intValue();
            else
                size++;
        }

        return size;
    }

    public void clearCart() {
        product2quantity.keySet().clear();
    }

    public int getId() {
        return id;
    }

    public Map<Product, Number> getProducts() {
        return product2quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return product2quantity.equals(cart.product2quantity);
    }

    @Override
    public int hashCode() {
        return product2quantity.hashCode();
    }
}
