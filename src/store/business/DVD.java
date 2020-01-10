import java.util.Vector;
import java.util.UUID;

/**
* <h1> Object DVD </h1>
* This class refers to the DVD object, of any DVD registered in our
* xml file.
* <b>Note :</b> It contains the basics informations about our DVD.
* it's name , price, ID, stock, image, actors that play in it, length and genre
* @since   10-01-2020
*/

public class DVD extends Product {
    private Vector<String> actors;
    private int length;
    private String genre;

    /**
    * This constructor initialises the basic informations of a DVD.
    *
    * @param name This is the first parameter and name of the DVD
    * @param price  This is the second parameter and price of the DVD
    * @param identifier This is the third parameter and id of the DVD
    * @param stock  This is the fourth parameter and stock of the DVD
    * @param image This is the fifth parameter and image of the DVD
    * @param actors  This is sixth parameter and actors of the DVD
    * @param length This is seventh parameter and length of the DVD
    * @param genre This is eighth parameter and genre of the DVD
    */
    public DVD(String name, double price, UUID identifier, int stock, String image, Vector<String> actors, int length, String genre) {
        super(name, price, identifier, stock, image);
        this.actors = actors;
        this.length = length;
        this.genre = genre;
    }

    /**
    * This is the method that gets you the actors that play in the DVD
    * @return Vector<String> This return the actors that play in the DVD
    */
    public Vector<String> getActors() {
        return actors;
    }

    /**
    * This is the method that gets you all the playing actor inside one String variable
    * @return String This return the actors that play in the DVD
    */
    public String printActors() {
        String str = new String();
        for (int i = 0; i < actors.size() - 1; i++) {
            str += actors.get(i) + ", ";
        }
        str+= actors.get(actors.size() - 1);
        return str;
    }

    /**
    * This is the method that gets you the length of the DVD
    * @return int This return the length of the DVD
    */
    public int getLength() {
        return length;
    }

    /**
    * This is the method that gets you the genre of the DVD
    * @return String This return the genre of the DVD
    */
    public String getGenre() {
        return genre;
    }

    /**
    * This is the method that prints you in the terminal all the info concerning the DVD
    */
    public void printProduct() {
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
