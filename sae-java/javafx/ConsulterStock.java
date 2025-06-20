package javafx;

import src.*;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class ConsulterStock extends VBox {

    /**
     * Constructeur de la classe ConsulterStock.
     * Affiche le stock de livres disponibles dans la librairie.
     * @param connexion La connexion à la base de données MySQL.
     */
    public ConsulterStock(ConnexionMySQL connexion) {
        super(10);  
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);
        Button quitter = new Button("Quitter");
        quitter.setOnAction(new ControleurQuitter());
        BorderPane topPane = new BorderPane();
        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        Utilitaire.styliserBoutonQuitter(quitter);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexion).creerScene(1440, 850));
        });
        HBox hbox = new HBox(10, retour, quitter);
        hbox.setAlignment(Pos.TOP_RIGHT);
        topPane.setRight(hbox);
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.getChildren().add(0, topPane);
        


        List<Livre> tousLesLivres = new ArrayList<>();
        try {
            tousLesLivres = LivreBD.chargerLivres(connexion.getConnexion());
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger les livres.");
            alert.setContentText("Une erreur est survenue lors de la connexion à la base de données ou de la récupération des livres: " + e.getMessage());
            alert.showAndWait();
        }

        Label salut = new Label("Consultation du stock :");
        salut.setStyle("-fx-font-weight: bold; -fx-font-size: 40px;");

        ScrollPane scroll = new ScrollPane();
        VBox tableauContenu = new VBox(5);
        tableauContenu.setPadding(new Insets(10));
        
        HBox entete = new HBox(20);
        entete.setPadding(new Insets(10));
        entete.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #b0b0b0; -fx-border-width: 1px; -fx-border-radius: 5px;");
        entete.setAlignment(Pos.CENTER_LEFT);

        Label txtIsbn = new Label("ISBN");
        txtIsbn.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        txtIsbn.setPrefWidth(120); 

        Label txtLivre = new Label("Titre du Livre");
        txtLivre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        txtLivre.setPrefWidth(400); 

        Label txtQte = new Label("Quantité");
        txtQte.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        txtQte.setPrefWidth(100); 
        
        entete.getChildren().addAll(txtIsbn, txtLivre, txtQte);


        tableauContenu.getChildren().add(entete);
        
       
        
        if (tousLesLivres.isEmpty()) {
            Label noBooksLabel = new Label("Aucun livre trouvé en stock.");
            noBooksLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic; -fx-text-fill: #888888; -fx-padding: 20px;");
            tableauContenu.getChildren().add(noBooksLabel);
        } else {
            for (Livre livre : tousLesLivres) { 
                HBox ligne = new HBox(20);
                ligne.setPadding(new Insets(8, 10, 8, 10)); 
                ligne.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");
                ligne.setAlignment(Pos.CENTER_LEFT);

                Label lblIsbn = new Label("" + livre.getIsbn());
                lblIsbn.setStyle("-fx-font-size: 14px;");
                lblIsbn.setPrefWidth(120);
                lblIsbn.setAlignment(Pos.CENTER_LEFT);

                Label lblTitre = new Label(livre.getTitre());
                lblTitre.setStyle("-fx-font-size: 14px;");
                lblTitre.setWrapText(true);
                lblTitre.setPrefWidth(400);
                lblTitre.setAlignment(Pos.CENTER_LEFT);

                Label lblQte = new Label("" + livre.getQuantite());
                lblQte.setStyle("-fx-font-size: 14px;");
                lblQte.setPrefWidth(100);
                lblQte.setAlignment(Pos.CENTER_LEFT);




                ligne.getChildren().addAll(lblIsbn, lblTitre, lblQte);
                
                tableauContenu.getChildren().add(ligne); 
            }
        }

        scroll.setContent(tableauContenu);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        this.getChildren().addAll(salut, scroll);
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