package javafx;

import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class ControleurQuitter implements EventHandler<ActionEvent> {
    /**
     * Constructeur de la classe ControleurQuitter.
     * Permet de gérer l'événement de fermeture de l'application.
     */
    public ControleurQuitter() {
    }
    /**
     * Méthode qui gère l'événement de fermeture de l'application.
     * Affiche une boîte de dialogue de confirmation avant de fermer l'application.
     *
     * @param event L'événement déclenché par le bouton "Quitter".
     */
    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir quitter l'application ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation de sortie");
        alert.setHeaderText(null);

        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}