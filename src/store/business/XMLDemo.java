import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;
import java.util.UUID;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;

public class XMLDemo {
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;
    
    
	private static String XML_PRODUCTS = "doc/produits.xml";
	private static String XML_CLIENTS  = "doc/clients.xml";

	private static String XML_OUTPUT_CLIENTS = "doc/clients_output.xml";

	public XMLDemo() {
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer        = transformerFactory.newTransformer();
			documentFactory    = DocumentBuilderFactory.newInstance();
			documentBuilder    = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
	}

	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
			
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return elementNodes;
	}

	public Document createXMLDocument() {
		return documentBuilder.newDocument();
	}	

	public void createXMLFile(Document document, String filePath) {
		try {
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		System.out.println("Done creating XML File");
	}

	public Book parseLivre(Element currentElement) {
		Book book = new Book("sans nom", 0.0, UUID.randomUUID(), 0, "/", "Personne", 0, "French");
		try {
			String nom_livre  = currentElement.getElementsByTagName("nom_livre").item(0).getTextContent();
			String author     = currentElement.getElementsByTagName("author").item(0).getTextContent();
			String pages      = currentElement.getElementsByTagName("pages").item(0).getTextContent();
			String language   = currentElement.getElementsByTagName("language").item(0).getTextContent();
			String price      = currentElement.getElementsByTagName("price").item(0).getTextContent();
			String stock      = currentElement.getElementsByTagName("stock").item(0).getTextContent();
			String image      = currentElement.getElementsByTagName("image").item(0).getTextContent();
			String identifier = currentElement.getElementsByTagName("identifier").item(0).getTextContent();
			UUID   uniqueID   = UUID.randomUUID();
			book = new Book(nom_livre, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, author, Integer.valueOf(pages), language);
			// book.printProduct();
		} catch (Exception ex) {
			System.out.println("Something is wrong with the XML client book element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return book;
	}

	public DVD parseDVD(Element currentElement) {
		Vector<String> actorsVect = new Vector<String>();
		actorsVect.add(" ");
		DVD dvd = new DVD("sans nom", 0.0, UUID.randomUUID(), 0, "/", actorsVect, 0, "Aucun");
		try {
			String nom_DVD    = currentElement.getElementsByTagName("nom_DVD").item(0).getTextContent();
			String actors     = currentElement.getElementsByTagName("actors").item(0).getTextContent();
			String duration   = currentElement.getElementsByTagName("duration").item(0).getTextContent();
			String genre      = currentElement.getElementsByTagName("genre").item(0).getTextContent();
			String price      = currentElement.getElementsByTagName("price").item(0).getTextContent();
			String stock      = currentElement.getElementsByTagName("stock").item(0).getTextContent();
			String image      = currentElement.getElementsByTagName("image").item(0).getTextContent();
			String identifier = currentElement.getElementsByTagName("identifier").item(0).getTextContent();
			UUID   uniqueID   = UUID.randomUUID();

			actorsVect = new Vector<String>();
			String[] allActors = actors.split(", ");
			for (String var : allActors) actorsVect.add(var);

			dvd = new DVD(nom_DVD, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, actorsVect, Integer.valueOf(duration), genre);
			// dvd.printProduct();

		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client DVD element");
			System.out.println("Problem is : " + ex.getMessage());
		}
		
		return dvd;
	}

	public Game parseGame(Element currentElement) {
		Vector<String> platformVect = new Vector<String>();
		platformVect.add(" ");
		Game game = new Game("sans nom", 0.0, UUID.randomUUID(), 0, "/", " ", platformVect);
		try {
			String identifier = currentElement.getElementsByTagName("identifier").item(0).getTextContent();
			String nom_jeu    = currentElement.getElementsByTagName("nom_jeu").item(0).getTextContent();
			String platform   = currentElement.getElementsByTagName("platform").item(0).getTextContent();
			String genre      = currentElement.getElementsByTagName("genre").item(0).getTextContent();
			String price      = currentElement.getElementsByTagName("price").item(0).getTextContent();
			String stock      = currentElement.getElementsByTagName("stock").item(0).getTextContent();
			String image      = currentElement.getElementsByTagName("image").item(0).getTextContent();
			UUID   uniqueID   = UUID.randomUUID();
			
			platformVect = new Vector<String>();
			String[] allPlatform = platform.split(", ");
			for (String var : allPlatform) platformVect.add(var);
			
			game = new Game(nom_jeu, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, genre, platformVect);
			// game.printProduct();
			
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client game element");
			System.out.println("Problem is : " + ex.getMessage());
		}
		
		return game;
	}

	public List<Product> getProducts(String category) {
		Product currentProduct = new Product("sans nom", 0.0, UUID.randomUUID(), 0, "/");
		LinkedList<Product> allProducts = new LinkedList<Product>();

		NodeList nodes = this.parseXMLFile(XML_PRODUCTS);
		if (nodes == null) return null;
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				if (category == "tous") {
					if (currentElement.getNodeName().equals("livre"))     currentProduct = parseLivre(currentElement);
					else if (currentElement.getNodeName().equals("DVD"))  currentProduct = parseDVD(currentElement);
					else if (currentElement.getNodeName().equals("game")) currentProduct = parseGame(currentElement);
					allProducts.add(currentProduct);
				} else {
					if (currentElement.getNodeName().equals("livre")     && category == "livre") {
						currentProduct = parseLivre(currentElement);
						allProducts.add(currentProduct);
					} 
					else if (currentElement.getNodeName().equals("DVD")  && category == "DVD")   {
						currentProduct = parseDVD(currentElement);
						allProducts.add(currentProduct);
					} 
					else if (currentElement.getNodeName().equals("game") && category == "game")  {
						currentProduct = parseGame(currentElement);
						allProducts.add(currentProduct);
					} 
				}
			}  
		}

		return allProducts;
	}

	public Client parseClient(Element currentElement) {
		Client client = new Client("Prénom", "Nom", "example@example.com", UUID.randomUUID());
		try {
			String firstname = currentElement.getElementsByTagName("firstname").item(0).getTextContent();
			String lastname  = currentElement.getElementsByTagName("lastname").item(0).getTextContent();
			String email     = currentElement.getElementsByTagName("email").item(0).getTextContent();
			UUID   uniqueID  = UUID.fromString(currentElement.getElementsByTagName("uniqueID").item(0).getTextContent());
			
			client = new Client(firstname, lastname, email, uniqueID);
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client element");
			System.out.println("Problem is : " + ex.getMessage());
		}
		
		return client;
	}

	public List<Client> getClients() {
		Client currentClient = null;
		LinkedList<Client> allClients = new LinkedList<Client>();

		NodeList nodes = this.parseXMLFile(XML_CLIENTS);
		if (nodes == null) return null;
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				currentClient = parseClient(currentElement);
				allClients.add(currentClient);
			}  
		}

		return allClients;
	}

	public void addClient(String firstname, String lastname, String email) {
		Document document = this.createXMLDocument();
		if (document == null) return;

			// create root element
		Element root = document.createElement("clients");
		document.appendChild(root);

		//save one "client" element; create a loop to save more elements!!
		Element client = document.createElement("client");
		// clientUUID element
		UUID uniqueID = UUID.randomUUID();
		Element clientUUID = document.createElement("uniqueID");
		clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
		client.appendChild(clientUUID);
		// firstName element
		String firstName = firstname;
		Element firstNameElement = document.createElement("firstname");
		firstNameElement.appendChild(document.createTextNode(firstName));
		client.appendChild(firstNameElement);
		//lastName element
		String lastName = lastname;
		Element lastNameElement = document.createElement("lastname");
		lastNameElement.appendChild(document.createTextNode(lastName));
		client.appendChild(lastNameElement);
		//address element
		String eMail = email;
		Element emailElement = document.createElement("email");
		emailElement.appendChild(document.createTextNode(eMail));
		client.appendChild(emailElement);
		root.appendChild(client);

		List<Client> allClients = getClients();
		for (int i = 0; i < allClients.size(); i++) {
				//save one "client" element; create a loop to save more elements!!
			client = document.createElement("client");
			// clientUUID element
			uniqueID = UUID.randomUUID();
			clientUUID = document.createElement("uniqueID");
			clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
			client.appendChild(clientUUID);
			// firstName element
			firstName = allClients.get(i).getFirstname();
			firstNameElement = document.createElement("firstname");
			firstNameElement.appendChild(document.createTextNode(firstName));
			client.appendChild(firstNameElement);
			//lastName element
			lastName = allClients.get(i).getLastname();
			lastNameElement = document.createElement("lastname");
			lastNameElement.appendChild(document.createTextNode(lastName));
			client.appendChild(lastNameElement);
			//address element
			eMail = allClients.get(i).getEmail();
			emailElement = document.createElement("email");
			emailElement.appendChild(document.createTextNode(eMail));
			client.appendChild(emailElement);
			root.appendChild(client);
		}
		
		this.createXMLFile(document, XML_CLIENTS);
	}
}