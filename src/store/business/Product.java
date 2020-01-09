import java.util.Vector;
import java.util.UUID;
import java.util.List;

public class Product {
    protected String name;
    protected double price;
    protected UUID identifier;
    protected int stock;
    protected String image;

    public Product() {}
    public Product(String name, double price, UUID identifier, int stock, String image) {
        this.name       = name;
        this.price      = price;
        this.identifier = identifier;
        this.stock      = stock;
        this.image      = image;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public UUID getId() {
        return identifier;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public void decreaseStock(int n) {
        stock = stock - n;
    }

    public void printProduct() {}
}