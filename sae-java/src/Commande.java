
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Commande{
    private List<Livre> listeDesLivresCommande;
    private int numcom;
    private LocalDate date;
    private boolean enligne;
    private ModeReception modeDeReception;

    public Commande (int numco, ModeReception modeDeReception){
        this.numcom = numco;
        this.date = LocalDate.now();
        this.modeDeReception = modeDeReception;
        this.listeDesLivresCommande = new ArrayList<>();
    }

    public Commande() {
        this.numcom = 0;
        this.date = LocalDate.now();
        this.modeDeReception = ModeReception.LIVRAISON;
        this.listeDesLivresCommande = new ArrayList<>();
    }
    public void setNumcom(Connection conn) throws SQLException {
        this.numcom = CommandeBD.getDernierIdCommande(conn) + 1;
    }
    public void setModeDeReception(ModeReception modeDeReception) {
        this.modeDeReception = modeDeReception;
    }
    public int getIdCommande() {
        return numcom;
    }
    public LocalDate getDate() {
        return date;
    }
    public void ajouterLivreACommande(Livre livre){
        listeDesLivresCommande.add(livre);
    }

    public String editerFacture(){

        String facture = "-------------------------------------------------------------------";


        facture += "\nNuméro de commande : " + numcom;
        facture += "\nDate : " + date;
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