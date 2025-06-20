package javafx;

import src.*;
import java.util.*;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class ConsulterStats extends VBox {
    /**
     * Constructeur de la classe ConsulterStats.
     * Affiche les statistiques de vente de la librairie.
     * @param connexion La connexion à la base de données MySQL.
     */
    public ConsulterStats(ConnexionMySQL connexion) {
        super(20);  
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        Button quitter = new Button("Quitter");
        quitter.setOnAction(new ControleurQuitter());
        BorderPane topPane = new BorderPane();
        Button retour = new Button("Retour");
         retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexion).creerScene(1440, 850));
        });
        Utilitaire.styliserBoutonRetour(retour);
        Utilitaire.styliserBoutonQuitter(quitter);
        HBox hbox = new HBox(10, retour, quitter);
        topPane.setRight(hbox);
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.getChildren().add(0, topPane);
        Double chiffreAffaire = 0.0;
        try {chiffreAffaire = AdministrateurBD.getChiffreAffairesTotal(connexion.getConnexion());
        } catch (Exception e) {
            e.printStackTrace();
           
        }
        
        
            
        Label txtCA = new Label("Chiffre d’affaire total : " + chiffreAffaire + " €");
        txtCA.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        Label annonceTablo = new Label("Voici les livres les plus vendus : ");
        annonceTablo.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");


        Label txtLivre = new Label("Titre :");
        txtLivre.setStyle(" -fx-font-size: 25px;");
        Label txtQte = new Label("Exemplaires vendus :");
        txtQte.setStyle("-fx-font-size: 25px;");
        VBox fauxtableau = new VBox(10);
        
        HBox entete = new HBox(400);
        entete.setStyle("-fx-border-color: gray;" +
                        "-fx-border-width: 1px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-padding: 5px;" +
                        "-fx-background-color: white;");           
    
    
        entete.getChildren().addAll(txtLivre,txtQte);
        entete.setMaxWidth(1000);
        fauxtableau.getChildren().add(entete);

        List<String> livrePlusVendus = new ArrayList<>();
        try {
            livrePlusVendus = AdministrateurBD.getLivresPlusVendus(connexion.getConnexion());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int lig = 0; lig < 10; lig++) {
            HBox ligne = new HBox(50);
            ligne.setStyle("-fx-border-color: gray;" +
                        "-fx-border-width: 1px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-padding: 5px;" +
                        "-fx-background-color: white;");
            ligne.setMaxWidth(1000);
            ligne.setAlignment(Pos.CENTER_LEFT);

            String[] parts = livrePlusVendus.get(lig).split("-", 2);

            Label label = new Label(parts[0]);
            Label label2 = new Label(parts[1]);

            // Fixe une largeur préférée à chaque label pour un bon alignement
            label.setPrefWidth(500);
            label.setStyle("-fx-font-size: 15px;");
            label2.setPrefWidth(500);
            label2.setStyle("-fx-font-size: 15px;");

            ligne.getChildren().addAll(label, label2);
            fauxtableau.getChildren().add(ligne);
        }

    
                this.getChildren().addAll(txtCA,annonceTablo,fauxtableau);
    }

    /**
     * Méthode pour créer une scène avec les dimensions spécifiées.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}
