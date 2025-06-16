import controleur.ControleurPrincipal;

import javafx.application.Application;
import javafx.stage.Stage;

public class Executer extends Application {
    @Override
    public void start(Stage primaryStage) {
        ControleurPrincipal controleur = new ControleurPrincipal(primaryStage);
        controleur.afficherAccueil();
        primaryStage.setTitle("Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}