package javafx;

import src.ConnexionMySQL;
import src.Client;
import src.ClientBD;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;

public class VueCreerCompte extends BorderPane {

    private ConnexionMySQL connexionMySQL;
    private Stage stagePrincipal;
    private Connexion connexionApp; 

    /**
     * Constructeur de la classe VueCreerCompte.
     * Permet à un utilisateur de créer un nouveau compte client.
     * @param stagePrincipal La fenêtre principale de l'application.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param connexionApp La connexion à l'application pour naviguer entre les pages.
     */
    public VueCreerCompte(Stage stagePrincipal, ConnexionMySQL connexionMySQL, Connexion connexionApp) {
        super();
        this.stagePrincipal = stagePrincipal;
        this.connexionMySQL = connexionMySQL;
        this.connexionApp = connexionApp; 
        
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        BorderPane topPane = new BorderPane();
        topPane.setRight(quitter);
        topPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(topPane);

        Label labelCreation = new Label("Créer un nouveau compte client");
        labelCreation.setFont(Font.font("Arial", 18));
        
        TextField tfNom = new TextField();
        tfNom.setPromptText("Nom");
        TextField tfPrenom = new TextField();
        tfPrenom.setPromptText("Prénom");
        TextField tfAdresse = new TextField();
        tfAdresse.setPromptText("Adresse");
        TextField tfCodePostal = new TextField();
        tfCodePostal.setPromptText("Code postal");
        TextField tfVille = new TextField();
        tfVille.setPromptText("Ville");

        Button btnConfirmerCreation = new Button("Créer le compte et se connecter"); 
        Utilitaire.styliserBoutonConfirmer(btnConfirmerCreation);
        btnConfirmerCreation.setOnAction(e -> {
            String nom = tfNom.getText().trim(); // trim permet de supprimer les espaces inutiles au début et à la fin
            String prenom = tfPrenom.getText().trim();
            String adresse = tfAdresse.getText().trim();
            String codePostal = tfCodePostal.getText().trim();
            String ville = tfVille.getText().trim();

            if (!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !codePostal.isEmpty() && !ville.isEmpty()) {
                if (codePostal.length() != 5 || !codePostal.matches("\\d+")) { //permet juste de vérifier que le code postal est un nombre de 5 chiffres
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Code Postal Invalide");
                    alert.setHeaderText(null);
                    alert.setContentText("Le code postal doit comporter 5 chiffres.");
                    alert.showAndWait();
                    return;
                }
                try {
                    int nouvelId = ClientBD.getDernierIdClient(connexionMySQL) + 1;
                    Client nouveauClient = new Client(nouvelId, nom, prenom, adresse, codePostal, ville);
                    ClientBD.ajouterClient(connexionMySQL.getConnexion(), nouveauClient);

                    String nomClient = prenom + " " + nom; 
                    System.out.println("Nouveau client créé: " + nomClient + " avec ID: " + nouvelId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Compte Créé");
                    alert.setHeaderText("Votre compte a été créé avec succès.");
                    alert.setContentText("Bienvenue, " + nomClient + " !"+
                            "\nVous pouvez maintenant accéder à votre compte."+
                            "\nVotre ID client est: " + nouvelId);
                    alert.showAndWait();


                    AccueilClient accueilClient = new AccueilClient(nouveauClient, connexionMySQL);
                    Scene sceneAccueil = accueilClient.creerScene(1440, 850);
                    stagePrincipal.setScene(sceneAccueil);
                    System.out.println("Création Compte - Accueil Client affiché");
                    stagePrincipal.setTitle("Accueil Client - " + nomClient);
                    stagePrincipal.setResizable(true);
                    
                } catch (SQLException sqle) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur BD");
                    alert.setHeaderText("Erreur lors de la création du compte.");
                } catch (Exception ex) { 
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Une erreur inattendue est survenue.");
                    alert.setContentText("Détails: " + ex.getMessage());
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champ(s) vide(s)");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs pour créer un compte.");
                alert.showAndWait();
            }
        });

        Button retourChoix = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retourChoix);
        retourChoix.setOnAction(e -> {
            this.connexionApp.afficherPageChoixClient();
            System.out.println("Retour au choix Client");
        });

        VBox vboxCompte = new VBox(20, labelCreation, tfNom, tfPrenom, tfAdresse, tfCodePostal, tfVille, btnConfirmerCreation, retourChoix);
        vboxCompte.setAlignment(Pos.CENTER);
        vboxCompte.setPadding(new Insets(40));
        this.setCenter(vboxCompte);
    }

    /**
     * Crée une scène pour la vue de création de compte.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}