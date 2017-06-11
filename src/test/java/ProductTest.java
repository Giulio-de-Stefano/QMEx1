import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.testng.Assert.assertEquals;

public class ProductTest {

    @DataProvider(name = "badProductNames")
    public static Object[][] badProductNames() {
        return new Object[][]{
                {EMPTY},
                {null},
                {"P.ears"},
                {",Pears"},
                {"Pears'"}
        };
    }

    @DataProvider(name = "whitespaceProductNames")
    public static Object[][] whitespaceProductNames() {
        return new Object[][]{
                {" Bananas"},
                {"Apples    "},
                {"   Pears "}
        };
    }

    @DataProvider(name = "badPrices")
    public static Object[][] badPrices() {
        return new Object[][]{
                {0.0},
                {-1.0},
                {-0.01}
        };
    }

    @Test(dataProvider = "badProductNames", expectedExceptions = IllegalArgumentException.class)
    public void testProductBadNames(String badName) {
        new Product(badName, 1.00, true);
    }

    @Test(dataProvider = "whitespaceProductNames")
    public void testProductWhitespaceNames(String whitespaceName) {
        Product p = new Product(whitespaceName, 0.50, false);
        assertEquals(p.getName(), whitespaceName.trim());
    }

    @Test(dataProvider = "badPrices", expectedExceptions = IllegalArgumentException.class)
    public void testProductBadPrices(Double badPrice) {
        new Product("Whatever", badPrice, false);
    }
}
