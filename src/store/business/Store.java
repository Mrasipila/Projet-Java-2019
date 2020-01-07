import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class Store {
    private XMLDemo xmlDemo;
    private List<Product>                  productList;
    private List<Client>                   clientList;
    private List<Transaction>              transactionList;
    private HashMap<String, List<Product>> categories;

    public Store(String category) {
        xmlDemo         = new XMLDemo();
        productList     = xmlDemo.getProducts(getCategory(category));      // initialisation de la list
        clientList      = xmlDemo.getClients();            // initialisation de la list
        transactionList = null;                     // initialisation de la list
        categories      = null;                     // initialisation de la HashMap
    }

    private String getCategory(String category) {
        if (category == "Tous")   return "tous";
        if (category == "DVDs")   return "DVD";
        if (category == "Livres") return "livre";
        else return "game";
    }

    public void updateProducts(String category) {
        productList = xmlDemo.getProducts(getCategory(category));
    }

    public List<Product> getProducts() {
        return productList;
    }

    public List<Client> getClients() {
        return clientList;
    }

    public void printProducts() {
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).printProduct();
        }
    }

    public void printClients() {
        for (int i = 0; i < clientList.size(); i++) {
            clientList.get(i).printClient();
        }
    }

    public void addProduct(Product p) {
        productList.add(p);
    }

    public void addClient(Client c) {
        clientList.add(c);
    }

    public void addTransaction(Transaction t) {
        transactionList.add(t);
    }

    public void addCategory(Product p, String cat) {
        categories.keySet();
    }
}