import java.util.UUID;

public class Book extends Product {
    private String author;
    private int pages;
    private String language;

    public Book(String name, double price, UUID identifier, int stock, String image, String author, int pages, String language) {
        super(name, price, identifier, stock, image);
        this.author   = author;
        this.pages    = pages;
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public String getLanguage() {
        return language;
    }

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