package javafx;

import src.*;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos; 
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class ConsulterLibrairies extends VBox {

    /**
     * Constructeur de la classe ConsulterLibrairies.
     * Affiche la liste des librairies disponibles dans la base de données.
     * @param connexion La connexion à la base de données MySQL.
     */
    public ConsulterLibrairies(ConnexionMySQL connexion) {        
        super(20);
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px;"); 
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);"); 
        this.setPadding(new Insets(30)); 


        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());

        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexion).creerScene(1440, 850));
        });
        
        HBox hbox = new HBox(10, retour, quitter);  

        BorderPane topPane = new BorderPane();
        topPane.setRight(hbox);

        Label salut = new Label("Voici la liste des Librairies :");
        salut.setStyle("-fx-font-weight: bold;-fx-font-size: 40px;");
        VBox.setMargin(salut, new Insets(20, 0, 20, 0)); 

        ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(true);
        scroll.setFitToWidth(true);


        VBox tableauContenu = new VBox(5); 
        tableauContenu.setPadding(new Insets(10));

        

        HBox entete = new HBox(10); 
        entete.setPadding(new Insets(10));
        entete.setStyle("-fx-border-color: gray;" +"-fx-border-width: 1px;" +"-fx-background-radius: 10px;" +"-fx-border-radius: 10px;" +"-fx-padding: 5px;"); 

        

        Label texteEntete = new Label("id, Nom de la Librairie, Ville de la Librairie");
        texteEntete.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
        texteEntete.setWrapText(true);

        entete.getChildren().addAll(texteEntete);
        tableauContenu.getChildren().add(entete);

        List<Magasin> listeLibrairies = MagasinBD.chargerMagasins(connexion.getConnexion());  
        
        Image image = new Image("file:../../img/magasin.png"); 

        for (int lig = 0; lig < listeLibrairies.size(); lig++) {
            HBox ligne = new HBox(10); 
            ligne.setPadding(new Insets(8, 10, 8, 10));
            ligne.setStyle("-fx-border-color: gray;" +"-fx-border-width: 1px;" +"-fx-background-radius: 10px;" +"-fx-border-radius: 10px;" +"-fx-padding: 5px;");

            String nomMag = listeLibrairies.get(lig).getNommag();
            String idMag = String.valueOf(listeLibrairies.get(lig).getIdmag());
            String villeMag = listeLibrairies.get(lig).getVillemag();

            Label labelId = new Label(idMag);
            labelId.setStyle("-fx-font-size: 15px;");

            Label labelNom = new Label(nomMag);
            labelNom.setStyle("-fx-font-size: 15px;");
            labelNom.setWrapText(true); 

            Label labelVille = new Label(villeMag);
            labelVille.setStyle("-fx-font-size: 15px;");
            labelVille.setWrapText(true); 
            
            ImageView imgLivre = new ImageView(image);
            imgLivre.setFitWidth(40);
            imgLivre.setFitHeight(40);

            ligne.getChildren().addAll(labelId, labelNom, labelVille, imgLivre);
            tableauContenu.getChildren().add(ligne); 
        }

        scroll.setContent(tableauContenu);

        this.getChildren().addAll(topPane, salut, scroll);
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