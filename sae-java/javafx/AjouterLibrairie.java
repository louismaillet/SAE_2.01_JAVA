package javafx;

import src.*;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;

public class AjouterLibrairie extends VBox {
    /**
     * Constructeur de la classe AjouterLibrairie.
     * @param connexionMySQL La connexion à la base de données MySQL.
     */
    public AjouterLibrairie(ConnexionMySQL connexionMySQL) {
        super(10);
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER); 

        Label nomLabel = new Label("Veuillez renseigner le nom de la librairie :");
        nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField nomField = new TextField();
        nomField.setPrefWidth(300); 

        Label villeLabel = new Label("Veuillez renseigner la ville dans laquelle se situe la librairie : ");
        villeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField villeField = new TextField();
        villeField.setPrefWidth(300); 

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexionMySQL).creerScene(1440, 850));
        });
        HBox hbox = new HBox(10, quitter);
        hbox.setAlignment(Pos.TOP_RIGHT);
        BorderPane topPane = new BorderPane();
        topPane.setRight(hbox);
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.getChildren().add(0, topPane);

        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);
        valider.setOnAction(e -> {
            String nomLibrairie = nomField.getText();
            String villeLibrairie = villeField.getText();
            System.out.println("tessssssssssssssst");
            Magasin ajouterLibrairie = new Magasin(MagasinBD.getDernierIdMagasin(connexionMySQL.getConnexion()) +1,nomLibrairie, villeLibrairie);
            System.out.println("tessssssssssssssst2");
            if (nomLibrairie.isEmpty() || villeLibrairie.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                try {
                    AdministrateurBD.ajouterLibrairie(connexionMySQL.getConnexion(), ajouterLibrairie);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Librairie ajoutée");
                    alert.setHeaderText(null);
                    alert.setContentText("La librairie a été ajoutée avec succès.");
                    alert.showAndWait();
                    nomField.clear();
                    villeField.clear();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.getChildren().addAll(nomLabel, nomField, villeLabel, villeField, valider, retour);
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
