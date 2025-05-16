
import java.util.*;

public class Commande{
    private List<Livre> listeDesLivresCommande;
    private int numcom;
    private String date;
    private boolean enligne;
    private ModeReception modeDeReception;

    public Commande (int numco, String date, boolean enligne, ModeReception modeDeReception){
        this.numcom = numco;
        this.date = date;
        this.enligne = enligne;
        this.modeDeReception = modeDeReception;
        this.listeDesLivresCommande = new ArrayList<>();
    }

    public void ajouterLivreACommande(Livre livre){
        listeDesLivresCommande.add(livre);
    }

    public String editerFacture(){

        
        String facture = "-------------------------------------------------------------------";

        facture += "\nNuméro de commande : " + numcom;
        facture += "\nDate : " + date;
        facture += "\nCommande en ligne : " + (enligne ? "Oui" : "Non");
        facture += "\nMode de réception : " + modeDeReception;

        
        for (Livre livre : listeDesLivresCommande) {
            facture += "\n" + livre.toString();
        }
        facture += "\nTotal livres : " + listeDesLivresCommande.size();
        double total = 0.0;
        for (Livre livre : listeDesLivresCommande) {
            total += livre.getPrix();
        }
        facture += "\nTotal facture : " + total + " €";

        
        facture += "\n-------------------------------------------------------------------";
        return facture;
    }
}