import java.util.UUID;
import java.sql.Timestamp;
import java.util.List;

public class Transaction {
    private UUID clientID;
    private UUID productID;
    private int numProducts;
    private Timestamp time;

    public Transaction(UUID clientID, UUID productID, int numProducts, Timestamp time) {
        this.clientID = clientID;
        this.productID = productID;
        this.numProducts = numProducts;
        this.time = time;
    }
}