import java.util.UUID;
import java.util.List;

public class Transaction {
    private UUID clientId;
    private UUID productId;
    private int numProducts;
    private String time;

    public Transaction(UUID clientId, UUID productId, int numProducts, String time) {
        this.clientId    = clientId;
        this.productId   = productId;
        this.numProducts = numProducts;
        this.time        = time;
    }

    public void printTransaction() {
        System.out.println("clientId    : " + clientId);
        System.out.println("productId   : " + productId);
        System.out.println("numProducts : " + numProducts);
        System.out.println("time        : " + time);
        System.out.println();
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public String getTime() {
        return time;
    }
}