import java.util.List;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

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
        if (category == "Tous")       return "tous";
        if (category == "DVDs")       return "DVD";
        if (category == "Livres")     return "livre";
        if (category == "Jeux Vidéo") return "game";
        else return category;
    }

    public List<String> getCategories() {
        return xmlDemo.getCategories();
    }

    public void updateProducts(String category) {
        // productList = xmlDemo.getProducts(getCategory(category));
        productList = xmlDemo.getProducts(category);
    }

    public Client getClient(String first, String last) {
        Client client = new Client("Prénom", "Nom", "Aucun résultat", UUID.randomUUID());
        List<Client> clients = xmlDemo.getClients();
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(clients.get(i).getLastname().toLowerCase());
            System.out.println(last.toLowerCase());
            clients.get(i).printClient();
            if (clients.get(i).getFirstname().toLowerCase().equals(first.toLowerCase()) && clients.get(i).getLastname().toLowerCase().equals(last.toLowerCase())) client = clients.get(i);
            else if (clients.get(i).getLastname().toLowerCase().equals(last.toLowerCase())) client = clients.get(i);
        }
        return client;
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
        
    }

    public void decreaseProduct(Product p, int nbUnits) {
        xmlDemo.decreaseStockProduct(p, nbUnits);
        for (int i = 0; i < productList.size(); i++) {
            if (p.getId() == productList.get(i).getId()) {
                productList.get(i).decreaseStock(nbUnits);
                System.out.println(productList.get(i).getStock());
            }
        }
    }

    public void addClient(String firstname, String lastname, String email) {
        xmlDemo.addClient(firstname, lastname, email);
    }

    public void addTransaction(Transaction t) {
        xmlDemo.addTransaction(t);
    }

    public void addCategory(Product p, String cat) {
        categories.keySet();
    }
}