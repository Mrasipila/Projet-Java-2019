import java.util.UUID;
import java.util.List;

/**
* <h1> Object Client </h1>
* This class refers to the client object, of any client registered in our
* xml file.
* <b>Note :</b> It contains the basics informations about our customers.
* it's firstname , lastname, email and id
* @since   10-01-2020
*/

public class Client {
    private String firstname;
    private String lastname;
    private String email;
    private UUID uniqueID;

    /**
    * This constructor initialises the basic informations of a client.
    *
    * @param firstname This is the first parameter and firstname of the client
    * @param lastname  This is the second parameter and lastname of the client
    * @param email This is the third parameter and email of the client
    * @param uniqueID  This is the fourth parameter and ID of the client
    */
    public Client(String firstname, String lastname, String email, UUID uniqueID) {
        this.firstname = firstname;
        this.lastname  = lastname;
        this.email     = email;
        this.uniqueID  = uniqueID;
    }

    /**
    * This is the method that prints you in the terminal all the info regarding the client
    */
    public void printClient() {
        System.out.println("firstname : " + firstname);
        System.out.println("lastname  : " + lastname);
        System.out.println("email     : " + email);
        System.out.println("uniqueID  : " + uniqueID);
        System.out.println();
    }

    /**
    * This is the method that gets you the firstname of the client
    * @return String This return the firstname of the client
    */
    public String getFirstname() {
        return firstname;
    }

    /**
    * This is the method that gets you the lastname of the client
    * @return String This return the lastname of the client
    */
    public String getLastname() {
        return lastname;
    }

    /**
    * This is the method that gets you the email of the client
    * @return String This return the email of the client
    */
    public String getEmail() {
        return email;
    }

    /**
    * This is the method that gets you the ID of the client
    * @return String This return the ID of the client
    */
    public UUID getId() {
        return uniqueID;
    }
}
