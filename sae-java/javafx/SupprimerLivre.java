package javafx;

import src.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class SupprimerLivre extends VBox {
    
    private Vendeur vendeur;

    /**
     * Constructeur de la classe SupprimerLivre.
     * Permet au vendeur de supprimer un livre de sa librairie en entrant l'ISBN.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param vendeur Le vendeur connecté.
     */
    public SupprimerLivre(ConnexionMySQL connexionMySQL, Vendeur vendeur){
        super(30); 
        this.vendeur = vendeur;
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER); 

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());

        BorderPane topPane = new BorderPane();
        topPane.setRight(quitter);
        this.getChildren().add(0, topPane);

        Label txt = new Label("Veuillez entrer l’ISBN du livre que vous voulez supprimer :");
        txt.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        TextField isbnASupprimer = new TextField();
        isbnASupprimer.setPrefWidth(300); 

        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);

        Button annuler = new Button("annuler");
        Utilitaire.styliserBoutonRetour(annuler);


        valider.setOnAction(e -> {
            String isbnStr = isbnASupprimer.getText();
            if (isbnStr.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Veuillez entrer un ISBN.").showAndWait();
                return;
            }
            long isbn = Long.parseLong(isbnStr);
            if (LivreBD.supprimerLivre(connexionMySQL.getConnexion(),isbn, vendeur.getMagasin().getIdmag())) {
                new Alert(Alert.AlertType.INFORMATION, "Livre supprimé avec succès !").showAndWait();

            }
            else {
                new Alert(Alert.AlertType.ERROR, "Erreur : le livre avec cet ISBN n'a pas pu être supprimé.").showAndWait();
            }
            
            
        });

        annuler.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            AccueilVendeur accueilVendeur = new AccueilVendeur(vendeur, connexionMySQL); // Passer null pour le vendeur, car on revient à l'accueil
            Scene scene = accueilVendeur.creerScene(1440, 1080);
            stage.setScene(scene);
        });


        this.getChildren().addAll(txt, isbnASupprimer, valider, annuler);
            }

    /**
     * Crée une scène pour la vue SupprimerLivre.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}
