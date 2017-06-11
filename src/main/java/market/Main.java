package market;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        Product p = new Product("Beans", 0.50, true);
        customer.getCart().addProduct(p, 1);
    }
}
