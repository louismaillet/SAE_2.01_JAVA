package javafx;

import src.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VuePasserCommande extends BorderPane {

    private Commande commande; 
    private Client client; 
    private ConnexionMySQL connexionMySQL;

    /**
     * Constructeur de la classe VuePasserCommande.
     * Permet à un client de choisir le mode de livraison pour sa commande.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     */
    public VuePasserCommande(Client client, ConnexionMySQL connexionMySQL) {
        super();
        this.commande = new Commande();
        try {
            this.commande.setNumcom(connexionMySQL.getConnexion());

        } catch (Exception e) {
        }
        this.client = client;
        this.connexionMySQL = connexionMySQL; 
        setPadding(new Insets(20));

        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");


        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());

        BorderPane topPane = new BorderPane();
        topPane.setRight(quitter);
        topPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(topPane);
        this.setPadding(new Insets(10));


        Label livraisonLabel = new Label("Quel mode de livraison souhaitez-vous ?");
        livraisonLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        Button boutonEnMagasin = new Button("Livraison en magasin");
        Button boutonADomicile = new Button("Livraison à domicile");
        Button boutonRetour = new Button("Retour");

        boutonRetour.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        Utilitaire.styliserBouton(boutonEnMagasin);
        Utilitaire.styliserBouton(boutonADomicile);

        boutonEnMagasin.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            this.commande.setModeDeReception(ModeReception.ENMAGASIN);
            VueChoisirMagasin vueChoisirMagasin = new VueChoisirMagasin(this.client, this.connexionMySQL, this.commande);
            Scene sceneChoisirMagasin = vueChoisirMagasin.creerScene(1440, 850);
            stage.setScene(sceneChoisirMagasin);

        });

        boutonADomicile.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            this.commande.setModeDeReception(ModeReception.LIVRAISON);
            VueAcheterLivreDomicile vueAcheterLivreDomicile = new VueAcheterLivreDomicile(this.client, this.connexionMySQL , this.commande);
            Scene sceneAcheterLivre = vueAcheterLivreDomicile.creerScene(1440, 850);
            stage.setScene(sceneAcheterLivre);
        });

        boutonRetour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            AccueilClient accueilClient = new AccueilClient(this.client, this.connexionMySQL);
            Scene sceneAccueilClient = accueilClient.creerScene(1440, 850);
            stage.setScene(sceneAccueilClient);
        });

        HBox choixLivraison = new HBox(20, boutonEnMagasin, boutonADomicile);
        choixLivraison.setPadding(new Insets(20));
        choixLivraison.setAlignment(Pos.CENTER);

        VBox vboxContenu = new VBox(20, livraisonLabel, choixLivraison, boutonRetour);
        vboxContenu.setPadding(new Insets(20));
        vboxContenu.setAlignment(Pos.CENTER);

        setCenter(vboxContenu);
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