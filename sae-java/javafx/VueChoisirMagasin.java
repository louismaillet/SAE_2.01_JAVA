package javafx;

import src.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VueChoisirMagasin extends BorderPane {

    private Client client;
    private ConnexionMySQL connexionMySQL; 
    private Commande commande; 
    private List<Magasin> listeMagasins = new ArrayList<>();
    /**
     * Constructeur de la classe VueChoisirMagasin.
     * Permet à un client de choisir un magasin pour récupérer sa commande.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param commande La commande en cours du client.
     */ 
    public VueChoisirMagasin(Client client, ConnexionMySQL connexionMySQL, Commande commande) {
        super();
        this.client = client;
        this.connexionMySQL = connexionMySQL; 
        this.commande = commande; 
        this.listeMagasins = MagasinBD.chargerMagasins(this.connexionMySQL.getConnexion()); 
        this.setPadding(new Insets(30));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        Label titre = new Label("Choisissez votre magasin :");
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 30px; -fx-text-fill: #2c3e50;");
        BorderPane.setAlignment(titre, Pos.CENTER);
        BorderPane.setMargin(titre, new Insets(0, 0, 30, 0));
        this.setTop(titre);

        VBox vboxMagasins = new VBox(15);
        vboxMagasins.setAlignment(Pos.CENTER_LEFT);
        vboxMagasins.setPadding(new Insets(20));
        vboxMagasins.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
        vboxMagasins.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");

        ToggleGroup groupeMagasins = new ToggleGroup();

        for (Magasin magasin : listeMagasins) {
            RadioButton rb = new RadioButton(magasin.getNommag() + " - " + magasin.getVillemag());
            rb.setToggleGroup(groupeMagasins);
            rb.setUserData(magasin); 
            rb.setStyle("-fx-font-size: 18px; -fx-text-fill: #333333;");
            
            vboxMagasins.getChildren().add(rb);

        }

        Button boutonChoisir = new Button("Choisir ce magasin");
        Utilitaire.styliserBouton(boutonChoisir);
            boutonChoisir.setOnAction(e -> {
            if (groupeMagasins.getSelectedToggle() != null) {
                Magasin magasinChoisi = (Magasin) groupeMagasins.getSelectedToggle().getUserData();
                System.out.println("Magasin choisi : " + magasinChoisi.getNommag()); 
                Stage stage = (Stage) this.getScene().getWindow();
                this.commande.setMagasinRetrait(magasinChoisi); 
                VueAcheterLivreMagasin vueAcheterLivre = new VueAcheterLivreMagasin(this.client, magasinChoisi, this.connexionMySQL, this.commande);
                Scene scene = vueAcheterLivre.creerScene(1440, 850);
                stage.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sélection requise");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez choisir un magasin pour continuer.");
                alert.showAndWait();
            }
        });

        Button boutonRetour = new Button("Retour à l'Accueil");
        Utilitaire.styliserBoutonRetour(boutonRetour);
        boutonRetour.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        boutonRetour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            VuePasserCommande vuePasserCommande = new VuePasserCommande(this.client, this.connexionMySQL);
            Scene scene = vuePasserCommande.creerScene(1440, 850);
            stage.setScene(scene);
        });

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        BorderPane top = new BorderPane();
        top.setRight(quitter);
        top.setAlignment(quitter, Pos.TOP_RIGHT);
        this.setTop(top);
        this.setPadding(new Insets(10));

        VBox mid = new VBox(20, vboxMagasins, boutonChoisir, boutonRetour);
        mid.setAlignment(Pos.CENTER);
        mid.setPadding(new Insets(20));
        this.setCenter(mid);
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