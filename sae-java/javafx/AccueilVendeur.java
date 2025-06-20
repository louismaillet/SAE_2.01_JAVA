package javafx;

import src.*;

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

public class AccueilVendeur extends BorderPane {

    /**
     * Constructeur de la classe AccueilVendeur.
     * @param vendeur Le vendeur connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     */
    public AccueilVendeur(Vendeur vendeur, ConnexionMySQL connexionMySQL) {
        super();
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        Label titre = new Label("Bonjour " + vendeur.getPrenom() + " " + vendeur.getNom() + " !");
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        BorderPane haut = new BorderPane();
        haut.setCenter(titre);  
        haut.setRight(quitter);
        this.setTop(haut);

        VBox milieu = new VBox(50);
        milieu.setPadding(new Insets(70));
        milieu.setAlignment(Pos.CENTER);

        Button ajouteLivre = new Button("Ajouter un livre");
        ajouteLivre.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            AjouteLivre ajouteLivreVue = new AjouteLivre(vendeur, connexionMySQL);
            Scene scene = ajouteLivreVue.creerScene(1440, 850);
            stage.setScene(scene);
        });
        Button supprimerLivre = new Button("Supprimer un livre");
        supprimerLivre.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            SupprimerLivre supprimerLivreVue = new SupprimerLivre(connexionMySQL, vendeur);
            Scene scene = supprimerLivreVue.creerScene(1440, 850);
            stage.setScene(scene);
        });
        Button ModifierStock = new Button("Modifier la quantité d'un livre");
        ModifierStock.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            ModifierStock ModifierStockVue = new ModifierStock(connexionMySQL, vendeur);
            Scene scene = ModifierStockVue.creerScene(1440, 850);
            stage.setScene(scene);
        });
        Button verifierDispo = new Button("Consulter tout le stock");
        verifierDispo.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            ConsulterLivres dispoLivre = new ConsulterLivres(connexionMySQL, vendeur);
            Scene scene = dispoLivre.creerScene(1440, 850);
            stage.setScene(scene);
        });

        Button deconnexion = new Button("Déconnexion");
        deconnexion.setOnAction(new ControleurRetourAccueil());



        Utilitaire.styliserBouton(ajouteLivre);
        Utilitaire.styliserBouton(supprimerLivre);
        Utilitaire.styliserBouton(ModifierStock);
        Utilitaire.styliserBouton(verifierDispo);
        Utilitaire.styliserBoutonQuitter(deconnexion);

        milieu.getChildren().addAll(
            ajouteLivre,
            supprimerLivre,
            ModifierStock,
            verifierDispo,
            deconnexion
            
        );
        this.setTop(haut);
        this.setCenter(milieu);
        this.setPadding(new Insets(70));
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
