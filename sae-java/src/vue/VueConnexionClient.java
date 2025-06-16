package vue;

import controleur.ControleurPrincipal;
import controleur.ControleurPasserCommande;
import controleur.ControleurConsulterPanier;




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
import javafx.stage.Window;

public class VueConnexionClient extends Application {
    private static final String TITRE = "IUTEAM'S - La plateforme de jeux de l'IUTO";
    private static final String CHEMIN_ICONE = "file:/home/iut45/Etudiants/o22401162/SAE/SAEJAVAFINAL/SAE_2.01_JAVA/sae-java/img/bookIcon.png";

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITRE);
        stage.getIcons().add(new Image(CHEMIN_ICONE));

        BorderPane racine = new BorderPane();

        // Ajout du bouton Quitter sur la page principale
        Button quitter = new Button("Quitter");
        quitter.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        quitter.setOnAction(e -> stage.close());
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        racine.setTop(quitter);
        racine.setPadding(new Insets(10));

        Text titre = new Text("Bonjour, bienvenue sur la plateforme !");
        titre.setFont(Font.font("Arial", 24));
        titre.setFill(Color.BLACK);
        titre.setTextAlignment(TextAlignment.CENTER);

        Button client = creerBouton("Client");
        client.setOnAction(e -> {
            System.out.println("Connexion Client");
            ConnexionClient();
        });
        Button vendeur = creerBouton("Vendeur");
        vendeur.setOnAction(e -> {
            System.out.println("Connexion Vendeur");
            ConnexionVendeur();
        });

        Button admin = creerBouton("Administrateur");
        admin.setOnAction(e -> {
            ConnexionAdmin();
            System.out.println("Connexion Admin");
        });


        HBox boiteBoutons = new HBox(20, client, vendeur, admin);
        boiteBoutons.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(40, titre, boiteBoutons);
        vbox.setAlignment(Pos.CENTER);

        racine.setCenter(vbox);

        Scene scene = new Scene(racine, 800, 600);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    private Button creerBouton(String texte) {
        Button bouton = new Button(texte);
        bouton.setTooltip(new Tooltip(texte));
        bouton.setPrefSize(250, 80);
        bouton.setStyle("-fx-font-size: 18px; -fx-background-color: orange; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px;");
        bouton.setOnMouseEntered(e -> bouton.setStyle("-fx-background-color: #ffb84d; -fx-border-color: black; -fx-border-width: 1px; -fx-font-size: 18px; -fx-text-fill: black; -fx-font-weight: bold;"));
        bouton.setOnMouseExited(e -> bouton.setStyle("-fx-font-size: 18px; -fx-background-color: orange; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px;"));
        return bouton;
    }


    public void ConnexionClient() {
        Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);

        // Page choix : Se connecter ou Créer un compte
        Label labelChoix = new Label("Bienvenue Client ! Que souhaitez-vous faire ?");
        labelChoix.setFont(Font.font("Arial", 18));

        Button btnSeConnecter = new Button("Se connecter");
        btnSeConnecter.setPrefWidth(200);
        btnSeConnecter.setStyle("-fx-font-size: 16px; -fx-background-color: orange; -fx-text-fill: black; -fx-font-weight: bold;");

        Button btnCreerCompte = new Button("Créer un compte");
        btnCreerCompte.setPrefWidth(200);
        btnCreerCompte.setStyle("-fx-font-size: 16px; -fx-background-color: orange; -fx-text-fill: black; -fx-font-weight: bold;");

        Button retourAccueil = new Button("Retour");
        retourAccueil.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        retourAccueil.setOnAction(e -> start(stage));

        VBox vboxChoix = new VBox(30, labelChoix, btnSeConnecter, btnCreerCompte, retourAccueil);
        vboxChoix.setAlignment(Pos.CENTER);
        vboxChoix.setPadding(new Insets(40));
        Scene sceneChoix = new Scene(vboxChoix, 800, 600);

        // Page Se connecter (ID seulement)
        Label labelConnexion = new Label("Veuillez entrer votre ID client :");
        labelConnexion.setFont(Font.font("Arial", 18));
        TextField tfIdConnexion = new TextField();
        tfIdConnexion.setPromptText("ID client");

        Button retourChoix1 = new Button("Retour");
        retourChoix1.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        retourChoix1.setOnAction(e -> stage.setScene(sceneChoix));

        VBox vboxConnexion = new VBox(20, labelConnexion, tfIdConnexion, retourChoix1);
        vboxConnexion.setAlignment(Pos.CENTER);
        vboxConnexion.setPadding(new Insets(40));
        Scene sceneConnexion = new Scene(vboxConnexion, 800, 600);

        // Page Créer un compte (nom, prénom, adresse, code postal, ville)
        Label labelCreation = new Label("Créer un compte client");
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

        Button btnConfirmer = new Button("Confirmer");
        btnConfirmer.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        Button retourChoix2 = new Button("Retour");
        retourChoix2.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        retourChoix2.setOnAction(e -> stage.setScene(sceneChoix));

        VBox vboxCreation = new VBox(20, labelCreation, tfNom, tfPrenom, tfAdresse, tfCodePostal, tfVille, btnConfirmer, retourChoix2);
        vboxCreation.setAlignment(Pos.CENTER);
        vboxCreation.setPadding(new Insets(40));
        Scene sceneCreation = new Scene(vboxCreation, 800, 600);

        // Actions des boutons
        btnSeConnecter.setOnAction(e -> stage.setScene(sceneConnexion));
        btnCreerCompte.setOnAction(e -> stage.setScene(sceneCreation));

        // Affichage de la première page
        stage.setScene(sceneChoix);
        stage.setTitle("Connexion Client");
        stage.getIcons().add(new Image(CHEMIN_ICONE));
        stage.show();
        stage.setResizable(false); 
        System.out.println("Connexion Client - Choix affiché");
    }


    public void ConnexionVendeur() {
        Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);

        Label label = new Label("Veuillez entrer votre ID vendeur :");
        label.setFont(Font.font("Arial", 18));
        TextField textField = new TextField();
        textField.setPromptText("ID vendeur");

        Button retour = new Button("Retour");
        retour.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        retour.setOnAction(e -> start(stage));

        VBox vbox = new VBox(20, label, textField, retour);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));

        Scene sceneVendeur = new Scene(vbox, 800, 600);
        stage.setScene(sceneVendeur);
        stage.setResizable(false); 
        System.out.println("Connexion Vendeur");
    }

    public void ConnexionAdmin() {
        Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);

        Label label = new Label("Veuillez entrer votre ID administrateur :");
        label.setFont(Font.font("Arial", 18));
        TextField textField = new TextField();
        textField.setPromptText("ID administrateur");

        Button retour = new Button("Retour");
        retour.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        retour.setOnAction(e -> start(stage));

        VBox vbox = new VBox(20, label, textField, retour);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));

        Scene sceneAdmin = new Scene(vbox, 800, 600);
        stage.setScene(sceneAdmin);
        stage.setTitle("Connexion Administrateur");
        stage.getIcons().add(new Image(CHEMIN_ICONE));
        stage.show();
        stage.setResizable(false); 
        System.out.println("Connexion Admin");
    }

@Override
    public void stop() {
        System.out.println("Fermeture de l'application.");
    }
    @Override
    public void init() {
        System.out.println("Initialisation de l'application.");
    }

    // Méthode main pour lancer l'application JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}
