import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CartTest {

    @DataProvider(name = "legitProducts")
    public static Object[][] legitProducts() {
        return new Object[][]{
                {new Product("Coke", 0.75, true), 5},
                {new Product("Beans", 0.50, true), 1},
                {new Product("Chocolate", 1.00, true), 3},
                {new Product("Beer", 2.80, true), 10},
                {new Product("Oranges", 1.99, false), 30},
        };
    }

    @DataProvider(name = "nonPositiveQuantitiesProducts")
    public static Object[][] nonPositiveQuantitiesProducts() {
        return new Object[][]{
                {new Product("Coke", 0.75, true), -1},
                {new Product("Oranges", 1.99, false), 0},
                {new Product("Apples", 1.99, false), -10000},
        };
    }

    @Test(dataProvider = "legitProducts")
    public void testAddProductLegitQuantity(Product product, int quantity) {
        Cart c = new Cart();
        c.addProduct(product, quantity);
        c.getProductQuantity(product);
    }

    @Test(dataProvider = "nonPositiveQuantitiesProducts",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*must be positive.*")
    public void testAddProductNonPositiveQuantity(Product product, int quantity) {
        Cart c = new Cart();
        c.addProduct(product, quantity);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*does not exist.*")
    public void testRemoveProductAbsent() {
        Product beans = new Product("Beans", 0.50, true);
        Cart c = new Cart();
        c.removeProduct(beans, 1);
    }

    @Test(dataProvider = "nonPositiveQuantitiesProducts",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*must be positive.*")
    public void testRemoveProductNonPositiveQuantity(Product product, int quantity) {
        Cart c = new Cart();
        c.removeProduct(product, quantity);
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
        c.addProduct(weighableProduct, 200);
        c.addProduct(weighableProduct, 400);
        c.addProduct(weighableProduct, 300);
        assertEquals(c.getProductQuantity(weighableProduct), 900);
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
        cart.addProduct(oranges, 750);

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
        cart.addProduct(oranges, 750);

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
        c.addProduct(oranges, 800);
        assertEquals(c.getUniqueProductCount(), 2);

        c.clearProducts();
        assertEquals(c.getUniqueProductCount(), 0);
    }

    @Test
    public void testContainsProduct() {
        Product beans = new Product("Beans", 0.50, true);

        Cart c = new Cart();
        assertFalse(c.containsProduct(beans));

        c.addProduct(beans, 1);
        assertTrue(c.containsProduct(beans));

        c.addProduct(beans, 3);
        assertTrue(c.containsProduct(beans));

        c.removeProduct(beans, 1);
        assertTrue(c.containsProduct(beans));

        c.removeProduct(beans);
        assertFalse(c.containsProduct(beans));
    }

    @Test
    public void testContainsProductQuantity() {
        Product oranges = new Product("Oranges", 1.50, false);

        Cart c = new Cart();
        assertTrue(c.containsProductQuantity(oranges, 0));
        assertFalse(c.containsProductQuantity(oranges, 1));

        c.addProduct(oranges, 500);
        assertTrue(c.containsProductQuantity(oranges, 500));

        c.removeProduct(oranges, 375);
        assertTrue(c.containsProductQuantity(oranges, 125));

        c.removeProduct(oranges, 125);
        assertTrue(c.containsProductQuantity(oranges, 0));
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

