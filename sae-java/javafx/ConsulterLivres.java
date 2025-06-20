package javafx;

import src.*;

import java.util.*;
    
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ConsulterLivres extends VBox {

    /**
     * Constructeur de la classe ConsulterLivres.
     * Affiche la liste des livres disponibles dans la librairie du vendeur.
     * @param connexion La connexion à la base de données MySQL.
     * @param vendeur Le vendeur connecté.
     */
    public ConsulterLivres(ConnexionMySQL connexion, Vendeur vendeur) {
        super(10);  
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);
        Button quitter = new Button("Quitter");
        quitter.setOnAction(new ControleurQuitter());
        Utilitaire.styliserBoutonQuitter(quitter);


        Button retour = new Button("Retour");
         retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilVendeur(vendeur, connexion).creerScene(1440, 850));
        });
        Utilitaire.styliserBoutonRetour(retour);
        HBox hbox = new HBox(10, retour, quitter);
        hbox.setAlignment(Pos.TOP_RIGHT);
        BorderPane topPane = new BorderPane();
        topPane.setRight(hbox);
        BorderPane.setAlignment(quitter, Pos.TOP_RIGHT);
        this.getChildren().add(0, topPane);

        Label salut = new Label("Voici la liste des livres de votrer librairie : " + vendeur.getMagasin().getNommag());
        salut.setStyle("-fx-font-weight: bold;-fx-font-size: 40px;");

        Label txtIsbn = new Label("ISBN :");
        txtIsbn.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
    
        Label txtNom = new Label("Titre :");
        txtNom.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        Label txtPages = new Label("Pages :");
        txtPages.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        Label txtDate = new Label("Date de publication :");
        txtDate.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
        txtDate.setWrapText(true);

        Label txtPrix = new Label("Prix :");
        txtPrix.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        Label txtQte = new Label("Quantité :");
        txtQte.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        ScrollPane scroll = new ScrollPane();
        VBox fauxtableau = new VBox(10);
        
        GridPane entete = new GridPane();

        List<Livre> tousLesLivres = LivreBD.chargerLivresParMagasin(connexion.getConnexion(), vendeur.getMagasin().getIdmag());
        System.out.println("Nombre de livres : " + tousLesLivres.size());
        
        for (int i = 0; i < 5; i++) {
            ColumnConstraints colonne = new ColumnConstraints();
            colonne.setMinWidth(50);
            colonne.setPrefWidth(150);
            entete.getColumnConstraints().add(colonne);
        }
        
        entete.add(txtIsbn, 0, 0);
        entete.add(txtNom, 1, 0);
        entete.add(txtPages, 2, 0);
        entete.add(txtDate, 3, 0);
        entete.add(txtPrix, 4, 0);
        entete.add(txtQte, 5, 0);
        entete.setHgap(50);
        

        fauxtableau.getChildren().add(entete);
        for (int lig = 0; lig < tousLesLivres.size(); lig++) {
            GridPane ligne = new GridPane();
            ligne.setStyle("-fx-border-color: gray;" +"-fx-border-width: 1px;" +"-fx-background-radius: 10px;" +"-fx-border-radius: 10px;" +"-fx-padding: 5px;");   
            
            for (int i = 0; i < 5; i++) {
                ColumnConstraints colonne = new ColumnConstraints();
                colonne.setMinWidth(100);
                colonne.setPrefWidth(200);
                ligne.getColumnConstraints().add(colonne);
            }
            
            for (int col = 0; col < 6; col++) {
                if (col == 0){
                    Label label = new Label(String.valueOf(tousLesLivres.get(lig).getIsbn()));
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                    continue;
                }
                if (col == 1){
                    Label label = new Label(tousLesLivres.get(lig).getTitre());
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                    continue;
                }
                if (col == 2){
                    Label label = new Label(String.valueOf(tousLesLivres.get(lig).getNbPages()));
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                    continue;
                }
                if (col == 3){
                    Label label = new Label(String.valueOf(tousLesLivres.get(lig).getDatePubli()));
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                    continue;
                }
                if (col == 4){
                    Label label = new Label(String.valueOf(tousLesLivres.get(lig).getPrix()));
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                    continue;
                }
                if (col == 5){
                    Label label = new Label(String.valueOf(tousLesLivres.get(lig).getQuantite()));
                    label.setStyle("-fx-font-size: 15px;");
                    ligne.add(label, col, 0);
                }
            }
            
            fauxtableau.getChildren().add(ligne); 
        }

        scroll.setContent(fauxtableau);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(true);

        this.getChildren().addAll(salut, scroll);
    }

    /**
     * Crée une scène pour la fenêtre de consultation des livres.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return La scène créée.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }
}