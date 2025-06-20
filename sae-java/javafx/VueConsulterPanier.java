package javafx;

import src.*;

import java.util.HashMap;
import java.util.Iterator; 
import java.util.Map;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VueConsulterPanier extends BorderPane {

    private Client client;
    private ConnexionMySQL connexionMySQL;
    private Commande commande;
    private VBox contenuPanier; 
    /**
     * Constructeur de la classe VueConsulterPanier.
     * Permet à un client de consulter son panier et de valider sa commande.
     * @param client Le client connecté.
     * @param connexionMySQL La connexion à la base de données MySQL.
     * @param commande La commande en cours du client.
     */
    public VueConsulterPanier(Client client, ConnexionMySQL connexionMySQL, Commande commande) {
        super();
        this.client = client;
        this.connexionMySQL = connexionMySQL;
        this.commande = commande;
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
        contenuPanier = new VBox(15);
        contenuPanier.setAlignment(Pos.TOP_CENTER);
        contenuPanier.setPadding(new Insets(20));
        contenuPanier.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");

        this.setPadding(new Insets(20));
        Label titre = new Label("Votre Panier, " + client.getPrenom() + " " + client.getNom() + " !");
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 30px; -fx-text-fill: #333333;");
        Button quitter = new Button("Quitter");
        Utilitaire.styliserBoutonQuitter(quitter);
        quitter.setOnAction(new ControleurQuitter());
        BorderPane top = new BorderPane();
        top.setRight(quitter);
        top.setCenter(titre);
        top.setAlignment(titre, Pos.TOP_CENTER);        
        top.setAlignment(quitter, Pos.TOP_RIGHT); 
        this.setTop(top);

        Label modeReception = new Label();
        modeReception.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #555555;"); 

        Button boutonRetour = new Button("Retour aux livres");
        boutonRetour.setPrefWidth(200); 
        Utilitaire.styliserBoutonRetour(boutonRetour);

        if (this.commande.getModeDeReception() == ModeReception.ENMAGASIN) {
            modeReception.setText("Mode de réception : En magasin");
            boutonRetour.setOnAction(e -> {
                Stage stage = (Stage) this.getScene().getWindow();
                VueAcheterLivreMagasin vueAcheterLivre = new VueAcheterLivreMagasin(this.client, this.commande.getMagasin(), this.connexionMySQL, this.commande);
                Scene scene = vueAcheterLivre.creerScene(1440, 850);
                stage.setScene(scene);
            });
        } else {
            modeReception.setText("Mode de réception : À domicile");
            boutonRetour.setOnAction(e -> {
                Stage stage = (Stage) this.getScene().getWindow();
                VueAcheterLivreDomicile vueAcheterLivre = new VueAcheterLivreDomicile(this.client, this.connexionMySQL, this.commande);
                Scene scene = vueAcheterLivre.creerScene(1440, 850);
                stage.setScene(scene);
            });
        }

        Label titreContenu = new Label("Contenu de votre panier :");
        titreContenu.setStyle("-fx-font-weight: bold; -fx-font-size: 30px; -fx-padding: 10 0 0 0;");
        contenuPanier.getChildren().addAll(titreContenu, modeReception);

        reconstruireContenuPanier();
        
        setCenter(contenuPanier);
        
        Button boutonValiderPayer = new Button("Valider et Payer");
        boutonValiderPayer.setPrefWidth(200); 
        Utilitaire.styliserBoutonConfirmer(boutonValiderPayer);

        boutonValiderPayer.setOnAction(e -> {
            if (commande.getListeDesLivresCommande().isEmpty()) { 
                afficherAlerte(Alert.AlertType.WARNING, "Panier vide", null, "Votre panier est vide. Impossible de valider la commande.");
                return;
            }

            try {
                Map<Livre, Integer> livresPourValidation = new HashMap<>();
                for (Livre l : commande.getListeDesLivresCommande()) {
                    livresPourValidation.put(l, livresPourValidation.getOrDefault(l, 0) + 1);
                }

                int idMagasin = -1;
                if (commande.getModeDeReception() == ModeReception.ENMAGASIN && commande.getMagasin() != null) {
                    idMagasin = commande.getMagasin().getIdmag();
                } else if (commande.getModeDeReception() == ModeReception.LIVRAISON) {
                    for (Livre livre : livresPourValidation.keySet()) {
                        for (Magasin m : MagasinBD.chargerMagasins(connexionMySQL.getConnexion())) {
                            if (MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), m.getIdmag(), livre.getIsbn()) > 0) {
                                
                            }
                        }
                    }
                }

                if (idMagasin == -1) {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur magasin", null, "Impossible de déterminer le magasin pour la réduction du stock.");
                    return;
                }

                for (Map.Entry<Livre, Integer> entry : livresPourValidation.entrySet()) {
                    Livre livre = entry.getKey();
                    Integer quantiteDemandee = entry.getValue();

                    int qteActuelle = MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), idMagasin, livre.getIsbn());
                    if (qteActuelle < quantiteDemandee) {
                        afficherAlerte(Alert.AlertType.ERROR, "Stock insuffisant", null, "Stock insuffisant pour le livre : '" + livre.getTitre() + "'. Quantité disponible : " + qteActuelle + ", demandée : " + quantiteDemandee + ".");
                        return; 
                    }
                }

                for (Map.Entry<Livre, Integer> entry : livresPourValidation.entrySet()) {
                    Livre livre = entry.getKey();
                    Integer quantiteAEnlever = entry.getValue();
                    
                    LivreBD.modifierQteLivre(connexionMySQL.getConnexion(), livre.getIsbn(), idMagasin, quantiteAEnlever);
                }
                
                afficherAlerte(Alert.AlertType.INFORMATION, "Commande validée", null, "Votre commande a été validée et payée avec succès !");
                
                commande.getListeDesLivresCommande().clear();

                Stage stage = (Stage) this.getScene().getWindow();
                AccueilClient accueilClient = new AccueilClient(client, connexionMySQL);
                Scene sceneAccueil = accueilClient.creerScene(1440, 850);
                stage.setScene(sceneAccueil);
                stage.setTitle("Accueil Client - " + client.getPrenom() + " " + client.getNom());
                stage.setResizable(true);

            } catch (Exception ex) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur inattendue est survenue.", "Détails : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        HBox bas = new HBox(30, boutonRetour, boutonValiderPayer);
        bas.setAlignment(Pos.CENTER);
        bas.setPadding(new Insets(20));
        setBottom(bas);
    
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f2ff, #aaccff);");
    }
    /**
     * Reconstruit le contenu du panier en fonction des livres dans la commande.
     * Met à jour l'affichage pour refléter les livres présents dans le panier.
     */
    private void reconstruireContenuPanier() {
        contenuPanier.getChildren().clear(); 
        
        Label titreContenu = new Label("Contenu de votre panier :");
        titreContenu.setStyle("-fx-font-weight: bold; -fx-font-size: 35px; -fx-padding: 10 0 0 0;");
        contenuPanier.getChildren().add(titreContenu);

        Label modeReception = new Label();
        Label endroitReception = new Label();
        modeReception.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #555555;");
        endroitReception.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #555555;");
        if (this.commande.getModeDeReception() == ModeReception.ENMAGASIN) {
            modeReception.setText("Mode de réception : En magasin");
            endroitReception.setText("Magasin : " + this.commande.getMagasin().getNommag());
        } else {
            modeReception.setText("Mode de réception : À domicile");
            endroitReception.setText("Adresse : " + this.client.getAdressecli());

        }
        contenuPanier.getChildren().addAll(modeReception,endroitReception);


        Map<Livre, Integer> dicoLivres = new HashMap<>();
        for (Livre element : commande.getListeDesLivresCommande()) {
            dicoLivres.put(element, dicoLivres.getOrDefault(element, 0) + 1);
        }

        double prixTotal = 0.0; 
        int nbLivre = 0;

        if (dicoLivres.isEmpty()) {
            Label panierVideLabel = new Label("Votre panier est vide. Pensez à acheter un petit livre !");
            panierVideLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic; -fx-text-fill: #888888;");
            contenuPanier.getChildren().add(panierVideLabel);
        } else {
            for (Map.Entry<Livre, Integer> entry : dicoLivres.entrySet()) {
                Livre livre = entry.getKey();
                Integer quantite = entry.getValue();
                
                HBox ligneLivre = new HBox(10);
                ligneLivre.setAlignment(Pos.CENTER);

                Label livreLabel = new Label(livre.getTitre() + " - " + String.format("%.2f", livre.getPrix()) + " € (x" + quantite + ")");
                livreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #444444; -fx-min-width: 400px;"); // Largeur minimale pour l'alignement

                Button boutonMoins = new Button("-");
                boutonMoins.setStyle("-fx-font-size: 14px; -fx-pref-width: 30px; -fx-pref-height: 30px; -fx-background-color: #f0ad4e; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                boutonMoins.setOnAction(e -> {
                    boolean suppr = commande.getListeDesLivresCommande().remove(livre);
                    if (suppr) {
                        reconstruireContenuPanier(); 
                    }
                });

                Button boutonPlus = new Button("+");
                boutonPlus.setStyle("-fx-font-size: 14px; -fx-pref-width: 30px; -fx-pref-height: 30px; -fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                boutonPlus.setOnAction(e -> {
                    if (commande.getModeDeReception() == ModeReception.ENMAGASIN && commande.getMagasin() != null) {
                        try {
                            int idMagasin = commande.getMagasin().getIdmag();
                            int qteActuelleEnStock = MagasinBD.getQuantiteLivre(connexionMySQL.getConnexion(), idMagasin, livre.getIsbn());

                            int qteDansPanier = 0;
                            for (Livre l : commande.getListeDesLivresCommande()) {
                                if (l.equals(livre)) { 
                                    qteDansPanier++;
                                }
                            }

                            if (qteDansPanier < qteActuelleEnStock) {
                                commande.getListeDesLivresCommande().add(livre);
                                reconstruireContenuPanier(); 
                            } else {
                                afficherAlerte(Alert.AlertType.WARNING, "Stock Limité", null, "Impossible d'ajouter plus d'exemplaires de '" + livre.getTitre() + "'. Stock maximum atteint pour ce magasin.");
                            }
                        } catch (Exception ex) { 
                            afficherAlerte(Alert.AlertType.ERROR, "Erreur de stock", null, "Une erreur est survenue lors de la vérification du stock : " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    } else {
                        commande.getListeDesLivresCommande().add(livre);
                        reconstruireContenuPanier(); 
                    }
                });
                
                ligneLivre.getChildren().addAll(livreLabel, boutonMoins, boutonPlus);
                contenuPanier.getChildren().add(ligneLivre);

                prixTotal += (livre.getPrix() * quantite);
                nbLivre += quantite;
            }
        }
        
        Label totalLabel = new Label("Total : " + String.format("%.2f", prixTotal) + " € pour " + nbLivre + " livre(s)");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #006600; -fx-padding: 15 0 0 0;");

        contenuPanier.getChildren().add(totalLabel);
    }

    /**
     * Crée une scène pour la vue ConsulterPanier.
     * @param largeur La largeur de la scène.
     * @param hauteur La hauteur de la scène.
     * @return Une nouvelle instance de Scene avec cette vue.
     */
    public Scene creerScene(double largeur, double hauteur) {
        return new Scene(this, largeur, hauteur);
    }

    /**
     * Affiche une alerte avec le type, titre, en-tête et contenu spécifiés.
     * @param type Le type d'alerte (par exemple, Alert.AlertType.INFORMATION, Alert.AlertType.ERROR).
     * @param titre Le titre de l'alerte.
     * @param entete L'en-tête de l'alerte.
     * @param contenu Le contenu de l'alerte.
     */
    private void afficherAlerte(Alert.AlertType type, String titre, String entete, String contenu) {
        Alert alerte = new Alert(type);
        alerte.setTitle(titre);
        alerte.setHeaderText(entete);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }
}