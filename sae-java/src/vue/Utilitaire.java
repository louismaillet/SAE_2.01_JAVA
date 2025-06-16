import javafx.scene.control.Button;

public class Utilitaire {
    public static void styliserBouton(Button bouton) {
        String styleNormal = "-fx-background-color: orange;-fx-border-color: black; -fx-border-width: 1px;-fx-font-weight: bold; -fx-font-size: 20px; ";
        String styleHover = "-fx-background-color: gold;-fx-border-color: #ff8800; -fx-border-width: 2px;-fx-font-weight: bold; -fx-font-size: 20px;";
        bouton.setStyle(styleNormal);
        bouton.setOnMouseEntered(e -> bouton.setStyle(styleHover));
        bouton.setOnMouseExited(e -> bouton.setStyle(styleNormal));

    }
}