import java.util.Vector;
import java.util.UUID;

public class DVD extends Product {
    private Vector<String> actors;
    private int length;
    private String genre;
 
    public DVD(String name, double price, UUID identifier, int stock, String image, Vector<String> actors, int length, String genre) {
        super(name, price, identifier, stock, image);
        this.actors = actors;
        this.length = length;
        this.genre = genre;
    }

    public Vector<String> getActors() {
        return actors;
    }

    public int getLength() {
        return length;
    }

    public String getGenre() {
        return genre;
    }

    public void printProduct() {
        System.out.println("Type       : DVD");
        System.out.println("nom_DVD    : " + name);
        System.out.println("price      : " + price);
        System.out.println("uniqueID   : " + identifier);
        System.out.println("stock      : " + stock);
        System.out.println("image      : " + image);
        System.out.println("actors     : " + actors);
        System.out.println("duration   : " + length);
        System.out.println("genre      : " + genre);
        System.out.println();
    }
}