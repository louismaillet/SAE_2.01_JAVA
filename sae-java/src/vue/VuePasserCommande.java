package vue;

import controleur.ControleurPrincipal;
import controleur.ControleurRetourAccueil;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VuePasserCommande extends BorderPane {
    public VuePasserCommande(ControleurPrincipal controleur) {
        super();
        setPadding(new Insets(20));

        Label label = new Label("Quel mode de livraison souhaitez-vous ?");
        Button boutonEnMagasin = new Button("Livraison en magasin");
        Button boutonADomicile = new Button("Livraison à domicile");
        Button retour = new Button("Retour");

        Utilitaire.styliserBouton(boutonEnMagasin);
        Utilitaire.styliserBouton(boutonADomicile);
        Utilitaire.styliserBouton(retour);

        retour.setOnAction(new ControleurRetourAccueil(controleur));

        HBox hbox = new HBox(20, boutonEnMagasin, boutonADomicile);
        hbox.setPadding(new Insets(20));

        VBox vbox = new VBox(20, label, hbox, retour);
        vbox.setPadding(new Insets(20));

        setCenter(vbox);
    }
}