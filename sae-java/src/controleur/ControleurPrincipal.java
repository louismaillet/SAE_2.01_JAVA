package controleur;

import vue.VueAccueilClient;
import vue.VueConsulterPanier;
import vue.VuePasserCommande;
import vue.VueConnexionClient;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControleurPrincipal {
    private Stage primaryStage;

    public ControleurPrincipal(Stage stage) {
        this.primaryStage = stage;
    }

    public void afficherAccueilClient() {
        VueAccueilClient vue = new VueAccueilClient(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }

    public void afficherPasserCommande() {
        VuePasserCommande vue = new VuePasserCommande(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }

    public void afficherConsulterPanier() {
        VueConsulterPanier vue = new VueConsulterPanier(this);
        primaryStage.setScene(new Scene(vue, 800, 500));
    }

}