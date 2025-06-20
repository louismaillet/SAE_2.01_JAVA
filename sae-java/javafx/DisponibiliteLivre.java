package javafx;

import src.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class DisponibiliteLivre extends VBox {

    public DisponibiliteLivre(ConnexionMySQL connexionMySQL, Vendeur vendeur) {
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
        hbox.setAlignment(Pos.TOP_RIGHT);
        BorderPane topPane = new BorderPane();
        topPane.setRight(hbox);
        this.getChildren().add(0, topPane); 

        Label txt = new Label("Veuillez entrer l’isbn du livre dont vous voulez vérifier la disponibilité :");
        txt.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        TextField isbn = new TextField();
        isbn.setPrefWidth(300); 

        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);

        Label qtt = new Label();
        qtt.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        
        this.getChildren().addAll(txt, isbn, valider, qtt);


        valider.setOnAction(event -> {
            try {
                int dispo = MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), vendeur.getMagasin().getIdmag(), Long.parseLong(isbn.getText()));
                qtt.setText("Il y a " + dispo + " exemplaires de ce livre disponibles.");
            } catch (NumberFormatException e) {
                qtt.setText("Veuillez entrer un ISBN numérique valide.");
            } catch (NullPointerException e) {
                qtt.setText("Aucun livre trouvé avec cet ISBN.");
            }
        });
    }

    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}