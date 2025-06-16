package controleur;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleurPrincipal {
    private Stage primaryStage;

    public ControleurPrincipal(Stage stage) {
        this.primaryStage = stage;
    }

    public void afficherAccueil() {
        Accueil vue = new Accueil(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }

    public void afficherPasserCommande() {
        PasserCommande vue = new PasserCommande(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }

    public void afficherConsulterPanier() {
        ConsulterPanier vue = new ConsulterPanier(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }
}