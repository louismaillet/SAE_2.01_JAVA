package javafx;

import src.ConnexionMySQL;
import src.MagasinBD;
import src.Vendeur;
import src.AdministrateurBD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ConsulterVendeurs extends VBox {

    /**
     * Constructeur de la classe ConsulterVendeurs.
     * Affiche la liste des vendeurs dans la base de données.
     * @param connexion La connexion à la base de données MySQL.
     */
    public ConsulterVendeurs(ConnexionMySQL connexion) {
        super(20);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #add8e6);");
        this.setAlignment(Pos.TOP_CENTER);
        this.setMaxWidth(Double.MAX_VALUE);

        Button retour = new Button("Retour");
        Utilitaire.styliserBoutonRetour(retour);
        retour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new AccueilAdmin(connexion).creerScene(1440, 850));
        });

        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
    
        BorderPane topPane = new BorderPane();
        HBox hbox = new HBox(10, retour, quitter);

        topPane.setRight(hbox);


        Label salut = new Label("Voici la liste des vendeurs :");
        salut.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-text-fill: #2c3e50;");

        ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(true);
        scroll.setFitToWidth(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox tableauContenu = new VBox(5);
        tableauContenu.setPadding(new Insets(10));
        tableauContenu.setMaxWidth(Double.MAX_VALUE);

        HBox entete = new HBox(10);
        entete.setPadding(new Insets(10));
        entete.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #b0b0b0; -fx-border-width: 1px; -fx-border-radius: 5px;");

        Label txtId = new Label("Id");
        txtId.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #333;");

        Label txtNom = new Label("Nom");
        txtNom.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #333;");

        Label txtPrenom = new Label("Prénom");
        txtPrenom.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #333;");

        Label txtLibrairie = new Label("Librairie");
        txtLibrairie.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #333;");

        entete.getChildren().addAll(txtId, txtNom, txtPrenom, txtLibrairie);
        tableauContenu.getChildren().add(entete);

        List<Vendeur> tousLesVendeurs = new ArrayList<>();
        try {
            tousLesVendeurs = AdministrateurBD.getVendeurs(connexion.getConnexion());
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger les vendeurs.");
            alert.setContentText("Une erreur est survenue lors de la connexion à la base de données ou de la récupération des vendeurs: " + e.getMessage());
            alert.showAndWait();
        }

        if (tousLesVendeurs.isEmpty()) {
            Label noVendeursLabel = new Label("Aucun vendeur trouvé.");
            noVendeursLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic; -fx-text-fill: #888888; -fx-padding: 20px;");
            tableauContenu.getChildren().add(noVendeursLabel);
        } else {
            for (Vendeur vendeur : tousLesVendeurs) {
                HBox vendeurEntry = new HBox(10);
                vendeurEntry.setPadding(new Insets(8, 10, 8, 10));
                vendeurEntry.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

                Label lblId = new Label("" + vendeur.getId());
                lblId.setStyle("-fx-font-size: 14px;");

                Label lblNom = new Label(vendeur.getNom());
                lblNom.setStyle("-fx-font-size: 14px;");

                Label lblPrenom = new Label(vendeur.getPrenom());
                lblPrenom.setStyle("-fx-font-size: 14px;");

                String nomLibrairie = "N/A";
                try {
                    if (vendeur.getMagasin() != null && connexion.getConnexion() != null) {
                        nomLibrairie = MagasinBD.getMagasinParId(connexion.getConnexion(), vendeur.getMagasin().getIdmag()).getNommag();
                    } else if (vendeur.getMagasin() == null) {
                        nomLibrairie = "Non assigné";
                    }
                } catch (NullPointerException e) {
                    System.err.println("Magasin object or its ID is null for seller " + vendeur.getId() + ": " + e.getMessage());
                    nomLibrairie = "Non assigné";
                }
                Label lblLibrairie = new Label(nomLibrairie);
                lblLibrairie.setStyle("-fx-font-size: 14px;");

                vendeurEntry.getChildren().addAll(lblId, lblNom, lblPrenom, lblLibrairie);
                tableauContenu.getChildren().add(vendeurEntry);
            }
        }

        scroll.setContent(tableauContenu);

        this.getChildren().addAll(
            topPane, salut, entete, scroll);
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
