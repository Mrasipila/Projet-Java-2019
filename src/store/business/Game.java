import java.util.UUID;
import java.util.Vector;

public class Game extends Product {
    private String genre;
    private Vector<String> platform;

    public Game(String name, double price, UUID identifier, int stock, String image, String genre, Vector<String> platform) {
        super(name, price, identifier, stock, image);
        this.genre    = genre;
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public Vector<String> getPlatform() {
        return platform;
    }

    public String printPlatform() {
        String str = new String();
        for (int i = 0; i < platform.size() - 1; i++) {
            str += platform.get(i) + ", ";
        }
        str+= platform.get(platform.size() - 1);
        return str;
    }
    
    public void printProduct() {
        System.out.println("nom_jeu    : " + name);
        System.out.println("price      : " + price);
        System.out.println("uniqueID   : " + identifier);
        System.out.println("stock      : " + stock);
        System.out.println("image      : " + image);
        System.out.println("genre      : " + genre);
        System.out.println("plateforme : " + platform);
        System.out.println();
    }
}