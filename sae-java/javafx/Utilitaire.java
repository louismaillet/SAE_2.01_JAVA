package javafx;

import src.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Utilitaire {
    /**
     * Méthode pour styliser un bouton avec des effets de survol.
     * @param bouton Le bouton à styliser.
     */
    public static void styliserBouton(Button bouton) {
        String styleNormal = "-fx-background-color: orange;-fx-border-color: black; -fx-border-width: 1px;-fx-font-weight: bold; -fx-font-size: 20px; ";
        String styleSourisDessus = "-fx-background-color: gold;-fx-border-color: #ff8800; -fx-border-width: 2px;-fx-font-weight: bold; -fx-font-size: 20px;";
        bouton.setPrefWidth(1000);
        bouton.setStyle(styleNormal);
        bouton.setOnMouseEntered(e -> bouton.setStyle(styleSourisDessus));
        bouton.setOnMouseExited(e -> bouton.setStyle(styleNormal));

    }
    /**
     * Méthode pour styliser un label avec une taille de police spécifique.
     * @param label Le label à styliser.
     * @param taille La taille de la police à appliquer.
     */
    public static void styliserLabel(Label label, int taille){
        label.setStyle("-fx-font-weight: bold; -fx-font-size: "+taille+"px;");
    }
    /**
     * Méthode pour styliser un label avec une taille de police spécifique et un texte en gras.
     * @param label Le label à styliser.
     * @param taille La taille de la police à appliquer.
     */
    public static void styliserBoutonConfirmer(Button bouton){
        bouton.setStyle("-fx-font-size: 16px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        bouton.setPrefWidth(200);
    }
    /**
     * Méthode pour styliser un bouton de quitter avec une couleur et une taille spécifiques.
     * @param bouton Le bouton à styliser.
     */
    public static void styliserBoutonQuitter(Button bouton){
        bouton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        bouton.setPrefWidth(200);
    }
    /**
     * Méthode pour styliser un bouton de retour avec une couleur et une taille spécifiques.
     * @param retourChoix Le bouton de retour à styliser.
     */
    public static void styliserBoutonRetour(Button retourChoix) {
        retourChoix.setPrefWidth(200);
        retourChoix.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
    }

}