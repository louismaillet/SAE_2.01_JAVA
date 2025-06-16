package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurRetourAccueil implements EventHandler<ActionEvent> {
    private ControleurPrincipal controleurPrincipal;

    public ControleurRetourAccueil(ControleurPrincipal controleurPrincipal) {
        this.controleurPrincipal = controleurPrincipal;
    }

    @Override
    public void handle(ActionEvent event) {
        controleurPrincipal.afficherAccueil();
    }
}