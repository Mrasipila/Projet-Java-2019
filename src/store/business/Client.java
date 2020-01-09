import java.util.UUID;
import java.util.List;

public class Client {
    private String firstname;
    private String lastname;
    private String email;
    private UUID uniqueID;

    public Client(String firstname, String lastname, String email, UUID uniqueID) {
        this.firstname = firstname;
        this.lastname  = lastname;
        this.email     = email;
        this.uniqueID  = uniqueID;
    }

    public void printClient() {
        System.out.println("firstname : " + firstname);
        System.out.println("lastname  : " + lastname);
        System.out.println("email     : " + email);
        System.out.println("uniqueID  : " + uniqueID);
        System.out.println();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    } 

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return uniqueID;
    }
}