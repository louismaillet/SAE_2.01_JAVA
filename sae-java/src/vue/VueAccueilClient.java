package vue;

import controleur.ControleurPrincipal;
import controleur.ControleurPasserCommande;
import controleur.ControleurConsulterPanier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class VueAccueilClient extends BorderPane {
    public VueAccueilClient(ControleurPrincipal controleur) {
        super();

        Label bonjour = new Label("Bonjour + faudra mettre le blaze du pelo !");
        bonjour.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
        this.setTop(bonjour);

        VBox mid = new VBox(50);
        mid.setPadding(new Insets(70));
        mid.setAlignment(Pos.CENTER);
        mid.setStyle("-fx-border-color: black; -fx-border-width: 3px;");

        Button commande = new Button("Passer une commande");
        Button panier = new Button("Consulter mon panier");
        commande.setPrefSize(300, 75);
        panier.setPrefSize(300, 75);

        Utilitaire.styliserBouton(commande);
        Utilitaire.styliserBouton(panier);

        commande.setOnAction(new ControleurPasserCommande(controleur));
        panier.setOnAction(new ControleurConsulterPanier(controleur));

        mid.getChildren().addAll(commande, panier);
        this.setCenter(mid);
        this.setPadding(new Insets(70));
    }
}