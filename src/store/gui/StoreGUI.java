import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.*;

public class StoreGUI implements ActionListener {
    private Store store;

    // PANEL TOP
    private JPanel    infoStore;
    private JLabel    imgStore;
    private JComboBox searchProduct;
    private JButton   searchBtn;

    // PANEL PRODUCT
    private JPanel                   productPanel;
    private JPanel                   infoProductsPanel;
    private JPanel                   infoOneProduct;
    private JPanel                   currentProductPanel;
    private JPanel                   imgProductPanel;
    private JLabel                   titleListProducts;
    private JLabel                   imgProduct;
    private JLabel                   titleProduct;
    private JLabel                   descriptionProduct;
    private JLabel                   priceProduct;
    private JLabel                   quantityProduct;
    private JList<String>            listProducts;
    private DefaultListModel<String> listModel;
    private JScrollPane              listScroll;
    private Product                  currentProduct;
    private String                   currentProductName;
    private String                   strTitle;
    private String                   strDescription;
    private String                   strPrice;
    private String                   strQuantity;
    private int                      indexProduct;

    // PANEL CLIENT
    private JTextField searchNameClient;
    private JTextField searchSurnameClient;
    private JTextField searchEmailClient;
    private JTextField searchAddressClient;
    private JButton    searchClientBtn;
    private JButton    addClientBtn;
    private JPanel     infoClient;
    private JPanel     infoClientPanel;
    private Client     currentClient;
    private JLabel     surname;
    private JLabel     firstname;
    private JLabel     mail;
    private String     strFirstname;
    private String     strLastname;
    private String     strEmail; 

    // PANEL BUY
    private JLabel            nameBuyer;
    private JLabel            titleBuyer;
    private JLabel            nameArticle;
    private ArrayList<String> nb;
    private JLabel            nbArticles;
    private JPanel            clientBuy;
    private JPanel            productBuy;
    private JPanel            transactionPanel;
    private JButton           buyBtn; 
    private JComboBox         numberProduct;
    private JPanel            tmp;
    private String[]          number;

    /**
    * <h1> Object StoreGUI </h1>
    * This class creates the graphic interface
    * @since   10-01-2020
    */
    public StoreGUI() {
        store = new Store("Tous");

        JPanel center = new JPanel(new GridLayout(4, 1));
        
        // INSERTION DE LA PARTIE INFORMATION DE L'APPLICATION
        infoStore         = new JPanel(new FlowLayout());   // Définition de la ligne
        JPanel infoPanel  = new JPanel(new BorderLayout());  // Panneau qui contient logo + titre
        JLabel titleStore = new JLabel("MY MARKET PLACE"); // titre
        imgStore          = new JLabel(new ImageIcon("files/logo.png")); // logo
        infoPanel.add(titleStore, BorderLayout.SOUTH);  // insertion du titre
        infoPanel.add(imgStore, BorderLayout.CENTER);    // insertion du logo

        JPanel searchPanel = new JPanel(new BorderLayout());    // Panneau qui contient la définition du choix du produit
        JLabel titleSearch = new JLabel("Catégorie Produit");   // titre

        List<String> catl    = store.getCategories();
        Object[] objectArray = catl.toArray();
        String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
        searchProduct        = new JComboBox<>(stringArray);

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
        listModel         = new DefaultListModel<String>();
        store.updateProducts(searchProduct.getSelectedItem().toString());
        for (int i = 0; i < store.getProducts().size(); i++)
            listModel.addElement(store.getProducts().get(i).getName());

        currentProductName = store.getProducts().get(0).getName();
        listProducts = new JList<String>(listModel);
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listProducts.setVisibleRowCount(-1);
        listProducts.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    indexProduct       = listProducts.getAnchorSelectionIndex();
                    if (indexProduct >= 0) {
                        currentProductName = listProducts.getModel().getElementAt(indexProduct).toString();
                        updateProductInfo();
                    }
                } 
            }
        });
        listScroll = new JScrollPane(listProducts);     // contenu de la liste
        listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroll.setMaximumSize(new Dimension(500, 200));
        listScroll.setPreferredSize(new Dimension(500, 200));
        titleListProducts = new JLabel("Les produits disponibles");  // text descriptif

        infoProductsPanel.add(titleListProducts, BorderLayout.NORTH);
        infoProductsPanel.add(listScroll, BorderLayout.CENTER);

        productPanel.add(infoProductsPanel, FlowLayout.LEFT);
        productPanel.setBorder(BorderFactory.createTitledBorder("Produits"));

        strTitle            = "Titre produit";
        strDescription      = "Description";
        strPrice            = "Prix";
        strQuantity         = "Quantité";
        
        infoOneProduct      = new JPanel(new BorderLayout());     // information d'un produit
        JLabel currentTitle = new JLabel("Produit courant :");    // titre de la partie
        currentProductPanel = new JPanel(new GridLayout(4, 1));   // Détail des caractéristiques du produit
        titleProduct        = new JLabel(strTitle);                // texte
        descriptionProduct  = new JLabel(strDescription);          // texte
        priceProduct        = new JLabel(strPrice);                 // texte
        quantityProduct     = new JLabel(strQuantity);             // texte
        infoOneProduct.add(currentTitle, BorderLayout.NORTH);
        currentProductPanel.add(titleProduct);
        currentProductPanel.add(descriptionProduct);
        currentProductPanel.add(priceProduct);
        currentProductPanel.add(quantityProduct);
        infoOneProduct.add(currentProductPanel, BorderLayout.CENTER);
        infoOneProduct.setPreferredSize(new Dimension(400, 100));
        productPanel.add(infoOneProduct);

        imgProductPanel = new JPanel(new BorderLayout());        // Panneau qui contient l'image
        imgProduct      = new JLabel(new ImageIcon());    // Chargement de l'image
        imgProductPanel.add(imgProduct);
        productPanel.add(imgProductPanel);
        center.add(productPanel);

        // INSERTION DE LA PARTIE CLIENT DE L'APPLICATION
        JPanel clientPanel           = new JPanel(new GridLayout(1,4));
        JPanel addClientPanel        = new JPanel(new FlowLayout());
        JPanel searchClientPanel     = new JPanel(new GridLayout(4,2));
        JPanel searchBarNamePanel    = new JPanel(new BorderLayout());
        JPanel searchBarSurnamePanel = new JPanel(new BorderLayout());
        JPanel searchBarEmailPanel   = new JPanel(new BorderLayout());
        JPanel searchBarAddressPanel = new JPanel(new BorderLayout());
        JPanel voidPanel             = new JPanel();

        // Les barres de recherche
        currentClient             = new Client("Prénom", "Nom", "email", UUID.randomUUID());
        JLabel titleClientName    = new JLabel(currentClient.getFirstname());
        JLabel titleClientSurname = new JLabel(currentClient.getLastname());
        JLabel titleClientEmail   = new JLabel(currentClient.getEmail());
        searchNameClient          = new JTextField(20);
        searchSurnameClient       = new JTextField(20);
        searchEmailClient         = new JTextField(20);

        // Les Boutons "Ajouter" et "Rechercher"
        searchClientBtn = new JButton("Chercher");
        searchClientBtn.setActionCommand("searchClient");
        searchClientBtn.addActionListener(this);

        addClientBtn = new JButton("Ajouter");
        addClientBtn.setActionCommand("addClient");
        addClientBtn.addActionListener(this);
        addClientBtn.setEnabled(false);

        //La gestion des positions
        searchBarSurnamePanel.add(titleClientSurname,BorderLayout.NORTH);
        searchBarSurnamePanel.add(searchSurnameClient,BorderLayout.CENTER);
        searchClientPanel.add(searchBarSurnamePanel);

        searchBarNamePanel.add(titleClientName,BorderLayout.NORTH);
        searchBarNamePanel.add(searchNameClient,BorderLayout.CENTER);
        searchClientPanel.add(searchBarNamePanel);
        
        searchBarEmailPanel.add(titleClientEmail, BorderLayout.NORTH);
        searchBarEmailPanel.add(searchEmailClient, BorderLayout.CENTER);
        searchClientPanel.add(searchBarEmailPanel);
        
        addClientPanel.add(addClientBtn, FlowLayout.LEFT);
        addClientPanel.add(searchClientBtn, FlowLayout.CENTER);
        searchClientPanel.add(addClientPanel);

        clientPanel.setBorder(BorderFactory.createTitledBorder("Clients"));
        clientPanel.add(searchClientPanel);

        infoClientPanel          = new JPanel(new BorderLayout());
        JLabel infoCurrentClient = new JLabel("Informations du client courant : ");
        infoClient               = new JPanel(new GridLayout(4,1));
        strFirstname = "Prénom";
        strLastname  = "Nom";
        strEmail     = "example@example.fr";
        surname      = new JLabel(strLastname);
        firstname    = new JLabel(strFirstname);
        mail         = new JLabel(strEmail);
        infoClient.add(surname);
        infoClient.add(firstname);
        infoClient.add(mail);
        infoClientPanel.add(infoCurrentClient, BorderLayout.NORTH);
        infoClientPanel.add(infoClient, BorderLayout.CENTER);

        clientPanel.add(voidPanel);
        clientPanel.add(infoClientPanel);
        center.add(clientPanel);

        // INSERTION DE LA PARTIE TRANSACTION
        transactionPanel = new JPanel(new FlowLayout());
        clientBuy        = new JPanel(new BorderLayout());
        titleBuyer       = new JLabel("Nom du client courant");
        nameBuyer        = new JLabel(strLastname + " " + strFirstname);

        clientBuy.add(titleBuyer, BorderLayout.NORTH);
        clientBuy.add(nameBuyer, BorderLayout.SOUTH);
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transactions"));
        transactionPanel.add(clientBuy, FlowLayout.LEFT);

        productBuy    = new JPanel(new BorderLayout());
        nameArticle   = new JLabel(strTitle);
        nbArticles    = new JLabel("Nombre d'article :");

        nb            = new ArrayList<String>();
        number        = new String[] {"0"};
        numberProduct = new JComboBox<>(number);
        tmp           = new JPanel(new FlowLayout());

        productBuy.add(nameArticle, BorderLayout.NORTH);
        productBuy.add(tmp, BorderLayout.SOUTH);
        tmp.add(nbArticles);
        tmp.add(numberProduct);

        transactionPanel.add(productBuy, FlowLayout.CENTER);
        buyBtn = new JButton("Acheter");
        buyBtn.setActionCommand("buyProducts");
        buyBtn.addActionListener(this);
        buyBtn.setEnabled(false);
        transactionPanel.add(buyBtn, FlowLayout.RIGHT);
        center.add(transactionPanel);


        JFrame frame = new JFrame("AmazonDuPauvre");
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(center, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method do an update of the back and front end according to button pressed 
     * @param e action effectuer lors d'un appuie de bouton
     */
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "searchCat":
                    updateProductsList();
                    break;

                case "searchClient":
                    updateClientInfo();
                    break;

                case "addClient":
                    if (searchNameClient.getText().length() > 0 && searchSurnameClient.getText().length() > 0 && searchEmailClient.getText().length() > 0)
                        store.addClient(searchNameClient.getText(), searchSurnameClient.getText(), searchEmailClient.getText());
                    else 
                        addClientBtn.setEnabled(false);
                    break;

                case "buyProducts":
                    Transaction t = new Transaction(currentClient.getId(), currentProduct.getId(), Integer.valueOf(numberProduct.getSelectedItem().toString()), LocalDateTime.now().toString());
                    if (Integer.valueOf(numberProduct.getSelectedItem().toString()) <= currentProduct.getStock()) {
                        store.addTransaction(t);
                        store.decreaseProduct(currentProduct, Integer.valueOf(numberProduct.getSelectedItem().toString()));
                        updateProductInfo();
                    }
                    // store.getTransactions();
                    break;
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    /**
     * This method update the list of products according to the selected category
     */
    private void updateProductsList() {
        infoProductsPanel.removeAll();
        listModel = new DefaultListModel<String>();
        store.updateProducts(searchProduct.getSelectedItem().toString());
        for (int i = 0; i < store.getProducts().size(); i++) 
            listModel.addElement(store.getProducts().get(i).getName());
        listProducts = new JList<String>(listModel);
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listProducts.setVisibleRowCount(-1);
        listProducts.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    indexProduct       = listProducts.getAnchorSelectionIndex();
                    if (indexProduct >= 0) {
                        currentProductName = listProducts.getModel().getElementAt(indexProduct).toString();
                        updateProductInfo();
                    }
                } 
            }
        });
        listScroll   = new JScrollPane(listProducts);
        listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroll.setMaximumSize(new Dimension(500, 100));
        listScroll.setPreferredSize(new Dimension(500, 100));
        listScroll.updateUI();
        infoProductsPanel.add(titleListProducts, BorderLayout.NORTH);
        infoProductsPanel.add(listScroll, BorderLayout.CENTER);
        infoProductsPanel.updateUI();
    }

    /**
     * This method update the current product's features
     */
    private void updateProductInfo() {
        currentProductPanel.removeAll();
        productBuy.removeAll();
        imgProductPanel.removeAll();
        currentProduct     = getCurrentProduct(currentProductName);
        strTitle           = currentProduct.getName();
        strPrice           = String.valueOf(currentProduct.getPrice());
        strQuantity        = String.valueOf(currentProduct.getStock());
        titleProduct       = new JLabel(strTitle);                  // texte
        priceProduct       = new JLabel(strPrice + "€");            // texte
        quantityProduct    = new JLabel(strQuantity + " unité(s)"); // texte
        currentProductPanel.add(titleProduct);
        currentProductPanel.add(priceProduct);
        currentProductPanel.add(quantityProduct);
        imgProductPanel = new JPanel(new BorderLayout());        // Panneau qui contient l'image
        imgProduct      = new JLabel(new ImageIcon(currentProduct.getImage()));    // Chargement de l'image
        imgProductPanel.add(imgProduct);
        productPanel.add(imgProductPanel);
        
        infoOneProduct.updateUI();
        productPanel.updateUI();

        for (int i = 0; i < currentProduct.getStock(); i++) nb.add(String.valueOf(i+1));
        number        = new String[nb.size()];
        for (int i = 0; i < number.length; i++) number[i] = nb.get(i);
        numberProduct = new JComboBox<>(number);
        tmp           = new JPanel(new FlowLayout());
        nameArticle   = new JLabel(strTitle);

        productBuy.add(nameArticle, BorderLayout.NORTH);
        productBuy.add(tmp, BorderLayout.SOUTH);
        tmp.add(nbArticles);
        tmp.add(numberProduct);
        transactionPanel.add(productBuy, FlowLayout.CENTER);
        productBuy.updateUI();

        if (!currentClient.getLastname().equals("Nom") && !currentClient.getFirstname().equals("Prénom") && !currentClient.getEmail().equals("Aucun résultat") && !currentProduct.getName().equals("Sans nom"))
            buyBtn.setEnabled(true);

    }

    /**
     * This method update the client information
     */
    private void updateClientInfo() {
        infoClient.removeAll();
        clientBuy.removeAll();
        currentClient = store.getClient(searchNameClient.getText(), searchSurnameClient.getText());
        if (currentClient.getLastname().equals("Nom") && currentClient.getFirstname().equals("Prénom") && currentClient.getEmail().equals("Aucun résultat"))
            if (searchNameClient.getText().length() > 0 && searchSurnameClient.getText().length() > 0 && searchEmailClient.getText().length() > 0) 
                addClientBtn.setEnabled(true); 
        else {
            addClientBtn.setEnabled(false);
            buyBtn.setEnabled(false);
        } 
        
        strFirstname  = currentClient.getFirstname();
        strLastname   = currentClient.getLastname();
        strEmail      = currentClient.getEmail();
        surname       = new JLabel(strLastname);
        firstname     = new JLabel(strFirstname);
        mail          = new JLabel(strEmail);
        infoClient.add(surname);
        infoClient.add(firstname);
        infoClient.add(mail);
        infoClient.updateUI();
        infoClientPanel.updateUI();

        if (!currentClient.getLastname().equals("Nom") && !currentClient.getFirstname().equals("Prénom") && !currentClient.getEmail().equals("Aucun résultat") && currentProduct != null) 
            buyBtn.setEnabled(true);
        

        nameBuyer = new JLabel(strLastname + " " + strFirstname);
        clientBuy.add(titleBuyer, BorderLayout.NORTH);
        clientBuy.add(nameBuyer, BorderLayout.SOUTH);
        transactionPanel.add(clientBuy, FlowLayout.LEFT);
        clientBuy.updateUI();
        transactionPanel.updateUI();
    }

    /**
     * This method enable to get the current product that we need from the name
     */
    private Product getCurrentProduct(String name) {
        Product product = new Product("Sans nom", 0, UUID.randomUUID(), 0, "/");
        for (int i = 0; i < store.getProducts().size(); i++) {
            if (name.equals(store.getProducts().get(i).getName())) 
                return store.getProducts().get(i);
        }
        return product;
    }
    
    public static void main(String[] args) {
        System.out.println("Starting project StoreGUI...");
        new StoreGUI();
    }
}