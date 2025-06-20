package javafx;

import src.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VueMenuApresCommande extends BorderPane {

    private Client client;
    private String nomMagasinChoisi; 
    private ConnexionMySQL connexionMySQL;
    private Commande commande;

    public VueMenuApresCommande(Client client, String nomMagasinChoisi, ConnexionMySQL connexionMySQL, Commande commande) {
        super();
        this.client = client; 
        this.nomMagasinChoisi = nomMagasinChoisi;
        this.connexionMySQL = connexionMySQL;
        this.commande = commande; 

        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        Label confirmationLabel = new Label("Votre commande a été passée avec succès !");
        confirmationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        Label recapLabel = new Label("Récapitulatif : " + nomMagasinChoisi);
        recapLabel.setStyle("-fx-font-size: 16px;");

        Button boutonRetourAccueil = new Button("Retour à l'Accueil Client");
        boutonRetourAccueil.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            AccueilClient accueilClient = new AccueilClient(this.client, this.connexionMySQL);
            Scene scene = accueilClient.creerScene(1440, 850);
            stage.setScene(scene);
        });

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        Utilitaire.styliserBoutonRetour(boutonRetourAccueil);
        quitter.setOnAction(new ControleurQuitter());

        BorderPane topPane = new BorderPane();
        topPane.setRight(quitter);
        topPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(topPane);

        Button boutonConsulterPanier = new Button("Consulter mon Panier");
        boutonConsulterPanier.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VueConsulterPanier vueConsulterPanier = new VueConsulterPanier(this.client, this.connexionMySQL, this.commande);
            Scene scene = vueConsulterPanier.creerScene(1440, 850);
            stage.setScene(scene);
        });

        Button boutonVoirRecommandations = new Button("Voir les recommandations");
        boutonVoirRecommandations.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VueRecommandations vueRecommandations = new VueRecommandations(this.client, this.connexionMySQL, this.commande);
            Scene scene = vueRecommandations.creerScene(1440, 850);
            stage.setScene(scene);
        });


        VBox vboxContenu = new VBox(20, confirmationLabel, recapLabel, boutonConsulterPanier, boutonVoirRecommandations, boutonRetourAccueil);
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