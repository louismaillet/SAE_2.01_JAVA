package javafx;

import src.*; 
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccueilAdmin extends BorderPane {
    private ConnexionMySQL connexionMySQL;

    /**
     * Constructeur de la classe AccueilAdmin.
     * Crée la page d'accueil pour l'administrateur avec les options.
     *
     * @param connexionMySQL La connexion à la base de données MySQL.
     */
    public AccueilAdmin(ConnexionMySQL connexionMySQL) {
        super();
        this.connexionMySQL = connexionMySQL; 

        this.setPadding(new Insets(50));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        Label titre = new Label("Tableau de bord Administrateur");
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 3connexionMySQL2px; -fx-text-fill: #2c3e50;");
        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        BorderPane topPane = new BorderPane();
        topPane.setCenter(titre);
        topPane.setRight(quitter);
        topPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(topPane);

        Label bonjour = new Label("Bienvenue, Administrateur !");
        bonjour.setStyle("-fx-font-size: 30px; -fx-text-fill: #34495e;");

        VBox optionAdmin = new VBox(20);
        optionAdmin.setAlignment(Pos.CENTER);
        optionAdmin.setPadding(new Insets(30));
        optionAdmin.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-border-radius: 10px;");
        optionAdmin.setStyle("-fx-background-color: #fffffff;");


        Button voirVendeurButton = new Button("Voir les vendeurs");
        Utilitaire.styliserBouton(voirVendeurButton);  
        voirVendeurButton.setOnAction(e -> {
            ConsulterVendeurs vueVendeurs = new ConsulterVendeurs(this.connexionMySQL);
            Scene scene = vueVendeurs.creerScene(1440, 850);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
        });


        Button boutonNvVendeur = new Button("Ajouter un vendeur");
        Utilitaire.styliserBouton(boutonNvVendeur);
        boutonNvVendeur.setOnAction(e -> {
            AjouterVendeur vue = new AjouterVendeur(connexionMySQL);
            Scene scene = vue.creerScene(1440, 850);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
        });

        Button boutonStock = new Button("Consulter le stock ");
        boutonStock.setOnAction(e -> {
                ConsulterStock vueStock = new ConsulterStock(this.connexionMySQL);
                Scene scene = vueStock.creerScene(1440, 850);
                Stage stage = (Stage) this.getScene().getWindow();
                stage.setScene(scene);

        });

        Button boutonStats = new Button("Consulter Statistiques");
        Utilitaire.styliserBouton(boutonStats);
        boutonStats.setOnAction(e -> {
            ConsulterStats vueRapports = new ConsulterStats(this.connexionMySQL);
            Scene scene = vueRapports.creerScene(1440, 850);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
        });


        Button boutonAjouterLibrairie = new Button("Ajouter une librairie");
        Utilitaire.styliserBouton(boutonAjouterLibrairie);
        boutonAjouterLibrairie.setOnAction(e -> {
            AjouterLibrairie vueDonnees = new AjouterLibrairie(this.connexionMySQL);
            Scene scene = vueDonnees.creerScene(1440, 850);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
        });

        Button boutonConsulterLibrairie = new Button("Consulter les Librairies");
        Utilitaire.styliserBouton(boutonConsulterLibrairie);
        boutonConsulterLibrairie.setOnAction(e -> {
            ConsulterLibrairies vueDonnees = new ConsulterLibrairies(this.connexionMySQL);
            Scene scene = vueDonnees.creerScene(1440, 850);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
        });


        Button boutonDeco = new Button("Déconnexion");
        boutonDeco.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        Utilitaire.styliserBouton(boutonNvVendeur);
        Utilitaire.styliserBouton(boutonStock);
        Utilitaire.styliserBouton(boutonStats);
        Utilitaire.styliserBouton(boutonAjouterLibrairie);
        Utilitaire.styliserBouton(boutonConsulterLibrairie);

        optionAdmin.getChildren().addAll(bonjour, voirVendeurButton, boutonNvVendeur, boutonStock, boutonStats, boutonConsulterLibrairie, boutonAjouterLibrairie, boutonDeco);
        setCenter(optionAdmin);

        boutonDeco.setOnAction(new ControleurRetourAccueil());
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