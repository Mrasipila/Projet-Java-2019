import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class StoreGUI implements ActionListener {
    private Store store;

    // PANEL TOP
    private JPanel    infoStore;
    private JLabel    imgStore;
    private JComboBox searchProduct;
    private JButton   searchBtn;

    // PANEL PRODUCT
    private JPanel           productPanel;
    private JPanel           infoProductsPanel;
    private JPanel           infoOneProduct;
    private JPanel           currentProductPanel;
    private JLabel           imgProduct;
    private JLabel           titleProduct;
    private JLabel           descriptionProduct;
    private JLabel           priceProduct;
    private JLabel           quantityProduct;
    private JList            listProducts;
    private DefaultListModel listModel;
    private JScrollPane      listScroll;
    private Product          currentProduct;
    private String           currentProductName;
    private String           strTitle;
    private String           strDescription;
    private String           strPrice;
    private String           strQuantity;

    // PANEL CLIENT
    private JTextField searchNameClient;
    private JTextField searchSurnameClient;
    private JButton    searchClientBtn;
    private JButton    addClientBtn;
    private JPanel     infoClient;

    // PANEL BUY
    private JPanel  clientBuy;
    private JPanel  productBuy;
    private JButton buyBtn;

    public StoreGUI() {
        store = new Store("Tous");

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
        productPanel      = new JPanel(new FlowLayout());    // Panneau qui contient liste + détail + image 
        infoProductsPanel = new JPanel(new BorderLayout());  // Panneau qui contient la list des produits
        listModel         = new DefaultListModel();
        // updateProductsList();
        store.updateProducts(searchProduct.getSelectedItem().toString());
        for (int i = 0; i < store.getProducts().size(); i++) 
            listModel.addElement(store.getProducts().get(i).getName());
        listProducts = new JList(listModel);
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listProducts.setVisibleRowCount(-1);
        listScroll = new JScrollPane(listProducts);     // contenu de la liste
        listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroll.setMaximumSize(new Dimension(40, 90));
        listScroll.setPreferredSize(new Dimension(40, 90));
        listProducts.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // currentProductName = listProducts.getSelectedValuesList();
                    int indexProduct   = listProducts.getAnchorSelectionIndex();
                    currentProductName = listProducts.getModel().getElementAt(indexProduct).toString();
                    System.out.println(currentProductName);
                    updateProductInfo();
                } 
            }
        });
        // listModel.removeAllElements();
        JLabel titleListProducts = new JLabel("Les produits disponibles");  // text descriptif

        infoProductsPanel.add(titleListProducts, BorderLayout.NORTH);
        infoProductsPanel.add(listScroll, BorderLayout.CENTER);

        productPanel.add(infoProductsPanel, FlowLayout.LEFT);
        productPanel.setBorder(BorderFactory.createTitledBorder("Produits"));

        strTitle                  = "Titre produit";
        strDescription            = "Description";
        strPrice                  = "Prix";
        strQuantity               = "Quantité";
        
        infoOneProduct            = new JPanel(new BorderLayout());     // information d'un produit
        JLabel currentTitle       = new JLabel("Produit courant :");    // titre de la partie
        currentProductPanel       = new JPanel(new GridLayout(4, 1));   // Détail des caractéristiques du produit
        titleProduct       = new JLabel(strTitle);                // texte
        descriptionProduct = new JLabel(strDescription);          // texte
        priceProduct       = new JLabel(strPrice);                 // texte
        quantityProduct    = new JLabel(strQuantity);             // texte
        infoOneProduct.add(currentTitle, BorderLayout.NORTH);
        currentProductPanel.add(titleProduct);
        currentProductPanel.add(descriptionProduct);
        currentProductPanel.add(priceProduct);
        currentProductPanel.add(quantityProduct);
        infoOneProduct.add(currentProductPanel, BorderLayout.CENTER);
        productPanel.add(infoOneProduct);

        JPanel imgProductPanel = new JPanel(new BorderLayout());        // Panneau qui contient l'image
        imgProduct = new JLabel(new ImageIcon("files/product.jpg"));    // Chargement de l'image
        imgProductPanel.add(imgProduct);
        productPanel.add(imgProductPanel);
        center.add(productPanel);

        // INSERTION DE LA PARTIE CLIENT DE L'APPLICATION
        JPanel clientPanel = new JPanel(new GridLayout(1,3));
        JPanel addClientPanel = new JPanel(new FlowLayout());
        JPanel searchClientPanel = new JPanel(new GridLayout(3,1));
        JPanel searchBarNamePanel = new JPanel(new BorderLayout());
        JPanel searchBarSurnamePanel = new JPanel(new BorderLayout());
        JPanel voidPanel = new JPanel();

        // Les barres de recherche
        JLabel titleClientName = new JLabel("Client(name)");
        searchNameClient = new JTextField(20);
        JLabel titleClientSurname = new JLabel("Client(surname)");
        searchSurnameClient = new JTextField(20);

        // Les Boutons "Ajouter" et "Rechercher"
        searchClientBtn = new JButton("Chercher");
        searchClientBtn.setActionCommand("searchClient");
        searchClientBtn.addActionListener(this);

        addClientBtn = new JButton("Ajouter");
        addClientBtn.setActionCommand("addClient");
        addClientBtn.addActionListener(this);

        //La gestion des positions
        searchClientPanel.add(searchBarSurnamePanel);
        searchBarSurnamePanel.add(titleClientSurname,BorderLayout.NORTH);
        searchBarSurnamePanel.add(searchSurnameClient,BorderLayout.SOUTH);

        searchClientPanel.add(searchBarNamePanel);
        searchBarNamePanel.add(titleClientName,BorderLayout.NORTH);
        searchBarNamePanel.add(searchNameClient,BorderLayout.SOUTH);

        searchClientPanel.add(addClientPanel);
        addClientPanel.add(addClientBtn, FlowLayout.LEFT);
        addClientPanel.add(searchClientBtn, FlowLayout.CENTER);

        clientPanel.setBorder(BorderFactory.createTitledBorder("Clients"));
        clientPanel.add(searchClientPanel);
        //clientPanel.add(addClientPanel, FlowLayout.CENTER);

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

        clientPanel.add(voidPanel);
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
        JLabel nbArticles = new JLabel("Nombre d'article :");

        Object[] number = new Object[]{"0", "1", "2", "3", "4", "5"};
        JComboBox numberProduct = new JComboBox(number);         // input
        JPanel tmp = new JPanel(new FlowLayout());

        productBuy.add(nameArticle, BorderLayout.NORTH);
        productBuy.add(tmp, BorderLayout.SOUTH);
        tmp.add(nbArticles);
        tmp.add(numberProduct);

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
            // System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "searchCat":
                    System.out.println("search Category");
                    System.out.println("catgegory : " + searchProduct.getSelectedItem());
                    updateProductsList();
                    // code pour chercher la category du produit
                    // --> methode qui recherche les produits d'une category (= trie sur les category dans le fichier XML)
                    break;

                case "searchClient":
                    System.out.println("search client");
                    // System.out.println("clientInput : " + searchClient.getText());
                    
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

                // System.out.println(listProducts.getSelectedIndex());
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    private void updateProductsList() {
        listModel.clear();
        store.updateProducts(searchProduct.getSelectedItem().toString());
        for (int i = 0; i < store.getProducts().size(); i++) 
            listModel.addElement(store.getProducts().get(i).getName());
        // listProducts = new JList(listModel);
        // listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        // listProducts.setVisibleRowCount(-1);
        // listModel.updateUI();
        
        // listScroll = new JScrollPane(listProducts);     // contenu de la liste
        // listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // listScroll.setMaximumSize(new Dimension(40, 90));
        // listScroll.setPreferredSize(new Dimension(40, 90));
        listScroll.updateUI();
        infoProductsPanel.updateUI();

        System.out.println("listModel    : " + listModel);
        System.out.println("\nlistProducts : " + listProducts);
        System.out.println("\nlistScroll   : " + listScroll);
    }

    private void updateProductInfo() {
        currentProductPanel.removeAll();
        System.out.println("currentProductName : " + currentProductName);
        currentProduct = getCurrentProduct(currentProductName);
        System.out.println(currentProduct);
        strTitle    = currentProduct.getName();
        strPrice    = String.valueOf(currentProduct.getPrice());
        strQuantity = String.valueOf(currentProduct.getStock());
        titleProduct       = new JLabel(strTitle);                // texte
        priceProduct       = new JLabel(strPrice + "€");                 // texte
        quantityProduct    = new JLabel(strQuantity + " unité(s)");             // texte
        currentProductPanel.add(titleProduct);
        currentProductPanel.add(priceProduct);
        currentProductPanel.add(quantityProduct);
        // currentProductName.updateUI();
        infoOneProduct.updateUI();
        productPanel.updateUI();
        currentProduct.printProduct();
    }

    private Product getCurrentProduct(String name) {
        Product product = new Product("Sans nom", 0, UUID.randomUUID(), 1, "/");
        for (int i = 0; i < store.getProducts().size(); i++) {
            if (name == store.getProducts().get(i).getName()) 
                return store.getProducts().get(i);
        }
        return product;
    }
    

    public static void main(String[] args) {
        System.out.println("Starting project StoreGUI...");
        new StoreGUI();
    }
}