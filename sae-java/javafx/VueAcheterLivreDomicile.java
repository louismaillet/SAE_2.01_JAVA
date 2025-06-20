package javafx;

import src.*; 

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color; 
import javafx.scene.text.TextAlignment; 
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class VueAcheterLivreDomicile extends BorderPane {

    private Label labelNomMagasin;
    private HBox zoneAffichageLivres;
    private Button boutonPrecedent;
    private Button boutonSuivant;

    private List<Livre> tousLesLivres;
    private int pageCourante = 0;
    private final int LIVRES_PAR_PAGE = 5; 
    private Client client;
    private Commande commande;
    private ConnexionMySQL connexionMySQL;

    /**
     * Constructeur de la classe VueAcheterLivreDomicile.
     * Affiche les livres disponibles pour achat à domicile.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param commande La commande en cours du client.
     */
    public VueAcheterLivreDomicile(Client client, ConnexionMySQL connexionMySQL, Commande commande) {
        super();
        this.client = client;
        this.connexionMySQL = connexionMySQL;
        this.commande = commande;
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #add8e6);");


        VBox sectionHaut = new VBox(10);
        sectionHaut.setAlignment(Pos.CENTER);
        sectionHaut.setPadding(new Insets(20, 0, 20, 0));

        labelNomMagasin = new Label("Livraison à Domicile");
        labelNomMagasin.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-text-fill: #2c3e50;");
        Label welcomeMessage = new Label("Choisissez vos livres pour " + client.getPrenom() + " " + client.getNom() + " :");
        welcomeMessage.setStyle("-fx-font-size: 20px; -fx-text-fill: #34495e;");

        sectionHaut.getChildren().addAll(labelNomMagasin, welcomeMessage);
        this.setTop(sectionHaut);

        zoneAffichageLivres = new HBox(20);
        zoneAffichageLivres.setAlignment(Pos.CENTER);
        zoneAffichageLivres.setPadding(new Insets(20));
        zoneAffichageLivres.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-color: #f9f9f9;");

        this.setCenter(zoneAffichageLivres);

        HBox sectionBas = new HBox(20);
        sectionBas.setAlignment(Pos.CENTER);
        sectionBas.setPadding(new Insets(20));

        boutonPrecedent = new Button("Précédent");
        boutonSuivant = new Button("Suivant");

        Utilitaire.styliserBouton(boutonPrecedent);
        Utilitaire.styliserBouton(boutonSuivant);

        
        boutonPrecedent.setOnAction(e -> {
            if (pageCourante > 0) {
                pageCourante--;
                afficherLivres();
            }
        });

        boutonSuivant.setOnAction(e -> {
            if (tousLesLivres != null && (pageCourante + 1) * LIVRES_PAR_PAGE < tousLesLivres.size()) {
                pageCourante++;
                afficherLivres();
            }
        });


        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        Button deconnexion = new Button("Déconnexion");
        Utilitaire.styliserBoutonQuitter(deconnexion);
        deconnexion.setOnAction(new ControleurRetourAccueil());

        BorderPane haut = new BorderPane();
        HBox hautSection = new HBox(10, deconnexion, quitter);
        haut.setRight(hautSection);
        haut.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(haut);

        Button boutonConsulterPanier = new Button("Consulter mon panier");
        Utilitaire.styliserBouton(boutonConsulterPanier);
        boutonConsulterPanier.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VueConsulterPanier vueConsulterPanier = new VueConsulterPanier(this.client, this.connexionMySQL, this.commande);
            Scene scene = vueConsulterPanier.creerScene(1440, 850);
            stage.setScene(scene);
        });

        Button boutonVoirRecommandations = new Button("Voir les recommandations");
        Utilitaire.styliserBouton(boutonVoirRecommandations);
        boutonVoirRecommandations.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VueRecommandations vueRecommandations = new VueRecommandations(this.client, this.connexionMySQL, this.commande);
            Scene scene = vueRecommandations.creerScene(1440, 850);
            stage.setScene(scene);
        });

        sectionBas.getChildren().addAll(boutonConsulterPanier, boutonVoirRecommandations, new Spacer(), boutonPrecedent, boutonSuivant, new Spacer());
        this.setBottom(sectionBas);

        chargerTousLesLivres();
        afficherLivres();
    }

    /**
     * Charge tous les livres disponibles depuis la base de données.
     * En cas d'erreur, affiche une alerte et initialise la liste des livres à vide.
     */
    private void chargerTousLesLivres() {
        try {
            this.tousLesLivres = LivreBD.chargerLivres(connexionMySQL.getConnexion());
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger les livres.");
            alert.setContentText("Une erreur est survenue lors de la connexion à la base de données ou de la récupération des livres: " + e.getMessage());
            alert.showAndWait();
            this.tousLesLivres = new ArrayList<>();
        }
    }
    /**
     * Affiche les livres dans la zone d'affichage.
     * Gère la pagination et l'affichage des livres disponibles.
     */
    private void afficherLivres() {
        zoneAffichageLivres.getChildren().clear();
        if (tousLesLivres == null || tousLesLivres.isEmpty()) {
            Label noBooksLabel = new Label("Aucun livre disponible pour le moment.");
            noBooksLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic; -fx-text-fill: #888888;");
            zoneAffichageLivres.getChildren().add(noBooksLabel);
            boutonPrecedent.setDisable(true);
            boutonSuivant.setDisable(true);
            return;
        }

        int debut = pageCourante * LIVRES_PAR_PAGE;
        int fin = Math.min(debut + LIVRES_PAR_PAGE, tousLesLivres.size());

        for (int i = debut; i < fin; i++) {
            zoneAffichageLivres.getChildren().add(creerCadreLivre(tousLesLivres.get(i)));
        }
        mettreAJourBoutonsNavigation();
    }
    /**
     * Crée un cadre pour afficher les informations d'un livre.
     * @param livre Le livre à afficher.
     * @return Un VBox contenant les informations du livre et un bouton pour l'ajouter au panier.
     */
    private VBox creerCadreLivre(Livre livre) {
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
        cadre.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.2)));

        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2c3e50;");
        labelTitre.setWrapText(true);
        labelTitre.setTextAlignment(TextAlignment.CENTER);
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);
        VBox.setVgrow(labelTitre, Priority.SOMETIMES); 

        Label labelNbPages = new Label(livre.getNbPages() + " pages");
        labelNbPages.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Label labelPrix = new Label(String.format("%.2f", livre.getPrix()) + " €");
        labelPrix.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #e74c3c;");

        Label labelQte = new Label(); 
        labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        VBox infosLivre = new VBox(5);
        infosLivre.setAlignment(Pos.TOP_LEFT);
        infosLivre.getChildren().addAll(labelNbPages, labelPrix, labelQte); 

        Button boutonAjouterPanier = new Button("Ajouter au panier");
        boutonAjouterPanier.setPrefWidth(Double.MAX_VALUE);
        boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        int stockActuel = 0;
        try {
            stockActuel = LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite();
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la vérification du stock initial pour " + livre.getTitre() + ": " + e.getMessage());
            stockActuel = 0;
        }


        if (stockActuel <= 5 && stockActuel > 0) {
            labelQte.setText("Stock limité: " + stockActuel);
            labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12; -fx-font-weight: bold;"); 
        } else if (stockActuel == 0) {
            labelQte.setText("En rupture de stock");
            labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;"); 
        } else {
            labelQte.setText("Stock: " + stockActuel);
            labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;"); 
        }

        if (stockActuel == 0) {
            boutonAjouterPanier.setDisable(true);
            boutonAjouterPanier.setText("Indisponible");
            boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        }

        boutonAjouterPanier.setOnAction(e -> {
            int nouveauStock = 0;
            try {
                nouveauStock = LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite();
            } catch (Exception ex) {
                System.err.println("Erreur SQL lors de la vérification finale du stock pour " + livre.getTitre() + ": " + ex.getMessage());
                nouveauStock = 0;
            }

            if (nouveauStock > 0) {
                int qtePanier = this.commande.getQuantiteLivreDansPanier(livre.getIsbn()); 
                
                if (qtePanier < nouveauStock) {
                    this.commande.ajouterLivreACommande(livre); 
                    System.out.println("Livre ajouté au panier : " + livre.getTitre());
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Ajout Confirmé");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("Le livre '" + livre.getTitre() + "' a été ajouté à votre panier !");
                    confirmation.showAndWait();

                    int stockAJour = 0;
                    try {
                        stockAJour = LivreBD.getLivreParISBN(connexionMySQL.getConnexion(), livre.getIsbn()).getQuantite();
                    } catch (Exception ex) {
                        System.err.println("Erreur SQL lors de la mise à jour du stock d'affichage pour " + livre.getTitre() + ": " + ex.getMessage());
                        stockAJour = 0;
                    }
                    
                    if (stockAJour <= 5 && stockAJour > 0) {
                        labelQte.setText("Stock limité: " + stockAJour);
                        labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12; -fx-font-weight: bold;");
                    } else if (stockAJour == 0) {
                        labelQte.setText("En rupture de stock");
                        labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
                        boutonAjouterPanier.setDisable(true);
                        boutonAjouterPanier.setText("Indisponible");
                        boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                    } else {
                        labelQte.setText("Stock: " + stockAJour);
                        labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                    }

                } else {
                    Alert alertStockVide = new Alert(Alert.AlertType.WARNING);
                    alertStockVide.setTitle("Stock Limité");
                    alertStockVide.setHeaderText(null);
                    alertStockVide.setContentText("Vous avez déjà le maximum de ce livre en stock (" + nouveauStock + ") ou le stock est insuffisant.");
                    alertStockVide.showAndWait();
                }
            } else {
                Alert alertErreur = new Alert(Alert.AlertType.ERROR);
                alertErreur.setTitle("Stock Insuffisant");
                alertErreur.setHeaderText(null);
                alertErreur.setContentText("Désolé, le livre '" + livre.getTitre() + "' n'est plus disponible.");
                alertErreur.showAndWait();
                boutonAjouterPanier.setDisable(true);
                boutonAjouterPanier.setText("Indisponible");
                boutonAjouterPanier.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                labelQte.setText("En rupture de stock");
                labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
            }
        });

        Region espace1 = new Region();
        VBox.setVgrow(espace1, Priority.SOMETIMES);

        Region espace2 = new Region();
        VBox.setVgrow(espace2, Priority.ALWAYS);

        cadre.getChildren().addAll(labelTitre, espace1, infosLivre, espace2, boutonAjouterPanier);

        return cadre;
    }
    /**
     * Met à jour l'état des boutons de navigation (Précédent/Suivant) en fonction de la page courante et du nombre total de livres.
     */
    private void mettreAJourBoutonsNavigation() {
        if (tousLesLivres == null || tousLesLivres.isEmpty()) {
            boutonPrecedent.setDisable(true);
            boutonSuivant.setDisable(true);
            return;
        }
        boutonPrecedent.setDisable(pageCourante == 0);
        boutonSuivant.setDisable((pageCourante + 1) * LIVRES_PAR_PAGE >= tousLesLivres.size());
    }

    
    /**
     * Méthode pour créer une scène pour la vue d'achat de livre à domicile.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
    /**
     * Classe interne pour créer un espace flexible dans la mise en page.
     * Utilisée pour séparer les boutons de navigation.
     */
    private static class Spacer extends Region {
        public Spacer() {
            setPrefWidth(20);
            HBox.setHgrow(this, Priority.ALWAYS);
        }
    }
}