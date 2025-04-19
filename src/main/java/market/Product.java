package market;

import static org.apache.commons.lang3.StringUtils.isAlphanumericSpace;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Product {

    private static int global_id = 0;
    private final int id;
    private final String name;
    private final Double price; // per unit or per KG
    private final boolean isCountable;

    public Product(String name, Double price, boolean isCountable) {
        if (isBlank(name))
            throw new IllegalArgumentException("A product name can't be null or empty");

        if (!isAlphanumericSpace(name))
            throw new IllegalArgumentException("A product may only contain alphanumeric characters and spaces");

        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive");

        this.id = global_id++;
        this.name = name.trim();
        this.price = price;
        this.isCountable = isCountable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isCountable() {
        return isCountable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        
        if (o == null || getClass() != o.getClass())
            return false;

        Product product = (Product) o;

        if (isCountable != product.isCountable)
            return false;
        
        return name.equals(product.name) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (isCountable ? 1 : 0);
        
        return result;
    }
}
