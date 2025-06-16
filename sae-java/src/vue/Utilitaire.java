package vue;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Utilitaire {
    public static void styliserBouton(Button bouton) {
        String styleNormal = "-fx-background-color: orange;-fx-border-color: black; -fx-border-width: 1px;-fx-font-weight: bold; -fx-font-size: 20px; ";
        String styleSourisDessus = "-fx-background-color: gold;-fx-border-color: #ff8800; -fx-border-width: 2px;-fx-font-weight: bold; -fx-font-size: 20px;";
        bouton.setPrefWidth(200);
        bouton.setStyle(styleNormal);
        bouton.setOnMouseEntered(e -> bouton.setStyle(styleSourisDessus));
        bouton.setOnMouseExited(e -> bouton.setStyle(styleNormal));

    }
    public static void styliserLabel(Label label, int taille){
        label.setStyle("-fx-font-weight: bold; -fx-font-size: "+taille+"px;");
    }
    public static void styliserBoutonRetour(Button bouton){
        bouton.setPrefWidth(200);
        bouton.setStyle("-fx-font-size: 16px; -fx-background-color: orange; -fx-text-fill: black; -fx-font-weight: bold;");
    }
    public static void styliserBoutonConfirmer(Button bouton){
        bouton.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        bouton.setPrefWidth(200);
    }
    public static void styliserBoutonQuitter(Button bouton){
        bouton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        bouton.setPrefWidth(200);
    }
}