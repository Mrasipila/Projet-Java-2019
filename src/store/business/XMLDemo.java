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
    
    
	private static String XML_INPUT_PRODUCTS = "doc/produits.xml";
	private static String XML_INPUT_CLIENTS = "doc/clients.xml";

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

	public Book parseLivre(Element currentElement) {
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
			Book book = new Book(nom_livre, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, author, Integer.valueOf(pages), language);
			// book.printProduct();

			return book;
		} catch (Exception ex) {
			System.out.println("Something is wrong with the XML client book element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return null;
	}

	public DVD parseDVD(Element currentElement) {
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

			Vector<String> actorsVect = new Vector<String>();
			String[] allActors = actors.split(", ");
			for (String var : allActors) actorsVect.add(var);

			DVD dvd = new DVD(nom_DVD, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, actorsVect, Integer.valueOf(duration), genre);
			// dvd.printProduct();

			return dvd;
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client DVD element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return null;
	}

	public Game parseGame(Element currentElement) {
		try {
			String identifier = currentElement.getElementsByTagName("identifier").item(0).getTextContent();
			String nom_jeu    = currentElement.getElementsByTagName("nom_jeu").item(0).getTextContent();
			String platform   = currentElement.getElementsByTagName("platform").item(0).getTextContent();
			String genre      = currentElement.getElementsByTagName("genre").item(0).getTextContent();
			String price      = currentElement.getElementsByTagName("price").item(0).getTextContent();
			String stock      = currentElement.getElementsByTagName("stock").item(0).getTextContent();
			String image      = currentElement.getElementsByTagName("image").item(0).getTextContent();
			UUID   uniqueID   = UUID.randomUUID();
			
			Vector<String> platformVect = new Vector<String>();
			String[] allPlatform = platform.split(", ");
			for (String var : allPlatform) platformVect.add(var);
			
			Game game = new Game(nom_jeu, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, genre, platformVect);
			// game.printProduct();
			
			return game;
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client game element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return null;
	}

	public List<Product> getProducts(String category) {
		Product currentProduct = null;
		LinkedList<Product> allProducts = new LinkedList<Product>();

		NodeList nodes = this.parseXMLFile(XML_INPUT_PRODUCTS);
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
		try {
			String firstname = currentElement.getElementsByTagName("firstname").item(0).getTextContent();
			String lastname  = currentElement.getElementsByTagName("lastname").item(0).getTextContent();
			String address   = currentElement.getElementsByTagName("address").item(0).getTextContent();
			String email     = currentElement.getElementsByTagName("email").item(0).getTextContent();
			UUID   uniqueID  = UUID.randomUUID();
			
			Client client = new Client(firstname, lastname, address, email, uniqueID);
			
			return client;
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return null;
	}

	public List<Client> getClients() {
		Client currentClient = null;
		LinkedList<Client> allClients = new LinkedList<Client>();

		NodeList nodes = this.parseXMLFile(XML_INPUT_CLIENTS);
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
}