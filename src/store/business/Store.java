import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class Store {
    private List<Product>                  productList;
    private List<Client>                   clientList;
    private List<Transaction>              transactionList;
    private HashMap<String, List<Product>> categories;

    public Store() {
        productList     = null;     // initialisation de la list
        clientList      = null;     // initialisation de la list
        transactionList = null;     // initialisation de la list
        categories      = null;     // initialisation de la HashMap
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

    public void loadStore(String category) {   // charge les artciles depuis le fichier XML 
        List<Product> allProducts = new LinkedList<Product>();
        XMLDemo xmlDemo = new XMLDemo();
        
        switch(category) {
            case "Tous":
                allProducts = xmlDemo.readProducts("tous");
                break;

            case "DVDs":
                allProducts = xmlDemo.readProducts("DVD");
                break;

            case "Livres":
                allProducts = xmlDemo.readProducts("livre");
                break;

            case "Jeux Vidéo":
                allProducts = xmlDemo.readProducts("game");
                break;
        }
        for (int i = 0; i < allProducts.size(); i++) {
            allProducts.get(i).printProduct();
        }
    }

    public void loadClient() { // charge les clients depuis le fichier XML 

    }
}