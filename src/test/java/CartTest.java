import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartTest {

    @DataProvider(name = "legitProducts")
    public static Object[][] legitProducts() {
        return new Object[][]{
                {new Product("Coke", 0.75, true), 5},
                {new Product("Beans", 0.50, true), 1},
                {new Product("Chocolate", 1.00, true), 3},
                {new Product("Beer", 2.80, true), 10},
                {new Product("Oranges", 1.99, false), 0.3},
        };
    }

    @DataProvider(name = "badQuantitiesProducts")
    public static Object[][] badQuantitiesProducts() {
        return new Object[][]{
                {new Product("Coke", 0.75, true), -1},
                {new Product("Beans", 0.50, true), -0.01},
                {new Product("Chocolate", 1.00, true), 0.99},
                {new Product("Beer", 2.80, true), 0.25},
                {new Product("Oranges", 1.99, false), 0},
                {new Product("Apples", 1.99, false), -1},
                {new Product("Bananas", 1.99, false), -0.01},
                {new Product("Chocolate", 1.99, true), 0.5},
        };
    }

    @Test(dataProvider = "legitProducts")
    public void testAddProductLegitQuantity(Product product, Number quantity) {
        Cart c = new Cart();
        c.addProduct(product, quantity);
        c.getProductQuantity(product);
    }

    @Test(dataProvider = "badQuantitiesProducts", expectedExceptions = IllegalArgumentException.class)
    public void testAddProductBadQuantity(Product product, Number quantity) {
        Cart c = new Cart();
        c.addProduct(product, quantity);
    }

    @Test
    public void testGetQuantityMultipleProducts() {
        Cart c = new Cart();
        Product countableProduct = new Product("Beans", 0.50, true);

        c.addProduct(countableProduct, 1);
        c.addProduct(countableProduct, 5);
        c.addProduct(countableProduct, 3);
        assertEquals(c.getProductQuantity(countableProduct), 9);

        Product weighableProduct = new Product("Bananas", 1.8, false);
        c.addProduct(weighableProduct, 0.2);
        c.addProduct(weighableProduct, 0.4);
        c.addProduct(weighableProduct, 3);
        assertEquals(c.getProductQuantity(weighableProduct), 3.6);
    }

    @Test
    public void testGetUniqueProductCount() {
        Product beans = new Product("Beans", 0.50, true);
        Product chocolate = new Product("Chocolate", 1.65, true);
        Product oranges = new Product("Oranges", 0.99, false);

        Cart cart = new Cart();
        assertEquals(cart.getUniqueProductCount(), 0);

        cart.addProduct(beans, 4);
        cart.addProduct(chocolate, 1);
        cart.addProduct(oranges, 0.75);

        int uniqueProducts = 3;
        assertEquals(cart.getUniqueProductCount(), uniqueProducts);
    }

    @Test
    public void testGetRepeatedProductCount() {
        Product beans = new Product("Beans", 0.50, true);
        Product chocolate = new Product("Chocolate", 1.65, true);
        Product oranges = new Product("Oranges", 0.99, false);

        Cart cart = new Cart();
        assertEquals(cart.getRepeatedProductCount(), 0);

        cart.addProduct(beans, 4);
        cart.addProduct(chocolate, 1);
        cart.addProduct(oranges, 0.75);

        int repeatedProducts = 6;
        assertEquals(cart.getRepeatedProductCount(), repeatedProducts);
    }

    @Test
    public void testClear() {
        Product beans = new Product("Beans", 0.50, true);
        Product oranges = new Product("Oranges", 0.99, false);

        Cart c = new Cart();
        assertEquals(c.getUniqueProductCount(), 0);
        c.clearProducts();
        assertEquals(c.getUniqueProductCount(), 0);

        c.addProduct(beans, 3);
        c.addProduct(oranges, 0.80);
        assertEquals(c.getUniqueProductCount(), 2);

        c.clearProducts();
        assertEquals(c.getUniqueProductCount(), 0);
    }

    @Test
    public void testEquals() {
        Product beans = new Product("Beans", 0.50, true);
        Product chocolate = new Product("Chocolate", 1.65, true);

        Cart c1 = new Cart();
        Cart c2 = new Cart();
        assertTrue(c1.equals(c2));

        c1.addProduct(beans, 3);
        c2.addProduct(beans, 3);
        assertTrue(c1.equals(c2));

        c1.addProduct(chocolate, 2);
        c2.addProduct(chocolate, 2);
        assertTrue(c1.equals(c2));
    }
}

