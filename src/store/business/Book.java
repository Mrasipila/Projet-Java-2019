public class Book extends Product {

    private String author;
    private int pages;
    private Language language;

    public Book(int pages, String author, double price, String name) {
        this.pages = pages;
        this.author = author;
        this.price = price;
        this.name = name;
    }
}
