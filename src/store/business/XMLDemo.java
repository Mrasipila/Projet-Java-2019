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
import java.time.LocalDateTime;

public class XMLDemo {
	private TransformerFactory     transformerFactory;
	private Transformer            transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder        documentBuilder;
	private LocalDateTime          ldt;
    
    
	private static String XML_PRODUCTS      = "files/produits.xml";
	private static String XML_CLIENTS       = "files/clients.xml";
	private static String XML_TRANSACTIONS  = "files/transactions.xml";
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

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
			UUID   uniqueID   = UUID.fromString(identifier);
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
			UUID   uniqueID   = UUID.fromString(identifier);

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
			UUID   uniqueID   = UUID.fromString(identifier);
			
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

	public void decreaseStockProduct(Product p, int nbUnits) {
		Document document = this.createXMLDocument();
		if (document == null) return;

		Element root = document.createElement("produits");
		document.appendChild(root);
		List<Product> allProducts = getProducts("tous");
		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getClass().getName() == "Book") {
				Book b = (Book)allProducts.get(i);
				Element book = document.createElement("livre");
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
				if (p.getId() == b.getId()) stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock() - nbUnits)));
				else stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock())));
				book.appendChild(stock);
				Element image = document.createElement("image");
				image.appendChild(document.createTextNode(allProducts.get(i).getImage()));
				book.appendChild(image);
				root.appendChild(book);
			} else if (allProducts.get(i).getClass().getName() == "DVD") {
				DVD d = (DVD)allProducts.get(i);
				Element dvd = document.createElement("DVD");
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
				if (p.getId() == d.getId()) stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock() - nbUnits)));
				else stock.appendChild(document.createTextNode(String.valueOf(allProducts.get(i).getStock())));
				dvd.appendChild(stock);
				Element image = document.createElement("image");
				image.appendChild(document.createTextNode(allProducts.get(i).getImage()));
				dvd.appendChild(image);
				root.appendChild(dvd);
			} else if (allProducts.get(i).getClass().getName() == "Jeux") {
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
				System.out.println("p : " + p.getId());
				System.out.println("g : " + g.getId());
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

	public Transaction parseTransaction(Element currentElement) {
		Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), 0, String.valueOf(ldt));
		try {
			UUID clientID  = UUID.fromString(currentElement.getElementsByTagName("clientId").item(0).getTextContent());
			System.out.println(clientID);
			UUID productID = UUID.fromString(currentElement.getElementsByTagName("productId").item(0).getTextContent());
			System.out.println(productID);
			int numProduct = Integer.valueOf(currentElement.getElementsByTagName("numProducts").item(0).getTextContent());
			System.out.println(numProduct);
			String time    = currentElement.getElementsByTagName("time").item(0).getTextContent();
			System.out.println(time);
			transaction = new Transaction(clientID, productID, numProduct, time);
		} catch(Exception ex) {
			System.out.println("Something is wrong with the XML transaction element");
			System.out.println("Problem is : " + ex.getMessage());
		}
		
		return transaction;
	}

	public List<Transaction> getTransactions() {
		Transaction currentTransaction = null;
		LinkedList<Transaction> allTransactions = new LinkedList<Transaction>();

		NodeList nodes = this.parseXMLFile(XML_TRANSACTIONS);
		if (nodes == null) return allTransactions;
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				currentTransaction = parseTransaction(currentElement);
				allTransactions.add(currentTransaction);
			}  
		}
		
		return allTransactions;
	}
	
	
	public void addTransaction(Transaction t) {
		Document document = this.createXMLDocument();
		if (document == null) return;
		
		Element root = document.createElement("transactions");
		document.appendChild(root);
		
		Element transaction = document.createElement("transaction");
		Element clientID = document.createElement("clientId");
		clientID.appendChild(document.createTextNode(t.getClientId().toString()));
		transaction.appendChild(clientID);
		Element productID = document.createElement("productId");
		productID.appendChild(document.createTextNode(t.getProductId().toString()));
		transaction.appendChild(productID);
		Element numProducts = document.createElement("numProducts");
		numProducts.appendChild(document.createTextNode(String.valueOf(t.getNumProducts())));
		transaction.appendChild(numProducts);
		Element time = document.createElement("time");
		time.appendChild(document.createTextNode(LocalDateTime.now().toString()));
		transaction.appendChild(time);
		root.appendChild(transaction);
		System.out.println("Avant getTransactions");
		List<Transaction> allTransactions = getTransactions();
		System.out.println("Après getTransactions");
		for (int i = 0; i < allTransactions.size(); i++) {
			transaction = document.createElement("transaction");
			clientID = document.createElement("clientID");
			clientID.appendChild(document.createTextNode(allTransactions.get(i).getClientId().toString()));
			transaction.appendChild(clientID);
			productID = document.createElement("productID");
			productID.appendChild(document.createTextNode(allTransactions.get(i).getProductId().toString()));
			transaction.appendChild(productID);
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