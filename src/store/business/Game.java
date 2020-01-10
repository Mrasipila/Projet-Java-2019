import java.util.UUID;
import java.util.Vector;

/**
* <h1> Object Game </h1>
* This class refers to the Game object, of any Game registered in our
* xml file.
* <b>Note :</b> It contains the basics informations about our Game.
* it's name , price, ID, stock, image, genre and available platform
* @since   10-01-2020
*/

public class Game extends Product {
    private String genre;
    private Vector<String> platform;

    /**
    * This constructor initialises the basic informations of a Game.
    *
    * @param name This is the first parameter and name of the Game
    * @param price  This is the second parameter and price of the Game
    * @param identifier This is the third parameter and id of the Game
    * @param stock  This is the fourth parameter and stock of the Game
    * @param image This is the fifth parameter and image of the Game
    * @param genre  This is sixth parameter and genre of the Game
    * @param platform This is seventh parameter and platform of the Game
    */
    public Game(String name, double price, UUID identifier, int stock, String image, String genre, Vector<String> platform) {
        super(name, price, identifier, stock, image);
        this.genre    = genre;
        this.platform = platform;
    }

    /**
    * This is the method that gets you the genre of the Game
    * @return String This return the genre of the Game
    */
    public String getGenre() {
        return genre;
    }

    /**
    * This is the method that gets you the available platform of the Game
    * @return Vector<String< This return the available platforms of the Game
    */
    public Vector<String> getPlatform() {
        return platform;
    }

    /**
    * This is the method that gets you all the platforms available inside one String variable
    * @return String This return the actors that play in the DVD
    */
    public String printPlatform() {
        String str = new String();
        for (int i = 0; i < platform.size() - 1; i++) {
            str += platform.get(i) + ", ";
        }
        str+= platform.get(platform.size() - 1);
        return str;
    }

    /**
    * This is the method that prints you in the terminal all the info concerning the Game
    */
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
