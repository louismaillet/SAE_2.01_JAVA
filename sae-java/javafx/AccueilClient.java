package javafx;
import src.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccueilClient extends BorderPane {
    private Client client;
    private ConnexionMySQL connexionMySQL;

    /**
     * Constructeur de la classe AccueilClient.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     */
    public AccueilClient(Client client, ConnexionMySQL connexionMySQL) {
        super();
        this.client = client;
        this.connexionMySQL = connexionMySQL;
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        Label bonjour = new Label("Bonjour " + client.getPrenom() + " " + client.getNom() + " !");
        Button boutonQuitter = new Button("Quitter");

        boutonQuitter.setOnAction(new ControleurQuitter());
        Utilitaire.styliserBoutonQuitter(boutonQuitter);
        bonjour.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        
        BorderPane haut = new BorderPane();
        haut.setCenter(bonjour);  
        haut.setRight(boutonQuitter);
        this.setTop(haut);
        BorderPane.setAlignment(bonjour, Pos.TOP_CENTER);

        VBox milieu = new VBox(50);
        milieu.setPadding(new Insets(70));
        milieu.setAlignment(Pos.CENTER);
        milieu.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        Button demarrerCommande = new Button("Démarrer ma commande");
        Utilitaire.styliserBouton(demarrerCommande);
        demarrerCommande.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VuePasserCommande vuePasserCommande = new VuePasserCommande(this.client, this.connexionMySQL);
            Scene scene = vuePasserCommande.creerScene(1440, 850);
            stage.setScene(scene);
        });
        Button deconnexion = new Button("Déconnexion");
        Utilitaire.styliserBoutonQuitter(deconnexion);
        deconnexion.setOnAction(new ControleurRetourAccueil());

        milieu.getChildren().addAll(demarrerCommande, deconnexion);
        this.setCenter(milieu);
        this.setPadding(new Insets(70));

    }

    /**
     * Crée une scène pour l'accueil du client.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */

    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}