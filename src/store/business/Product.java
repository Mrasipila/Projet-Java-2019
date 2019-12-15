import java.util.UUID;
import java.util.List;

public abstract class Product {
    private String name;
    private double price;
    private UUID identifier;
    private int stock;
    private String image;

    public Product() {

    }

    public List<Product> getProducts(String xmlPath) {
        List<Product> allProducts = new List<Product>();
        
        return allProducts;
    }
}
