import java.util.UUID;
import java.util.List;

public class Client {
    private String firstName;
    private String lastName;
    private String address;
    private UUID uniqueID;

    public Client(String firstname, String lastname, String adresse, UUID uniqueID) {
        this.firstName = firstname;
        this.lastName  = lastname;
        this.address   = adresse;
        this.uniqueID  = uniqueID;
    }

    public Client() {
        
    }
}