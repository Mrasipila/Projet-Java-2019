import java.util.Vector;
import java.util.UUID;
import java.util.List;
/**
* <h1> Object Product </h1>
* This class refers to the mother class of the item available in our Store.
* <b>Note :</b> This is a mother class. And it is abstract
* @since   10-01-2020
*/
public class Product {
    protected String name;
    protected double price;
    protected UUID identifier;
    protected int stock;
    protected String image;

    public Product(String name, double price, UUID identifier, int stock, String image) {
        this.name       = name;
        this.price      = price;
        this.identifier = identifier;
        this.stock      = stock;
        this.image      = image;
    }

    /**
    * This is the method that gets you the name of the product
    */
    public String getName() {
        return name;
    }

    /**
    * This is the method that gets you the name of the price
    */
    public double getPrice() {
        return price;
    }

    /**
    * This is the method that gets you the name of the id
    */
    public UUID getId() {
        return identifier;
    }

    /**
    * This is the method that gets you the name of the stock
    */
    public int getStock() {
        return stock;
    }

    /**
    * This is the method that gets you the name of the image
    */
    public String getImage() {
        return image;
    }

    /**
    * This is the method an abstract method that decreases the stock of a product
    */
    public void decreaseStock(int n) {
        stock = stock - n;
    }

    /**
    * This is the method a method that print product feature
    */
    public void printProduct() {}
}
