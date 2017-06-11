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
}
