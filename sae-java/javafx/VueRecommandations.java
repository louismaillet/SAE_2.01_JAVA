package javafx;

import src.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class VueRecommandations extends BorderPane {

    private Client client;
    private ConnexionMySQL connexionMySQL;
    private Commande commande;
    private VBox contenuRecommandations;

    /**
     * Constructeur de la classe VueRecommandations.
     * Permet d'afficher les recommandations de livres pour un client.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param commande La commande en cours du client.
     */
    public VueRecommandations(Client client, ConnexionMySQL connexionMySQL, Commande commande) {
        super();
        this.client = client;
        this.connexionMySQL = connexionMySQL;
        this.commande = commande;

        setPadding(new Insets(20));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #add8e6);");

        Label titreLabel = new Label("Recommandations pour " + client.getPrenom() + " " + client.getNom() + " !");
        titreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px; -fx-text-fill: #2c3e50;");
        setTop(titreLabel);
        BorderPane.setAlignment(titreLabel, Pos.TOP_CENTER);

        contenuRecommandations = new VBox(20);
        contenuRecommandations.setAlignment(Pos.TOP_CENTER);
        contenuRecommandations.setPadding(new Insets(20));
        contenuRecommandations.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");
        contenuRecommandations.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        
        chargerEtAfficherRecommandations();

        setCenter(contenuRecommandations);

        Button boutonRetour = new Button("Retour aux achats");
        Utilitaire.styliserBoutonRetour(boutonRetour);

        boutonRetour.setOnAction(e -> { 
            Stage stage = (Stage) this.getScene().getWindow();
            if (this.commande.getModeDeReception() == ModeReception.ENMAGASIN) {
                VueAcheterLivreMagasin vueAcheterLivre = new VueAcheterLivreMagasin(this.client, this.commande.getMagasin(), this.connexionMySQL, this.commande);
                Scene scene = vueAcheterLivre.creerScene(1440, 850);
                stage.setScene(scene);
            } else {
                VueAcheterLivreDomicile vueAcheterLivre = new VueAcheterLivreDomicile(this.client, this.connexionMySQL, this.commande);
                Scene scene = vueAcheterLivre.creerScene(1440, 850);
                stage.setScene(scene);
            }
        }); 

        VBox bas = new VBox(20, boutonRetour);
        bas.setAlignment(Pos.CENTER);
        bas.setPadding(new Insets(20));
        setBottom(bas);
    }

    /**
     * Charge les recommandations de livres pour le client et les affiche dans la vue.
     */
    private void chargerEtAfficherRecommandations() {
        contenuRecommandations.getChildren().clear();

        try {
            List<Livre> recommandations = ClientBD.onVousRecommande(connexionMySQL.getConnexion(), client);

            if (recommandations.isEmpty()) {
                Label recommandationLabel = new Label("Aucune recommandation disponible pour le moment.");
                recommandationLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic; -fx-text-fill: #888888;");
                contenuRecommandations.getChildren().add(recommandationLabel);
            } else {
                Label titreLabel = new Label("Voici des livres qui pourraient vous intéresser :");
                titreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #34495e; -fx-padding: 0 0 10 0;");
                contenuRecommandations.getChildren().add(titreLabel);

                HBox conteneurLivres = new HBox(30);
                conteneurLivres.setAlignment(Pos.CENTER);
                int i = 0;
                
                for (Livre livre : recommandations) {
                    if (LivreBD.chargerLivresParMagasin(connexionMySQL.getConnexion(),commande.getMagasin().getIdmag()).contains(livre) && commande.getModeDeReception() == ModeReception.ENMAGASIN && i < 5){
                        conteneurLivres.getChildren().add(creerCadreLivreRecommande(livre));
                        i++;
                    }
                    else if(i < 5 && commande.getModeDeReception() == ModeReception.LIVRAISON && livre.getQuantite() > 0){
                        List<Magasin> magasins = MagasinBD.chargerMagasins(connexionMySQL.getConnexion());
                        for (Magasin magasin : magasins) {
                            if (LivreBD.chargerLivresParMagasin(connexionMySQL.getConnexion(), magasin.getIdmag()).contains(livre)) {
                                livre.setQuantite(LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite());
                                conteneurLivres.getChildren().add(creerCadreLivreRecommande(livre));
                                i++;
                                break;
                            }
                        }
                        
                    }
                }


                
                contenuRecommandations.getChildren().add(conteneurLivres);
            }
        } catch (SQLException e) {
            Label erreurLabel = new Label("Erreur lors du chargement des recommandations: " + e.getMessage());
            erreurLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            contenuRecommandations.getChildren().add(erreurLabel);
            e.printStackTrace();
        }
    }

    /**
     * Crée un cadre pour afficher les informations d'un livre recommandé.
     * @param livre Le livre à afficher.
     * @return Un VBox contenant les informations du livre.
     */
    private VBox creerCadreLivreRecommande(Livre livre) {
        VBox cadre = new VBox(8);
        cadre.setAlignment(Pos.TOP_LEFT);
        
        cadre.setPrefSize(220, 300);
        cadre.setMinSize(220, 300);
        cadre.setMaxSize(220, 300);

        cadre.setPadding(new Insets(15));
        cadre.setStyle(
            "-fx-border-color: #a2d2ff; " + 
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 10px; " +
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 10px;"
        );
        cadre.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.2))); // Ajout d'une ombre

        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2c3e50;");
        labelTitre.setWrapText(true);
        labelTitre.setTextAlignment(TextAlignment.CENTER);
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);
        VBox.setVgrow(labelTitre, Priority.SOMETIMES); 

        Label nbPagesLabel = new Label(livre.getNbPages() + " pages");
        nbPagesLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Label prixLabel = new Label(String.format("%.2f", livre.getPrix()) + " €");
        prixLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #e74c3c;");

        Label qteLabel = new Label(); 
        qteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        VBox infosLivre = new VBox(5);
        infosLivre.setAlignment(Pos.TOP_LEFT);
        infosLivre.getChildren().addAll(nbPagesLabel, prixLabel, qteLabel); 

        Button boutonAjouterPanier = new Button("Ajouter au panier");
        boutonAjouterPanier.setPrefWidth(Double.MAX_VALUE);
        boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        int stockActuel = 0;
        try {
            if (this.commande.getModeDeReception() == ModeReception.ENMAGASIN) {
                stockActuel = MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), this.commande.getMagasin().getIdmag(), livre.getIsbn());
            } else {
                stockActuel = LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite();
            }
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la vérification du stock pour " + livre.getTitre() + ": " + e.getMessage());
            stockActuel = 0;
        }

        if (stockActuel <= 5 && stockActuel > 0) {
            qteLabel.setText("Stock limité: " + stockActuel);
            qteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12; -fx-font-weight: bold;"); 
        } else if (stockActuel == 0) {
            qteLabel.setText("En rupture de stock");
            qteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;"); 
            boutonAjouterPanier.setDisable(true);
            boutonAjouterPanier.setText("Indisponible");
            boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        } else {
            qteLabel.setText("Stock: " + stockActuel);
            qteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;"); 
        }

        boutonAjouterPanier.setOnAction(e -> {
            int nouveauStock = 0;
            try {
                if (this.commande.getModeDeReception() == ModeReception.ENMAGASIN) {
                        nouveauStock = MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), this.commande.getMagasin().getIdmag(), livre.getIsbn());
                } else {
                        nouveauStock = LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite();
                }
            } catch (Exception ex) {
                System.err.println("Erreur SQL lors de la vérification du stock pour " + livre.getTitre() + ": " + ex.getMessage());
                nouveauStock = 0;       
            }

            if (nouveauStock > 0) {
                int qtePanier = 0; 
                if (qtePanier < nouveauStock) {
                    this.commande.ajouterLivreACommande(livre); 
                    System.out.println("Livre recommandé ajouté au panier : " + livre.getTitre());
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Ajout Confirmé");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("Le livre recommandé '" + livre.getTitre() + "' a été ajouté à votre panier !");
                    confirmation.showAndWait();
                } else {
                    Alert alertMaxQte = new Alert(Alert.AlertType.WARNING);
                    alertMaxQte.setTitle("Stock Limité");
                    alertMaxQte.setHeaderText(null);
                    alertMaxQte.setContentText("Vous avez déjà le maximum de ce livre en stock (" + nouveauStock + ") dans votre panier.");
                    alertMaxQte.showAndWait();
                }
            } else {
                Alert erreurStockVide = new Alert(Alert.AlertType.ERROR);
                erreurStockVide.setTitle("Stock Insuffisant");
                erreurStockVide.setHeaderText(null);
                erreurStockVide.setContentText("Désolé, le livre '" + livre.getTitre() + "' n'est plus disponible.");
                erreurStockVide.showAndWait();
                boutonAjouterPanier.setDisable(true);
                boutonAjouterPanier.setText("Indisponible");
                boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                qteLabel.setText("En rupture de stock");
                qteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
            }
        });

        Region espace1 = new Region(); // permet de créer un espace 
        VBox.setVgrow(espace1, Priority.SOMETIMES);

        Region espace2 = new Region();
        VBox.setVgrow(espace2, Priority.ALWAYS);

        cadre.getChildren().addAll(labelTitre, espace1, infosLivre, espace2, boutonAjouterPanier);

        return cadre;
    }

    /**
     * Crée une scène pour la vue des recommandations.
     * @param largeur La largeur de la scène.
     * @param doubleHauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double doubleHauteur) { 
        return new Scene(this, largeur, doubleHauteur);
    }
}