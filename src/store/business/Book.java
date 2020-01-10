import java.util.UUID;
/**
* <h1> Object Book </h1>
* This class refers to the book object, of any book specified in our
* xml file.
* <b>Note :</b> It contains the basics informations about a book.
* it's number of pages number, author, language, price, identifier, stock and image.
* @since   10-01-2020
*/

public class Book extends Product {
    private String author;
    private int pages;
    private String language;

    /**
    * This constructor initialises the basic informations of a book.
    *
    * @param name This is the first parameter and name of the book
    * @param price  This is the second parameter and price of the book
    * @param identifier This is the third parameter and id of the book
    * @param stock  This is the fourth parameter and stock of the book
    * @param image This is the fifth parameter and image of book cover
    * @param author  This is sixth parameter and author of the book
    * @param pages This is seventh parameter and number of pages of the book
    * @param language This is eighth parameter and langage the book is writen in
    */
    public Book(String name, double price, UUID identifier, int stock, String image, String author, int pages, String language) {
        super(name, price, identifier, stock, image);
        this.author   = author;
        this.pages    = pages;
        this.language = language;
    }
    /**
    * This is the method that gets you the name of the author of the book
    * @return String This return the name of the author of the book
    */
    public String getAuthor() {
        return author;
    }

    /**
    * This is the method that gets you the number of pages of the book
    * @return int This return the number of pages of the book
    */
    public int getPages() {
        return pages;
    }

    /**
    * This is the method that gets you the langage the book is written in
    * @return String This return the langage the book is written in
    */
    public String getLanguage() {
        return language;
    }

    /**
    * This is the method that prints you in the terminal all the info regarding the book
    */
    public void printProduct() {
        System.out.println("nom      : " + name);
        System.out.println("price    : " + price);
        System.out.println("uniqueID : " + identifier);
        System.out.println("stock    : " + stock);
        System.out.println("image    : " + image);
        System.out.println("author   : " + author);
        System.out.println("pages    : " + pages);
        System.out.println("langage  : " + language);
        System.out.println();
    }
}
