
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
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o){
            return true;
        }
        if (!(o instanceof Commande)) return false;
        Commande commande = (Commande) o;
        return numcom == commande.numcom &&
               enligne == commande.enligne &&
               Objects.equals(date, commande.date) &&
               Objects.equals(listeDesLivresCommande, commande.listeDesLivresCommande) &&
               modeDeReception == commande.modeDeReception;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 27 * hash + numcom;
        hash = 27 * hash + date.hashCode();
        int enligneValue = 0;
        if (enligne) {
            enligneValue = 1;
        }
        hash = 27 * hash + enligneValue;
        hash = 27 * hash + modeDeReception.hashCode();
        hash = 27 * hash + listeDesLivresCommande.hashCode();
        return hash;
    }
}