import java.util.UUID;
import java.util.List;

/**
* <h1> Object Transaction </h1>
* This class refers our Transaction. It contains all of our products and clients informations.
* It also contains methods to interact with the features.
* @since   10-01-2020
*/

public class Transaction {
    private UUID clientId;
    private UUID productId;
    private int numProducts;
    private String time;

    /**
    * This constructor initialises the basic informations of our Transaction.
    * Including all our clients, transaction list and products.
    * @param clientId This is the first parameter and UUID of the client,
    * @param productId This is the second parameter and UUID of the product
    * @param numProducts This is the third parameter and the number of Product to buy
    * @param time This is the fourth parameter and the current time of transaction
    */
    public Transaction(UUID clientId, UUID productId, int numProducts, String time) {
        this.clientId    = clientId;
        this.productId   = productId;
        this.numProducts = numProducts;
        this.time        = time;
    }

    /**
    * This method print the features of Transaction
    */
    public void printTransaction() {
        System.out.println("clientId    : " + clientId);
        System.out.println("productId   : " + productId);
        System.out.println("numProducts : " + numProducts);
        System.out.println("time        : " + time);
        System.out.println();
    }

    /**
    * This method gets you the clientId
    * @return UUID of client who is buying the product
    */
    public UUID getClientId() {
        return clientId;
    }

    /**
    * This method gets you the productId
    * @return UUID of product
    */
    public UUID getProductId() {
        return productId;
    }

    /**
    * This method gets you the number of product which are buying
    * @return the number of product which are buying
    */
    public int getNumProducts() {
        return numProducts;
    }

    /**
    * This method gets you the time of transaction
    * @return the transaction's time in String
    */
    public String getTime() {
        return time;
    }
}
