import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurPasserCommande implements EventHandler<ActionEvent> {
    private ControleurPrincipal controleurPrincipal;

    public ControleurPasserCommande(ControleurPrincipal controleurPrincipal) {
        this.controleurPrincipal = controleurPrincipal;
    }

    @Override
    public void handle(ActionEvent event) {
        controleurPrincipal.afficherPasserCommande();
    }
}