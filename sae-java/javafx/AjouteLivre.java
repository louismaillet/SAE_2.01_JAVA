package javafx;

import src.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AjouteLivre extends BorderPane {

    /**
     * Constructeur de la classe AjouteLivre.
     * @param vendeur Le vendeur connecté.
     * @param connexion La connexion à la base de données MySQL.
     */
    public AjouteLivre(Vendeur vendeur, ConnexionMySQL connexion) {
        super();
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        Label txt = new Label("Ajouter un livre au stock : ");
        txt.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        Button boutonQuitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(boutonQuitter);
        boutonQuitter.setOnAction(new ControleurQuitter());
        Utilitaire.styliserBoutonQuitter(boutonQuitter);
        BorderPane haut = new BorderPane();
        haut.setLeft(txt);
        haut.setRight(boutonQuitter);
        this.setTop(haut);

        GridPane mid = new GridPane();
        mid.setHgap(60);
        mid.setVgap(60);
        mid.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        mid.setPadding(new Insets(20));

        Label lblNomLivre = new Label("Nom du livre :");
        lblNomLivre.setStyle("-fx-font-size: 20px;");
        mid.add(lblNomLivre, 0, 0);

        TextField txtLivre = new TextField();
        mid.add(txtLivre, 0, 1);

        Label lblPrix = new Label("Prix :");
        lblPrix.setStyle("-fx-font-size: 20px;");
        mid.add(lblPrix, 1, 2);

        TextField txtPrix = new TextField();
        mid.add(txtPrix, 1, 3);

        Label lblDate = new Label("Date de publication :");
        lblDate.setStyle("-fx-font-size: 20px;");
        mid.add(lblDate, 0, 2);

        TextField txtDate = new TextField();
        mid.add(txtDate, 0, 3); 

        Label lblPages = new Label("Nombre de pages :");
        lblPages.setStyle("-fx-font-size: 20px;");
        mid.add(lblPages, 1, 0);

        TextField txtPages = new TextField();
        mid.add(txtPages, 1, 1);

        Label lblQte = new Label("Quantité :");
        lblQte.setStyle("-fx-font-size: 20px;");
        mid.add(lblQte, 2, 0);

        TextField txtQte = new TextField();
        mid.add(txtQte, 2, 1);

        this.setCenter(mid);

        Button valider = new Button("Valider");
        Utilitaire.styliserBoutonConfirmer(valider);
        valider.setOnAction(e -> {
            try {

                String nomLivre = txtLivre.getText();
                String pagesStr = txtPages.getText();
                String datePub = txtDate.getText(); 
                String prixStr = txtPrix.getText();
                String qteStr = txtQte.getText();

                if (nomLivre.isEmpty() || pagesStr.isEmpty() || datePub.isEmpty() || prixStr.isEmpty() || qteStr.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.").showAndWait();
                    return;
                }

                int pages = Integer.parseInt(pagesStr);
                int datePubInt = Integer.parseInt(datePub);
                double prix = Double.parseDouble(prixStr);
                int quantite = Integer.parseInt(qteStr);
                
                long isbn = LivreBD.getDernierISBN(connexion.getConnexion());
                Livre livreAjouter = new Livre(isbn + 1, nomLivre, pages, datePubInt, prix, quantite);
                LivreBD.ajouterLivreAMagasin(connexion.getConnexion(), livreAjouter, vendeur.getMagasin().getIdmag());

                new Alert(Alert.AlertType.INFORMATION, "Livre '" + nomLivre + "' ajouté avec succès !").showAndWait();
                
                txtLivre.clear();
                txtPages.clear();
                txtDate.clear();
                txtPrix.clear();
                txtQte.clear();

            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Format incorrect");
                alert.setContentText("Veuillez entrer des valeurs valides pour le prix, la date, le nombre de pages et la quantité.");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de l'ajout du livre");
                alert.setContentText("Une erreur est survenue lors de l'ajout du livre. Veuillez réessayer.");
                alert.showAndWait();
            }

        });

        Button annuler = new Button("Annuler");
        Utilitaire.styliserBoutonRetour(annuler);
        annuler.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            AccueilVendeur accueilVendeur = new AccueilVendeur(vendeur, connexion);
            Scene scene = accueilVendeur.creerScene(1440, 850);
            stage.setScene(scene);
            
        });
        HBox conteneur = new HBox();
        conteneur.getChildren().addAll(valider, annuler);
        conteneur.setSpacing(30);
        conteneur.setPadding(new Insets(20));
        conteneur.setAlignment(Pos.CENTER);
        this.setBottom(conteneur);
        this.setPadding(new Insets(30));


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
