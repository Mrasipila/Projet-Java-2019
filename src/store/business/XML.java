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
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
* <h1> Object XML </h1>
* This class refers ours XML file.
* It contains all of our method to parse, create and use xml data
* @since   10-01-2020
*/

public class XML {
	private TransformerFactory     transformerFactory;
	private Transformer            transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder        documentBuilder;
	private LocalDateTime          ldt;


	private static String XML_PRODUCTS      = "files/produits.xml";
	private static String XML_CLIENTS       = "files/clients.xml";
	private static String XML_TRANSACTIONS  = "files/transactions.xml";
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	/**
     * This constructor initialises the tool to use for xml parsing / creating.
     */
	public XML() {
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

	/**
	* This method return the XMLFile parse
	* @param String This is the file path
	* @return parse XML --> NodeList format
	*/
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

	/**
	* This method create the XMLDocument
	* @return The new document
	*/
	public Document createXMLDocument() {
		return documentBuilder.newDocument();
	}

	/**
	* This method create dynamicaly the categories from the XML file
	* @return List<String> the list of categories
	*/
	public List<String> getCategories(){
		NodeList nodes = this.parseXMLFile(XML_PRODUCTS);
		/*On récupère la première itération de tous
		les noms des items stockés dans nodes
		excepté les items appelés #text
		et on les stocke dans une liste de string */
		LinkedList<String> catList = new LinkedList<String>();
		catList.add("Tous");
		for (int i = 0; i<nodes.getLength(); i++) {
			if(!catList.contains(nodes.item(i).getNodeName()) && nodes.item(i).getNodeName() != "#text"){
				catList.add(nodes.item(i).getNodeName());
			}
		}
		return catList;
		// Si l'element courant (currentElement) n'est pas présent dans le tableau alors on l'ajoute !pasfait!
	}

	/**
	* This method create the XMLFile
	* @param document This is the document wich is create with createXMLDocument()
	* @param filePath This is the file path for the new XMLFile
	*/
	public void createXMLFile(Document document, String filePath) {
		try {
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		System.out.println("Done creating XML File : " + filePath);
	}

	/**
	* This method parse a book with it features
	* @param currentElement current element when the xml file is parsing
	* @return Book book
	*/
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
			UUID   uniqueID   = UUID.fromString(identifier);
			book = new Book(nom_livre, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, author, Integer.valueOf(pages), language);
		} catch (Exception ex) {
			System.out.println("Something is wrong with the XML client book element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return book;
	}

	/**
	* This method parse a DVD with it features
	* @param currentElement current element when the xml file is parsing
	* @return DVD dvd
	*/
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

			UUID   uniqueID   = UUID.fromString(identifier);
			actorsVect = new Vector<String>();
			String[] allActors = actors.split(", ");
			for (String var : allActors) actorsVect.add(var);

			dvd = new DVD(nom_DVD, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, actorsVect, Integer.valueOf(duration), genre);

		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client DVD element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return dvd;
	}

	/**
	 * This method parse a Game with it features
	 * @param currentElement current element when the xml file is parsing
	 * @return Game game
	 */
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
			UUID   uniqueID   = UUID.fromString(identifier);

			platformVect = new Vector<String>();
			String[] allPlatform = platform.split(", ");
			for (String var : allPlatform) platformVect.add(var);

			game = new Game(nom_jeu, Double.valueOf(price), uniqueID, Integer.valueOf(stock), image, genre, platformVect);

		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML client game element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return game;
	}

	/**
	 * This method get all products from category. It is stock in a List of product
	 * @param category First parameter which filter the products list
	 * @return List<Product> allProducts : all products with the good category
	 */
	public List<Product> getProducts(String category) {
		Product currentProduct = new Product("sans nom", 0.0, UUID.randomUUID(), 0, "/");
		LinkedList<Product> allProducts = new LinkedList<Product>();

		NodeList nodes = this.parseXMLFile(XML_PRODUCTS);
		if (nodes == null) return null;

		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				if (category == "Tous") {
					if (currentElement.getNodeName().equals("Livres"))    currentProduct = parseLivre(currentElement);
					else if (currentElement.getNodeName().equals("DVDs")) currentProduct = parseDVD(currentElement);
					else if (currentElement.getNodeName().equals("Jeux")) currentProduct = parseGame(currentElement);
					allProducts.add(currentProduct);
				} else {
					if (currentElement.getNodeName().equals("Livres")   && category == "Livres") {
						currentProduct = parseLivre(currentElement);
						allProducts.add(currentProduct);
					}
					else if (currentElement.getNodeName().equals("DVDs") && category == "DVDs")   {
						currentProduct = parseDVD(currentElement);
						allProducts.add(currentProduct);
					}
					else if (currentElement.getNodeName().equals("Jeux") && category == "Jeux")  {
						currentProduct = parseGame(currentElement);
						allProducts.add(currentProduct);
					}
				}
			}
		}

		return allProducts;
	}

	/**
	 * This method create a XML which is the new list of product with the new stock of product
	 * @param p the product which it must decrease 
	 * @param nbUnits the count which must be decrease of stock
	 */
	public void decreaseStockProduct(Product p, int nbUnits) {
		Document document = this.createXMLDocument();
		if (document == null) return;

		Element root = document.createElement("produits");
		document.appendChild(root);
		List<Product> allProducts = getProducts("Tous");
		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getClass().getName() == "Book") {
				Book b = (Book)allProducts.get(i);
				Element book = document.createElement("Livres");
				Element identifier = document.createElement("identifier");
				identifier.appendChild(document.createTextNode(allProducts.get(i).getId().toString()));
				book.appendChild(identifier);
				Element nom_livre = document.createElement("nom_livre");
				nom_livre.appendChild(document.createTextNode(allProducts.get(i).getName()));
				book.appendChild(nom_livre);
				Element author = document.createElement("author");
				author.appendChild(document.createTextNode(b.getAuthor()));
				book.appendChild(author);
				Element pages = document.createElement("pages");
				pages.appendChild(document.createTextNode(String.valueOf(b.getPages())));
				book.appendChild(pages);
				Element language = document.createElement("language");
				language.appendChild(document.createTextNode(b.getLanguage()));
				book.appendChild(language);
				Element price = document.createElement("price");
				price.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getPrice())));
				book.appendChild(price);
				Element stock = document.createElement("stock");
				if (p.getId().toString().equals(b.getId().toString())) stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock() - nbUnits)));
				else stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock())));
				book.appendChild(stock);
				Element image = document.createElement("image");
				image.appendChild(document.createTextNode(allProducts.get(i).getImage()));
				book.appendChild(image);
				root.appendChild(book);
			} else if (allProducts.get(i).getClass().getName() == "DVD") {
				DVD d = (DVD)allProducts.get(i);
				Element dvd = document.createElement("DVDs");
				Element identifier = document.createElement("identifier");
				identifier.appendChild(document.createTextNode(allProducts.get(i).getId().toString()));
				dvd.appendChild(identifier);
				Element nom_DVD = document.createElement("nom_DVD");
				nom_DVD.appendChild(document.createTextNode(allProducts.get(i).getName()));
				dvd.appendChild(nom_DVD);
				Element actors = document.createElement("actors");
				actors.appendChild(document.createTextNode(d.printActors()));
				dvd.appendChild(actors);
				Element duration = document.createElement("duration");
				duration.appendChild(document.createTextNode(String.valueOf(d.getLength())));
				dvd.appendChild(duration);
				Element genre = document.createElement("genre");
				genre.appendChild(document.createTextNode(d.getGenre()));
				dvd.appendChild(genre);
				Element price = document.createElement("price");
				price.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getPrice())));
				dvd.appendChild(price);
				Element stock = document.createElement("stock");
				if (p.getId().toString().equals(d.getId().toString())) stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock() - nbUnits)));
				else stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock())));
				dvd.appendChild(stock);
				Element image = document.createElement("image");
				image.appendChild(document.createTextNode(allProducts.get(i).getImage()));
				dvd.appendChild(image);
				root.appendChild(dvd);
			} else if (allProducts.get(i).getClass().getName() == "Game") {
				Game g = (Game)allProducts.get(i);
				Element game = document.createElement("Jeux");
				Element identifier = document.createElement("identifier");
				identifier.appendChild(document.createTextNode(allProducts.get(i).getId().toString()));
				game.appendChild(identifier);
				Element nom_jeu = document.createElement("nom_jeu");
				nom_jeu.appendChild(document.createTextNode(allProducts.get(i).getName()));
				game.appendChild(nom_jeu);
				Element genre = document.createElement("genre");
				genre.appendChild(document.createTextNode(g.getGenre()));
				game.appendChild(genre);
				Element platform = document.createElement("platform");
				platform.appendChild(document.createTextNode(g.printPlatform()));
				game.appendChild(platform);
				Element price = document.createElement("price");
				price.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getPrice())));
				game.appendChild(price);
				Element stock = document.createElement("stock");
				if (p.getId().toString().equals(g.getId().toString())) stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock() - nbUnits)));
				else stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock())));

				game.appendChild(stock);
				Element image = document.createElement("image");
				image.appendChild(document.createTextNode(allProducts.get(i).getImage()));
				game.appendChild(image);
				root.appendChild(game);
			}
		}

		this.createXMLFile(document, XML_PRODUCTS);
	}

	/**
	 * This method parse a Game with it features
	 * @param currentElement current element when the xml file is parsing
	 * @return Client client
	 */
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

	/**
	 * This method get all clients from xml file. It is stock in a List of client
	 * @return List<Client> allClients : return all clients 
	 */
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

	/**
	 * This method add a new client in the xml file.
	 * @param firstname firstname of the new client
	 * @param lastname lastname of the new client
	 * @param email email of the new client
	 */
	public void addClient(String firstname, String lastname, String email) {
		Document document = this.createXMLDocument();
		if (document == null) return;

		Element root = document.createElement("clients");
		document.appendChild(root);

		Element client = document.createElement("client");
		UUID uniqueID = UUID.randomUUID();
		Element clientUUID = document.createElement("uniqueID");
		clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
		client.appendChild(clientUUID);
		String firstName = firstname;
		Element firstNameElement = document.createElement("firstname");
		firstNameElement.appendChild(document.createTextNode(firstName));
		client.appendChild(firstNameElement);
		String lastName = lastname;
		Element lastNameElement = document.createElement("lastname");
		lastNameElement.appendChild(document.createTextNode(lastName));
		client.appendChild(lastNameElement);
		String eMail = email;
		Element emailElement = document.createElement("email");
		emailElement.appendChild(document.createTextNode(eMail));
		client.appendChild(emailElement);
		root.appendChild(client);

		List<Client> allClients = getClients();
		for (int i = 0; i < allClients.size(); i++) {
			client = document.createElement("client");
			uniqueID = UUID.randomUUID();
			clientUUID = document.createElement("uniqueID");
			clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
			client.appendChild(clientUUID);
			firstName = allClients.get(i).getFirstname();
			firstNameElement = document.createElement("firstname");
			firstNameElement.appendChild(document.createTextNode(firstName));
			client.appendChild(firstNameElement);
			lastName = allClients.get(i).getLastname();
			lastNameElement = document.createElement("lastname");
			lastNameElement.appendChild(document.createTextNode(lastName));
			client.appendChild(lastNameElement);
			eMail = allClients.get(i).getEmail();
			emailElement = document.createElement("email");
			emailElement.appendChild(document.createTextNode(eMail));
			client.appendChild(emailElement);
			root.appendChild(client);
		}

		this.createXMLFile(document, XML_CLIENTS);
	}

	/**
	 * This method parse a Transaction with it features
	 * @param currentElement current element when the xml file is parsing
	 * @return Transaction transaction
	 */
	public Transaction parseTransaction(Element currentElement) {
		Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), 1, LocalDateTime.now().toString());
		try {
			UUID clientID  = UUID.fromString(currentElement.getElementsByTagName("clientId").item(0).getTextContent());
			UUID productID = UUID.fromString(currentElement.getElementsByTagName("productId").item(0).getTextContent());
			int numProduct = Integer.valueOf(currentElement.getElementsByTagName("numProducts").item(0).getTextContent());
			String time    = currentElement.getElementsByTagName("time").item(0).getTextContent();
			transaction = new Transaction(clientID, productID, numProduct, time);
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML transaction element");
			System.out.println("Problem is : " + ex.getMessage());
		}

		return transaction;
	}

	/**
	 * This method get all transactions from xml file. It is stock in a List of transactions
	 * @return List<Transactions> allTransactions : return allTransactions from wml file
	 */
	public List<Transaction> getTransactions() {
		Transaction currentTransaction = null;
		LinkedList<Transaction> allTransactions = new LinkedList<Transaction>();

		NodeList nodes = this.parseXMLFile(XML_TRANSACTIONS);
		if (nodes == null) return null;
		for (int i = 0; i< nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				currentTransaction = parseTransaction(currentElement);
				allTransactions.add(currentTransaction);
			}
		}

		return allTransactions;
	}


	/**
	 * This method add a new transaction in the xml file
	 * @param t transaction which must be add in the xml file
	 */
	public void addTransaction(Transaction t) {
		System.out.println();
		Document document = this.createXMLDocument();
		if (document == null) return;

		Element root = document.createElement("transactions");
		document.appendChild(root);
		Element transaction = document.createElement("transaction");
		Element clientId = document.createElement("clientId");
		clientId.appendChild(document.createTextNode(t.getClientId().toString()));
		transaction.appendChild(clientId);
		Element productId = document.createElement("productId");
		productId.appendChild(document.createTextNode(t.getProductId().toString()));
		transaction.appendChild(productId);
		Element numProducts = document.createElement("numProducts");
		numProducts.appendChild(document.createTextNode(String.valueOf(t.getNumProducts())));
		transaction.appendChild(numProducts);
		Element time = document.createElement("time");
		time.appendChild(document.createTextNode(t.getTime()));
		transaction.appendChild(time);
		root.appendChild(transaction);
		List<Transaction> allTransactions = getTransactions();
		for (int i = 0; i < allTransactions.size(); i++) {
			transaction = document.createElement("transaction");
			clientId = document.createElement("clientId");
			clientId.appendChild(document.createTextNode(allTransactions.get(i).getClientId().toString()));
			transaction.appendChild(clientId);
			productId = document.createElement("productId");
			productId.appendChild(document.createTextNode(allTransactions.get(i).getProductId().toString()));
			transaction.appendChild(productId);
			numProducts = document.createElement("numProducts");
			numProducts.appendChild(document.createTextNode(String.valueOf(allTransactions.get(i).getNumProducts())));
			transaction.appendChild(numProducts);
			time = document.createElement("time");
			time.appendChild(document.createTextNode(allTransactions.get(i).getTime()));
			transaction.appendChild(time);
			root.appendChild(transaction);
		}

		this.createXMLFile(document, XML_TRANSACTIONS);
	}
}
