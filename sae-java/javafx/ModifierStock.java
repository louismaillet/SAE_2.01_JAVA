package javafx;

import src.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
public class ModifierStock extends VBox {
    /**
     * Constructeur de la classe ModifierStock.
     * Permet au vendeur de modifier la quantité d'un livre dans son stock.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param vendeur Le vendeur connecté.
     */
    public ModifierStock(ConnexionMySQL connexionMySQL, Vendeur vendeur) {
        super(30); 
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER); 

        Button quitter = new Button("Quitter");
        quitter.setOnAction(new ControleurQuitter());
        Utilitaire.styliserBoutonQuitter(quitter);

        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilVendeur(vendeur, connexionMySQL).creerScene(1440, 850));
        });

        HBox hbox = new HBox(10, retour, quitter);
        BorderPane topPane = new BorderPane();
        topPane.setRight(hbox);
        this.getChildren().add(0, topPane);

        Label txtIsbn = new Label("Veuillez entrer l’isbn du livre dont vous voulez modifier la quantité :");
        txtIsbn.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField isbn = new TextField();
        isbn.setPrefWidth(300); 

        Label txtQte = new Label("Entrez la nouvelle quantité :");
        txtQte.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField qte = new TextField();
        qte.setPrefWidth(300); 


        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);
        this.getChildren().addAll(txtIsbn, isbn,txtQte,qte,valider, retour);
        valider.setOnAction(event -> {
            if (
                MagasinBD.setQuantiteLivre(connexionMySQL.getConnexion(), vendeur.getMagasin().getIdmag(), Long.parseLong(isbn.getText()), Integer.parseInt(qte.getText()))) {;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La quantité du livre a été mise à jour avec succès.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de la mise à jour de la quantité du livre. Veuillez vérifier l'ISBN et la quantité.");
                alert.showAndWait();
            }
        });
    }

    /**
     * Crée une scène pour la vue ModifierStock.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}
