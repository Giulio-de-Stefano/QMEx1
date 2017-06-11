import java.util.HashMap;
import java.util.Map;

public class Cart {

    private static int global_id = 0;
    private final int id;
    private final Map<Product, Number> product2quantity;

    public Cart() {
        this.id = global_id++;
        product2quantity = new HashMap<>();
    }

    public void addProduct(Product newProduct, Number moreQuantity) {
        if (moreQuantity.doubleValue() <= 0)
            throw new IllegalArgumentException("Quantity to be added [" + moreQuantity + "] for product ["
                    + newProduct.getName() + "] must be positive");

        if (newProduct.isCountable() && moreQuantity.doubleValue() != moreQuantity.intValue())
            throw new IllegalArgumentException("Quantity to be added [" + moreQuantity + "] for product ["
                    + newProduct.getName() + "] must be an integer for countable products");

        if (newProduct.isCountable())
            product2quantity.put(newProduct, getProductQuantity(newProduct).intValue() + moreQuantity.intValue());
        else
            product2quantity.put(newProduct, getProductQuantity(newProduct).doubleValue() + moreQuantity.doubleValue());
    }

    public void removeProduct(Product remProduct) {
        removeProduct(remProduct, this.getProductQuantity(remProduct));
    }

    public void removeProduct(Product remProduct, Number lessQuantity) {
        if (lessQuantity.doubleValue() <= 0)
            throw new IllegalArgumentException("Quantity to be removed [" + lessQuantity + "] for product ["
                    + remProduct.getName() + "] must be positive");

        if (remProduct.isCountable() && lessQuantity.doubleValue() != lessQuantity.intValue())
            throw new IllegalArgumentException("Quantity to be removed [" + lessQuantity + "] for product ["
                    + remProduct.getName() + "] must be an integer for countable products");

        if (!this.containsProduct(remProduct))
            throw new IllegalArgumentException("Attempted to remove a product [" + remProduct.getName()
                    + "] that does not exist");

        // TODO check removing more than is present

        if (remProduct.isCountable())
            product2quantity.put(remProduct, getProductQuantity(remProduct).intValue() - lessQuantity.intValue());
        else
            product2quantity.put(remProduct, getProductQuantity(remProduct).doubleValue() - lessQuantity.doubleValue());
    }

    public boolean containsProduct(Product product) {
        return this.getProductQuantity(product).doubleValue() != 0;
    }

    public boolean containsProductQuantity(Product product, Number quantity) {
        return this.getProductQuantity(product).equals(quantity);
    }

    public Number getProductQuantity(Product product) {
        Number quantity = product2quantity.get(product);
        return (quantity == null || quantity.equals(0.0)) ? 0 : quantity;
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

    public void clearProducts() {
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
