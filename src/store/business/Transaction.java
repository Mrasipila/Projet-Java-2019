import java.util.UUID;
import java.sql.Timestamp;
import java.util.List;

public class Transaction {
    private UUID clientID;
    private UUID productID;
    private int numProducts;
    private Timestamp time;

    public Transaction() {

    }

    public List<Transaction> getTransactions(String xmlPath) {
        List<Transaction> allTransations = new List<Transaction>();
        return allTransations;
    }
}