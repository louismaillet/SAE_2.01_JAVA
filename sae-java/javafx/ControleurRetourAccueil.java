package javafx;
import src.*;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ControleurRetourAccueil implements EventHandler<ActionEvent> {
    /**
     * Constructeur de la classe ControleurRetourAccueil.
     * Permet de gérer l'événement de retour à l'accueil.
     */
    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment vous déconnectez ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Connexion connexionApp = new Connexion();
            connexionApp.start(stage);
        }
    }
}
