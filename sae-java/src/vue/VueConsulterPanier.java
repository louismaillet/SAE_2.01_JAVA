package vue;

import controleur.ControleurPrincipal;
import controleur.ControleurRetourAccueil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VueConsulterPanier extends VBox {
    public VueConsulterPanier(ControleurPrincipal controleur) {
        super(20);
        setPadding(new Insets(40));
        Label label = new Label("Voici votre panier !");
        Button retour = new Button("Retour");

        Utilitaire.styliserBouton(retour);

        retour.setOnAction(new ControleurRetourAccueil(controleur));
        getChildren().addAll(label, retour);
    }
}