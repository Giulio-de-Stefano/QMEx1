import java.util.HashMap;
import java.util.Map;

public class Cart {

    private static int global_id = 0;
    private final int id;
    private final Map<Product, Integer> product2quantity; // count if countable, GRAMS otherwise

    public Cart() {
        this.id = global_id++;
        product2quantity = new HashMap<>();
    }

    public void addProduct(Product newProduct, int moreQuantity) {
        if (moreQuantity <= 0)
            throw new IllegalArgumentException("Quantity to be added [" + moreQuantity + "] for product ["
                    + newProduct.getName() + "] must be positive");

        product2quantity.put(newProduct, getProductQuantity(newProduct) + moreQuantity);
    }

    public void removeProduct(Product remProduct) {
        removeProduct(remProduct, this.getProductQuantity(remProduct));
    }

    public void removeProduct(Product remProduct, int lessQuantity) {
        if (lessQuantity <= 0)
            throw new IllegalArgumentException("Quantity to be removed [" + lessQuantity + "] for product ["
                    + remProduct.getName() + "] must be positive");

        if (!this.containsProduct(remProduct))
            throw new IllegalArgumentException("Attempted to remove a product [" + remProduct.getName()
                    + "] that does not exist");

        int currentQuantity = getProductQuantity(remProduct);
        if (lessQuantity > currentQuantity)
            throw new IllegalArgumentException("Can't remove quantity [" + lessQuantity + "] for product ["
                    + remProduct.getName() + "] as there is only [" + currentQuantity + "]");

        product2quantity.put(remProduct, currentQuantity - lessQuantity);
    }

    public boolean containsProduct(Product product) {
        return this.getProductQuantity(product) != 0;
    }

    public boolean containsProductQuantity(Product product, int quantity) {
        return this.getProductQuantity(product) == quantity;
    }

    public int getProductQuantity(Product product) {
        Integer quantity = product2quantity.get(product);
        return (quantity == null) ? 0 : quantity;
    }

    public int getUniqueProductCount() {
        return product2quantity.size();
    }

    // multiple occurrences of a product are counted separately
    public int getRepeatedProductCount() {
        int size = 0;

        for (Product product : product2quantity.keySet()) {
            if (product.isCountable())
                size += product2quantity.get(product);
            else
                size++;
        }

        return size;
    }

    public void clearProducts() {
        product2quantity.keySet().clear();
    }

    public int getId() {
        return id;
    }

    public Map<Product, Integer> getProducts() {
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
