package javafx;

import src.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VueAcheterLivreMagasin extends BorderPane {

    private Label labelNomMagasin;
    private HBox zoneAffichageLivres;
    private Button boutonPrecedent;
    private Button boutonSuivant;
    private Commande commande; 

    private List<Livre> tousLesLivres;
    private int pageCourante = 0;
    private final int LIVRES_PAR_PAGE = 5;
    private Client client;
    private Magasin magasin;
    private ConnexionMySQL connexionMySQL; 
    /**
     * Constructeur de la classe VueAcheterLivreMagasin.
     * Affiche les livres disponibles pour achat en magasin.
     * @param client Le client connecté.
     * @param magasin Le magasin sélectionné.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param commande La commande en cours du client.
     */
    public VueAcheterLivreMagasin(Client client, Magasin magasin, ConnexionMySQL connexionMySQL, Commande commande) {
        super();

        this.tousLesLivres = LivreBD.chargerLivresParMagasin(connexionMySQL.getConnexion(), magasin.getIdmag());
        this.commande = commande;

        this.client = client;
        this.magasin = magasin;
        this.connexionMySQL = connexionMySQL; 
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #add8e6);");

        VBox sectionHaut = new VBox(10);
        sectionHaut.setAlignment(Pos.CENTER);
        sectionHaut.setPadding(new Insets(20, 0, 20, 0));

        labelNomMagasin = new Label(magasin.getNommag());
        labelNomMagasin.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-text-fill: #2c3e50;");
        Label bonjour = new Label("Choisissez vos livres pour " + client.getPrenom() + " " + client.getNom() + " :");
        bonjour.setStyle("-fx-font-size: 20px; -fx-text-fill: #34495e;");

        sectionHaut.getChildren().addAll(labelNomMagasin, bonjour);
        this.setTop(sectionHaut);

        zoneAffichageLivres = new HBox(30); 
        zoneAffichageLivres.setAlignment(Pos.CENTER);
        zoneAffichageLivres.setPadding(new Insets(20));
        this.setCenter(zoneAffichageLivres);

        HBox sectionBas = new HBox(30);
        sectionBas.setAlignment(Pos.CENTER);
        sectionBas.setPadding(new Insets(20));

        boutonPrecedent = new Button("Précédent");
        boutonSuivant = new Button("Suivant");
        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        Button deconnexion = new Button("Déconnexion");
        Utilitaire.styliserBoutonQuitter(deconnexion);
        deconnexion.setOnAction(new ControleurRetourAccueil());
        HBox hautSection = new HBox(10, deconnexion, quitter);
        BorderPane haut = new BorderPane();
        haut.setRight(hautSection);
        haut.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(haut);

        Utilitaire.styliserBouton(boutonPrecedent);
        Utilitaire.styliserBouton(boutonSuivant);
        boutonPrecedent.setOnAction(e -> {
            if (pageCourante > 0) {
                pageCourante--;
                afficherLivres();
            }
        });


        boutonSuivant.setOnAction(e -> {
            if ((pageCourante + 1) * LIVRES_PAR_PAGE < tousLesLivres.size()) {
                pageCourante++;
                afficherLivres();
            }
        });

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

        sectionBas.getChildren().addAll(boutonConsulterPanier, boutonVoirRecommandations, boutonPrecedent, boutonSuivant);
        this.setBottom(sectionBas);

        afficherLivres();
    }

    /**
     * Affiche les livres disponibles dans la zone d'affichage.
     * Gère la pagination pour afficher un nombre limité de livres par page.
     */
    private void afficherLivres() {
        zoneAffichageLivres.getChildren().clear();
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
     * @return Un VBox contenant les informations du livre.
     */
    private VBox creerCadreLivre(Livre livre) {
        VBox cadre = new VBox(8);
        cadre.setAlignment(Pos.TOP_LEFT); 
        
        cadre.setPrefSize(220, 300); 
        cadre.setMinSize(220, 300); 
        cadre.setMaxSize(220, 300);

        cadre.setPadding(new Insets(15));

        cadre.setStyle(
            "-fx-border-color: #bbdefb; " + 
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

        Label labelQte = new Label("Stock: " + livre.getQuantite());
        labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        if (livre.getQuantite() <= 5 && livre.getQuantite() > 0) {
            labelQte.setText("Stock limité: " + livre.getQuantite());
            labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12; -fx-font-weight: bold;");
        } else if (livre.getQuantite() == 0) {
            labelQte.setText("En rupture de stock");
            labelQte.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
        }

        VBox infosLivre = new VBox(5);
        infosLivre.setAlignment(Pos.TOP_LEFT);
        infosLivre.getChildren().addAll(labelNbPages, labelPrix, labelQte);

        Button boutonAcheter = new Button("Ajouter au panier");
        boutonAcheter.setPrefWidth(Double.MAX_VALUE);
        boutonAcheter.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        if (livre.getQuantite() == 0) {
            boutonAcheter.setDisable(true);
            boutonAcheter.setText("Indisponible");
            boutonAcheter.setStyle("-fx-font-size: 16px; -fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        }

        boutonAcheter.setOnAction(e -> {
            this.commande.ajouterLivreACommande(livre); 
            System.out.println("Livre ajouté : " + livre.getTitre());
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Ajout Confirmé");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Le livre '" + livre.getTitre() + "' a été ajouté à votre panier !");
            confirmation.showAndWait();
        });

        Region espace1 = new Region(); 
        VBox.setVgrow(espace1, Priority.SOMETIMES); 

        Region espace2 = new Region(); 
        VBox.setVgrow(espace2, Priority.ALWAYS); 

        cadre.getChildren().addAll(labelTitre, espace1, infosLivre, espace2, boutonAcheter);

        return cadre;
    }
    /**
     * Met à jour l'état des boutons de navigation (Précédent et Suivant).
     * Désactive le bouton Précédent si on est à la première page,
     * et désactive le bouton Suivant si on est à la dernière page.
     */
    private void mettreAJourBoutonsNavigation() {
        boutonPrecedent.setDisable(pageCourante == 0);
        boutonSuivant.setDisable((pageCourante + 1) * LIVRES_PAR_PAGE >= tousLesLivres.size());
    }

    /**
     * Crée une scène pour cette vue.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}