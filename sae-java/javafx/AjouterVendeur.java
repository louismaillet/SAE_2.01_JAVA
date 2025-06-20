package javafx;

import src.*;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AjouterVendeur extends VBox {
    /**
     * Constructeur de la classe AjouterVendeur.
     * @param connexion La connexion à la base de données MySQL.
     */
    public AjouterVendeur(ConnexionMySQL connexion) {
        super(30); 
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER); 

        Label nomLabel = new Label("Veuillez renseigner le nom du vendeur : ");
        nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField nomField = new TextField();
        nomField.setPrefWidth(300);

        Label prenomLabel = new Label("Veuillez renseigner le prénom du vendeur : ");
        prenomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField prenomField = new TextField();
        prenomField.setPrefWidth(300);

        Button quitter = new Button("Quitter");
        quitter.setOnAction(new ControleurQuitter());
        Utilitaire.styliserBoutonQuitter(quitter);

        BorderPane topPane = new BorderPane();
        topPane.setRight(quitter);
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.getChildren().add(0, topPane);

        Label txtcombo = new Label("Veuillez choisir la librairie du vendeur :");
        txtcombo.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        ComboBox<String> combo = new ComboBox<>();
        List<Magasin> magasins = MagasinBD.chargerMagasins(connexion.getConnexion());
        for (Magasin m : magasins) {
            combo.getItems().add(m.getNommag());
        }

        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexion).creerScene(1440, 850));
        });


        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);
        valider.setOnAction(event -> {
            String nomVendeur = nomField.getText();
            String prenomVendeur = prenomField.getText();
            String librairie = combo.getValue();

            try{
                Vendeur vendeur = new Vendeur(VendeurBD.getDernierIdVendeur(connexion.getConnexion())+1,nomVendeur, prenomVendeur, MagasinBD.getMagasinParNom(connexion.getConnexion(), librairie), RoleVendeur.VENDEUR);
                AdministrateurBD.creerCompteVendeur(connexion.getConnexion(), vendeur);
                Label confirmation = new Label("Vendeur ajouté avec succès !");
                confirmation.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: green;");
                this.getChildren().add(confirmation);
            }
            catch (Exception e) {
                System.out.println("Erreur lors de l'ajout du vendeur : " + e.getMessage());
            }
            nomField.clear();
            prenomField.clear();
            combo.getSelectionModel().clearSelection();
        });

        this.getChildren().addAll(nomLabel, nomField, prenomLabel, prenomField, txtcombo, combo, valider, retour);
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