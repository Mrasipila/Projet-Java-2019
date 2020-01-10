import java.util.List;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
* <h1> Object Store </h1>
* This class refers our Store. It contains all of our products and methods
* to interact with them.
* @since   10-01-2020
*/

public class Store {
    private XMLDemo xmlDemo;
    private List<Product>                  productList;
    private List<Client>                   clientList;
    private List<Transaction>              transactionList;
    private HashMap<String, List<Product>> categories;

    /**
    * This constructor initialises the basic informations of our Store.
    * Including all our clients, transaction list and products.
    * @param category This is the first parameter and name of the category,
     ["Tous","DVDs","Livres","Jeux-Video"]
    */
    public Store(String category) {
        xmlDemo         = new XMLDemo();
        productList     = xmlDemo.getProducts(getCategory(category));      // initialisation de la list
        clientList      = xmlDemo.getClients();            // initialisation de la list
        transactionList = null;                     // initialisation de la list
        categories      = null;                     // initialisation de la HashMap
    }

    /**
    * This method gets you the associated string of category selected in our Store.
    * @param category This is the first parameter and name of the category,
     ["Tous","DVDs","Livres","Jeux-Video"]
    * @return String the String containing the category selected in our Store.
    */
    private String getCategory(String category) {
        if (category == "Tous")       return "tous";
        if (category == "DVDs")       return "DVD";
        if (category == "Livres")     return "livre";
        if (category == "Jeux Vidéo") return "game";
        else return category;
    }

    /**
    * This method gets you the category generated automaticaly from xml file
    * @return List<String> containing the category of all the products present in our xml file.
    */
    public List<String> getCategories() {
        return xmlDemo.getCategories();
    }

    /**
    * This method gets you the category generated automaticaly from xml file
    * @param category the category that you want to get the list of.
    * @return List<String> of all the products of the selected category
    */
    public void updateProducts(String category) {
        // productList = xmlDemo.getProducts(getCategory(category));
        productList = xmlDemo.getProducts(category);
    }

    /**
    * This method gets you the client of desired firstname and surname
    * @param first the firstname of the client you want to get the object of.
    * @param last the lastname of the client you want to get the object of.
    * @return Client, of the clients of the selected firstname and lastname
    */
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

    /**
    * This method gets you the productlist of the actual category selected in your store
    * @return List<Product> of all the products of the selected category
    */
    public List<Product> getProducts() {
        return productList;
    }

    /**
    * This method gets you the client list of all the clients registered in your store
    * @return List<Client> of all the client of your store
    */
    public List<Client> getClients() {
        return clientList;
    }

    /**
    * This method prints in the terminal all the product in our store
    */
    public void printProducts() {
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).printProduct();
        }
    }

    /**
    * This method prints in the terminal all the clients in our store
    */
    public void printClients() {
        for (int i = 0; i < clientList.size(); i++) {
            clientList.get(i).printClient();
        }
    }

    public void addProduct(Product p) {

    }

    /**
    * This method decreases the stock of selected product
    * @param p this is the first paramater of the product you want to decrease the stock
    * @param nbUnits this is the second paramater of the number
    of product you want to remove from the store
    */
    public void decreaseProduct(Product p, int nbUnits) {
        xmlDemo.decreaseStockProduct(p, nbUnits);
        for (int i = 0; i < productList.size(); i++) {
            if (p.getId() == productList.get(i).getId()) {
                productList.get(i).decreaseStock(nbUnits);
                System.out.println(productList.get(i).getStock());
            }
        }
    }

    /**
    * This method adds client to our store
    * @param firstname this is the first parameter of the firstname of the new client
    * @param lastname this is the second paramater of the lastname of the new client
    * @param email this is the third paramater of the email of the new client
    */
    public void addClient(String firstname, String lastname, String email) {
        xmlDemo.addClient(firstname, lastname, email);
    }

    /**
    * This method adds a transaction to the xml file
    * @param t this is the first parameter of the transaction you want to add
    */
    public void addTransaction(Transaction t) {
        xmlDemo.addTransaction(t);
    }

    public void addCategory(Product p, String cat) {
        categories.keySet();
    }
}
