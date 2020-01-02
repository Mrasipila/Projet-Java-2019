import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class StoreGUI implements ActionListener {
    private Store store;

    // PANEL TOP
    private JPanel infoStore;
    private JLabel imgStore;
    private JComboBox searchProduct;
    private JButton searchBtn;

    // PANEL PRODUCT
    private JList listProducts;
    private JPanel currentProduct;
    private JLabel imgProduct;

    // PANEL CLIENT
    private JTextField searchClient;
    private JButton searchClientBtn;
    private JPanel infoClient;

    // PANEL BUY
    private JPanel clientBuy;
    private JPanel productBuy;
    private JButton buyBtn;

    public StoreGUI() {
        store = new Store();

        JPanel center = new JPanel(new GridLayout(4, 1));
        
        // INSERTION DE LA PARTIE INFORMATION DE L'APPLICATION
        infoStore         = new JPanel(new FlowLayout());   // Définition de la ligne
        JPanel infoPanel  = new JPanel(new BorderLayout());  // Panneau qui contient logo + titre
        JLabel titleStore = new JLabel("Amazon du pauvre"); // titre
        imgStore          = new JLabel(new ImageIcon("files/logoShop.png")); // logo
        infoPanel.add(titleStore, BorderLayout.NORTH);  // insertion du titre
        infoPanel.add(imgStore, BorderLayout.SOUTH);    // insertion du logo

        JPanel searchPanel = new JPanel(new BorderLayout());    // Panneau qui contient la définition du choix du produit
        JLabel titleSearch = new JLabel("Catégorie Produit");   // titre
        Object[] categories = new Object[]{"Tous", "DVDs", "Livres", "Jeux Vidéo"};
        searchProduct      = new JComboBox(categories);         // input
        searchPanel.add(titleSearch, BorderLayout.NORTH);       // insertion
        searchPanel.add(searchProduct, BorderLayout.SOUTH);     // insertion

        JPanel buttonPanel = new JPanel();      // Panneau qui contient le bouton chercher
        searchBtn = new JButton("Chercher");    // bouton
        searchBtn.setActionCommand("searchCat");
        searchBtn.addActionListener(this);
        buttonPanel.add(searchBtn);             // insertion

        infoStore.add(infoPanel, FlowLayout.LEFT);
        infoStore.add(searchPanel, FlowLayout.CENTER);
        infoStore.add(buttonPanel, FlowLayout.RIGHT);
        center.add(infoStore);

        // INSERTION DE LA PARTIE PRODUIT DE L'APPLICATION
        JPanel productPanel      = new JPanel(new FlowLayout());    // Panneau qui contient liste + détail + image 
        JPanel infoProductsPanel = new JPanel(new BorderLayout());  // Panneau qui contient la list des produits
        JLabel titleListProducts = new JLabel("Les produits disponibles");  // text descriptif
        listProducts             = new JList();                     // liste de tous les produits
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listProducts.setVisibleRowCount(-1);
        JScrollPane listScroll = new JScrollPane(listProducts);     // contenu de la liste
        listScroll.setPreferredSize(new Dimension(100, 80));        // dimmension de la liste
        infoProductsPanel.add(titleListProducts, BorderLayout.NORTH);
        infoProductsPanel.add(listProducts, BorderLayout.SOUTH);
        productPanel.add(infoProductsPanel, FlowLayout.LEFT);
        productPanel.setBorder(BorderFactory.createTitledBorder("Produits"));
        
        JPanel infoOneProduct     = new JPanel(new BorderLayout());     // information d'un produit
        JLabel currentTitle       = new JLabel("Produit courant :");    // titre de la partie
        currentProduct            = new JPanel(new GridLayout(4, 1));   // Détail des caractéristiques du produit
        JLabel titleProduct       = new JLabel("Title");                // texte
        JLabel descriptionProduct = new JLabel("Description");          // texte
        JLabel priceProduct       = new JLabel("Prix");                 // texte
        JLabel quantityProduct    = new JLabel("Quantité");             // texte
        infoOneProduct.add(currentTitle, BorderLayout.NORTH);
        currentProduct.add(titleProduct);
        currentProduct.add(descriptionProduct);
        currentProduct.add(priceProduct);
        currentProduct.add(quantityProduct);
        infoOneProduct.add(currentProduct, BorderLayout.CENTER);
        productPanel.add(infoOneProduct);

        JPanel imgProductPanel = new JPanel(new BorderLayout());        // Panneau qui contient l'image
        imgProduct = new JLabel(new ImageIcon("files/product.jpg"));    // Chargement de l'image
        imgProductPanel.add(imgProduct);
        productPanel.add(imgProductPanel);
        center.add(productPanel);

        // INSERTION DE LA PARTIE CLIENT DE L'APPLICATION
        JPanel clientPanel = new JPanel(new FlowLayout());
        JPanel searchClientPlanel = new JPanel(new BorderLayout());
        JLabel titleClient = new JLabel("Client(nom)");
        searchClient = new JTextField(20);
        searchClientBtn = new JButton("Chercher");
        searchClientBtn.setActionCommand("searchClient");
        searchClientBtn.addActionListener(this);
        searchClientPlanel.add(titleClient, BorderLayout.NORTH);
        searchClientPlanel.add(searchClient, BorderLayout.CENTER);
        searchClientPlanel.add(searchClientBtn, BorderLayout.SOUTH);
        clientPanel.setBorder(BorderFactory.createTitledBorder("Clients"));
        clientPanel.add(searchClientPlanel, FlowLayout.LEFT);

        JPanel infoClientPanel   = new JPanel(new BorderLayout());
        JLabel infoCurrentClient = new JLabel("Informations du client courant");
        infoClient               = new JPanel(new GridLayout(4,1));
        JLabel surname           = new JLabel("NOM");
        JLabel firstname         = new JLabel("Prénom");
        JLabel mail              = new JLabel("example@example.fr");
        JLabel adresse           = new JLabel("XX nom de la rue CODE_POSTALE VILLE");
        infoClient.add(surname);
        infoClient.add(firstname);
        infoClient.add(mail);
        infoClient.add(adresse);
        infoClientPanel.add(infoCurrentClient, BorderLayout.NORTH);
        infoClientPanel.add(infoClient, BorderLayout.CENTER);
        clientPanel.add(infoClientPanel);
        center.add(clientPanel);

        // INSERTION DE LA PARTIE TRANSACTION
        JPanel transactionPanel = new JPanel(new FlowLayout());
        clientBuy = new JPanel(new BorderLayout());
        JLabel titleBuyer = new JLabel("Nom du client courant");
        JLabel nameBuyer  = new JLabel("NOM Prénom");
        clientBuy.add(titleBuyer, BorderLayout.NORTH);
        clientBuy.add(nameBuyer, BorderLayout.SOUTH);
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transactions"));
        transactionPanel.add(clientBuy);

        productBuy = new JPanel(new BorderLayout());
        JLabel nameArticle  = new JLabel("NOM DE L'ARTICLE");
        JLabel nbArticles = new JLabel("Nombre d'article : 0");
        productBuy.add(nameArticle, BorderLayout.NORTH);
        productBuy.add(nbArticles, BorderLayout.SOUTH);
        transactionPanel.add(productBuy);
        buyBtn = new JButton("Acheter");
        buyBtn.setActionCommand("buyProducts");
        buyBtn.addActionListener(this);
        transactionPanel.add(buyBtn);
        center.add(transactionPanel);

        JFrame frame = new JFrame("AmazonDuPauvre");
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(center, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "searchCat":
                    System.out.println("search Category");
                    System.out.println("catgegory : " + searchProduct.getSelectedItem());
                    store.loadStore(searchProduct.getSelectedItem().toString());
                    // code pour chercher la category du produit
                    // --> methode qui recherche les produits d'une category (= trie sur les category dans le fichier XML)
                    break;

                case "searchClient":
                    System.out.println("search client");
                    System.out.println("clientInput : " + searchClient.getText());
                    // code pour chercher le client à partir d'un nom ou prénom
                    // --> filtre des personnes stocker dans le fichier XML à partir de l'entré du texte
                    break;

                case "buyProducts":
                    System.out.println("buy products");
                    // conclusion de la transaction
                    // check que les différentes informations correspondent bien à une situation possible
                    // check des stock
                    // check du client
                    // check de l'existence du produit et du client
                    break;
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting project StoreGUI...");
        StoreGUI storeGUI = new StoreGUI();
    }
}