package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurConsulterPanier implements EventHandler<ActionEvent> {
    private ControleurPrincipal controleurPrincipal;

    public ControleurConsulterPanier(ControleurPrincipal controleurPrincipal) {
        this.controleurPrincipal = controleurPrincipal;
    }

    @Override
    public void handle(ActionEvent event) {
        controleurPrincipal.afficherConsulterPanier();
    }
}