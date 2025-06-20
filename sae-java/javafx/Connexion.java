package javafx;

import src.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Connexion extends Application {

    private static final String TITRE_APPLICATION = "Livres Express";

    private static final String SERVEUR_BD = "servinfo-maria";
    private static final String NOM_BD = "DBmaillet"; 
    private static final String LOGIN_BD = "maillet";
    private static final String MDP_BD = "maillet"; 

    private ConnexionMySQL connexionMySQL;
    private Stage stagePrincipal; 

    /**
    * Méthode d'initialisation de l'application.
     */
    @Override
    public void init() {
        try {
            this.connexionMySQL = new ConnexionMySQL();
        } catch (ClassNotFoundException ex) {
            System.err.println("Erreur : Le pilote JDBC MySQL/MariaDB n'a pas été trouvé. " + ex.getMessage());
        }
    }

    /**
     * Méthode de démarrage de l'application.
     * @param stage La scène principale de l'application.
     */
    @Override
    public void start(Stage stage) {
        this.stagePrincipal = stage; 

        if (this.connexionMySQL == null) {
            try {
                this.connexionMySQL = new ConnexionMySQL();
            } catch (ClassNotFoundException e) {
                afficherErreurCritiqueEtQuitter("Le pilote JDBC n'a pas pu être chargé. L'application va se fermer.");
                return;
            }
        }
        
        stage.setTitle(TITRE_APPLICATION);

        if (!connexionAutomatiqueBD()) {
            afficherErreurCritiqueEtQuitter("Impossible de se connecter à la base de données.\nVeuillez vérifier la configuration et votre connexion réseau.\n\nL'application va se fermer.");
            return;
        }
        System.out.println("Connexion BD réussie !");


        BorderPane racine = new BorderPane();
        racine.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        

        BorderPane haut = new BorderPane();
        haut.setRight(creerBoutonQuitter());
        racine.setTop(haut);
        racine.setPadding(new Insets(10));


        Text titrePrincipal = new Text("Bonjour, bienvenue sur la plateforme !");
        titrePrincipal.setFont(Font.font("Arial", 24));
        titrePrincipal.setFill(Color.BLACK);
        titrePrincipal.setTextAlignment(TextAlignment.CENTER);


        Button boutonClient = creerBoutonRole("Client");
        boutonClient.setOnAction(e -> afficherPageChoixClient());

        Button boutonVendeur = creerBoutonRole("Vendeur");
        boutonVendeur.setOnAction(e -> afficherPageConnexionVendeur());

        Button boutonAdmin = creerBoutonRole("Administrateur");
        boutonAdmin.setOnAction(e -> afficherPageConnexionAdmin());

        HBox boiteBoutons = new HBox(20, boutonClient, boutonVendeur, boutonAdmin);
        boiteBoutons.setAlignment(Pos.CENTER);

        VBox vboxCentre = new VBox(40, titrePrincipal, boiteBoutons);
        vboxCentre.setAlignment(Pos.CENTER);
        racine.setCenter(vboxCentre);


        Scene scene = new Scene(racine, 1440, 850);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * Méthode pour établir une connexion automatique à la base de données.
     * @return true si la connexion est réussie, false sinon.
     */
    private boolean connexionAutomatiqueBD() {
        try {
            connexionMySQL.connecter(SERVEUR_BD, NOM_BD, LOGIN_BD, MDP_BD);
            return connexionMySQL.isConnecte();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la connexion automatique : " + e.getMessage());
            return false;
        }
    }

    /**
     * Méthode pour créer un bouton de rôle.
     * @param texte Le texte à afficher sur le bouton.
     * @return Le bouton créé.
     */
    private Button creerBoutonRole(String texte) {
        Button bouton = new Button(texte);
        Utilitaire.styliserBouton(bouton);
        return bouton;
    }
    /**
     * Méthode pour créer un bouton de quitter.
     * @return Le bouton de quitter créé.
     */
    private Button creerBoutonQuitter() {
        Button boutonQuitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(boutonQuitter);
        boutonQuitter.setOnAction(new ControleurQuitter());
        return boutonQuitter;
    }
    /**
     * Méthode pour afficher une alerte d'erreur critique et fermer l'application.
     * @param message Le message d'erreur à afficher.
     */
    private void afficherErreurCritiqueEtQuitter(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de Connexion à la Base de Données");
        alerte.setHeaderText("Connexion BD échouée");
        alerte.setContentText(message);
        alerte.showAndWait(); 
        stagePrincipal.close();
    }
    /**
     * Méthode pour afficher la page de choix du client.
     */
    public void afficherPageChoixClient() {
        BorderPane racineChoixClient = new BorderPane();
        racineChoixClient.setPadding(new Insets(10));
        racineChoixClient.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        BorderPane haut = new BorderPane();
        haut.setRight(creerBoutonQuitter());
        racineChoixClient.setTop(haut);

        Label texteChoix = new Label("Bienvenue Client ! Que souhaitez-vous faire ?");
        texteChoix.setFont(Font.font("Arial", 18));

        Button boutonSeConnecter = new Button("Se connecter (ID existant)");
        boutonSeConnecter.setPrefWidth(250);
        Utilitaire.styliserBouton(boutonSeConnecter);

        Button boutonCreerCompte = new Button("Créer un compte");
        boutonCreerCompte.setPrefWidth(250);
        Utilitaire.styliserBouton(boutonCreerCompte);

        Button boutonRetourAccueil = new Button("Retour");
        Utilitaire.styliserBoutonRetour(boutonRetourAccueil);
        boutonRetourAccueil.setOnAction(e -> start(stagePrincipal));

        VBox vboxChoix = new VBox(30, texteChoix, boutonSeConnecter, boutonCreerCompte, boutonRetourAccueil);
        vboxChoix.setAlignment(Pos.CENTER);
        vboxChoix.setPadding(new Insets(40));
        racineChoixClient.setCenter(vboxChoix);
        Scene sceneChoixClient = new Scene(racineChoixClient, 1440, 850);

        BorderPane racineConnexionClient = new BorderPane();
        racineConnexionClient.setPadding(new Insets(10));
        racineConnexionClient.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        BorderPane hautConnexionClient = new BorderPane();
        hautConnexionClient.setRight(creerBoutonQuitter());
        racineConnexionClient.setTop(hautConnexionClient);

        Label labelId = new Label("Veuillez entrer votre ID client :");
        Label labelCodePostal = new Label("Veuillez entrer votre code postal :");
        labelId.setFont(Font.font("Arial", 18));
        labelCodePostal.setFont(Font.font("Arial", 18));
        TextField champId = new TextField();
        champId.setPromptText("ID client");
        TextField champCodePostal = new TextField();
        champCodePostal.setPromptText("Code postal");

        Button boutonRetourConnexion = new Button("Retour");
        Utilitaire.styliserBoutonRetour(boutonRetourConnexion);
        boutonRetourConnexion.setOnAction(e -> stagePrincipal.setScene(sceneChoixClient));

        Button boutonConfirmerConnexion = new Button("Confirmer la connexion"); 
        Utilitaire.styliserBoutonConfirmer(boutonConfirmerConnexion);
        boutonConfirmerConnexion.setOnAction(e -> {
            String idClientStr = champId.getText().trim();
            String codePostalStr = champCodePostal.getText().trim();

            if (idClientStr.isEmpty() || codePostalStr.isEmpty()) {
                afficherAlerte(Alert.AlertType.WARNING, "Champ(s) vide(s)", null, "Veuillez entrer votre ID client et votre code postal.");
                return;
            }

            try {
                int idClient = Integer.parseInt(idClientStr);
                Client client = ClientBD.getClientParId(connexionMySQL.getConnexion(), idClient);
                
                if (client != null && client.getCodepostal().equals(codePostalStr)) {
                    System.out.println("Client ID saisi : " + idClient + " (" + client.getPrenom() + " " + client.getNom() + ")");
                    AccueilClient accueilClient = new AccueilClient(client, connexionMySQL); 
                    Scene sceneAccueil = accueilClient.creerScene(1440, 850);
                    stagePrincipal.setScene(sceneAccueil);
                    stagePrincipal.setTitle("Accueil Client - " + client.getPrenom() + " " + client.getNom());
                    stagePrincipal.setResizable(true);
                    System.out.println("Connexion Client - Accueil affiché");
                } else {
                    afficherAlerte(Alert.AlertType.ERROR, "Client introuvable", null, "ID invalide ou code postal incorrect. Veuillez vérifier vos informations.");
                }
            } catch (NumberFormatException nfe) {
                afficherAlerte(Alert.AlertType.WARNING, "ID invalide", null, "L'ID client doit être un nombre entier.");
            } catch (Exception ex) { 
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur inattendue est survenue.", "Détails : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox vboxConnexion = new VBox(20, labelId, champId, labelCodePostal, champCodePostal, boutonConfirmerConnexion, boutonRetourConnexion);
        vboxConnexion.setAlignment(Pos.CENTER);
        vboxConnexion.setPadding(new Insets(40));
        racineConnexionClient.setCenter(vboxConnexion);
        Scene sceneConnexionClient = new Scene(racineConnexionClient, 1440, 850);

        boutonSeConnecter.setOnAction(e -> stagePrincipal.setScene(sceneConnexionClient));

        boutonCreerCompte.setOnAction(e -> {
            VueCreerCompte vueCreerCompte = new VueCreerCompte(stagePrincipal, connexionMySQL, this);
            stagePrincipal.setScene(vueCreerCompte.creerScene(1440, 850));
            stagePrincipal.setTitle("Créer un compte client");
        });

        // Affiche la scène de choix client par défaut
        stagePrincipal.setScene(sceneChoixClient);
        stagePrincipal.setTitle("Connexion Client");
        stagePrincipal.show();
        System.out.println("Connexion Client - Choix affiché");
    }

    /**
     * Méthode pour afficher la page de connexion du vendeur.
     */
    public void afficherPageConnexionVendeur() {
        BorderPane racineVendeur = new BorderPane();
        racineVendeur.setPadding(new Insets(10));
        racineVendeur.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        BorderPane hautVendeur = new BorderPane();
        hautVendeur.setRight(creerBoutonQuitter());
        racineVendeur.setTop(hautVendeur);
        Label labelId = new Label("Veuillez entrer votre ID vendeur :");
        labelId.setFont(Font.font("Arial", 18));
        TextField champId = new TextField();
        champId.setPromptText("ID vendeur");

        Label labelPrenom = new Label("Veuillez entrer votre prénom :");
        labelPrenom.setFont(Font.font("Arial", 18));
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom vendeur");
        Button boutonRetour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(boutonRetour);
        boutonRetour.setOnAction(e -> start(stagePrincipal));

        Button boutonConfirmer = new Button("Confirmer");
        Utilitaire.styliserBoutonConfirmer(boutonConfirmer);
        boutonConfirmer.setOnAction(e -> {
            String idVendeurStr = champId.getText().trim();
            if (idVendeurStr.isEmpty()) {
                afficherAlerte(Alert.AlertType.WARNING, "Champ vide", null, "Veuillez entrer votre ID vendeur et votre prénom.");
                return;
            } else if ( ! prenomField.getText().trim().equals(VendeurBD.getVendeurParId(connexionMySQL.getConnexion(), Integer.parseInt(idVendeurStr)).getPrenom())) {
                afficherAlerte(Alert.AlertType.WARNING, "Prénom vendeur incorrect", null, "Veuillez entrer votre prénom attention a la majuscule !!");
                return;
            }
            try {
                int idVendeur = Integer.parseInt(idVendeurStr);
                System.out.println("ID vendeur saisi : " + idVendeur);
                Vendeur vendeur = VendeurBD.getVendeurParId(connexionMySQL.getConnexion(), idVendeur);
                if (vendeur != null) {
                    AccueilVendeur accueilVendeur = new AccueilVendeur(vendeur, connexionMySQL); 
                    Scene sceneAccueil = accueilVendeur.creerScene(1440, 850);
                    stagePrincipal.setScene(sceneAccueil);
                    stagePrincipal.setTitle("Accueil Vendeur - " + vendeur.getNom()); 
                    stagePrincipal.setResizable(true);
                    System.out.println("Connexion Vendeur - Accueil affiché");
                } else {
                    afficherAlerte(Alert.AlertType.ERROR, "Vendeur introuvable", null, "Aucun vendeur trouvé avec cet ID. Veuillez vérifier vos informations.");
                }
            } catch (NumberFormatException nfe) {
                afficherAlerte(Alert.AlertType.WARNING, "ID invalide", null, "L'ID vendeur doit être un nombre.");
            } catch (Exception ex) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur inattendue est survenue.", "Détails : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox vboxVendeur = new VBox(20, labelId, champId, labelPrenom, prenomField, boutonConfirmer, boutonRetour);
        vboxVendeur.setAlignment(Pos.CENTER);
        vboxVendeur.setPadding(new Insets(40));
        racineVendeur.setCenter(vboxVendeur);

        Scene sceneVendeur = new Scene(racineVendeur, 1440, 850);
        stagePrincipal.setScene(sceneVendeur);    
        stagePrincipal.setTitle("Connexion Vendeur");
        stagePrincipal.show();
        stagePrincipal.setResizable(false);
        System.out.println("Interface Connexion Vendeur affichée.");
    }

    /**
     * Méthode pour afficher la page de connexion de l'administrateur.
     */
    public void afficherPageConnexionAdmin() {
        BorderPane racineAdmin = new BorderPane();
        racineAdmin.setPadding(new Insets(10));
        racineAdmin.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        BorderPane hautAdmin = new BorderPane();
        hautAdmin.setRight(creerBoutonQuitter());
        racineAdmin.setTop(hautAdmin);
        Label labelMdp = new Label("Veuillez entrer le mot de passe administrateur :");
        labelMdp.setFont(Font.font("Arial", 18));
        PasswordField champMdp = new PasswordField(); 
        champMdp.setPromptText("Mot de passe administrateur");

        Button boutonConfirmer = new Button("Confirmer");
        Utilitaire.styliserBoutonConfirmer(boutonConfirmer);
        boutonConfirmer.setOnAction(e -> {
            String mdpSaisi = champMdp.getText();
            if (mdpSaisi.equals("licornebleueapoisrouge")) { 
                System.out.println("Connexion Admin réussie !");
                AccueilAdmin AccueilAdmin = new AccueilAdmin(connexionMySQL); 
                Scene sceneAdmin = AccueilAdmin.creerScene(1440, 850);
                stagePrincipal.setScene(sceneAdmin);
                stagePrincipal.setTitle("Accueil Administrateur");
                stagePrincipal.setResizable(true);
                System.out.println("Connexion Admin - Accueil affiché");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Authentification échouée", null, "Mot de passe incorrect. \n petit indice : trouvez un fichier texte ultra secret"); 
            }
        });

        Button boutonRetour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(boutonRetour);
        boutonRetour.setOnAction(e -> start(stagePrincipal));

        VBox vboxAdmin = new VBox(20, labelMdp, champMdp, boutonConfirmer, boutonRetour); 
        vboxAdmin.setAlignment(Pos.CENTER);
        vboxAdmin.setPadding(new Insets(40));
        racineAdmin.setCenter(vboxAdmin);

        Scene sceneAdmin = new Scene(racineAdmin, 1440, 850);
        stagePrincipal.setScene(sceneAdmin);
        stagePrincipal.setTitle("Connexion Administrateur");
        stagePrincipal.show();
        stagePrincipal.setResizable(false);    
        System.out.println("Interface Connexion Admin affichée.");
    }

    /**
     * Méthode pour afficher une alerte.
     * @param type Le type d'alerte (information, erreur, avertissement, etc.).
     * @param titre Le titre de l'alerte.
     * @param entete L'en-tête de l'alerte.
     * @param contenu Le contenu de l'alerte.
     */
    private void afficherAlerte(Alert.AlertType type, String titre, String entete, String contenu) {
        Alert alerte = new Alert(type);
        alerte.setTitle(titre);
        alerte.setHeaderText(entete);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }

    /**
     * Méthode pour fermer l'application et la connexion à la base de données.
     */
    @Override
    public void stop() {
        System.out.println("Fermeture de l'application.");
        if (connexionMySQL != null && connexionMySQL.isConnecte()) {
            try {
                connexionMySQL.close();
                System.out.println("Connexion à la base de données fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion BD : " + e.getMessage());
            }
        }
    }
    /**
     * Méthode principale pour lancer l'application JavaFX.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}