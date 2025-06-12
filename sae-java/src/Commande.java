import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Commande{
    private List<Livre> listeDesLivresCommande;
    private int numcom;
    private LocalDate date;
    private ModeReception modeDeReception;
    private Magasin magasinLivraison; //id du magasin

    public Commande (int numco, ModeReception modeDeReception){
        this.numcom = numco;
        this.date = LocalDate.now();
        this.modeDeReception = modeDeReception;
        this.listeDesLivresCommande = new ArrayList<>();
        this.magasinLivraison = new Magasin(1, "null", "null"); 

    }

    public Commande() {
        this.numcom = 0;
        this.date = LocalDate.now();
        this.modeDeReception = ModeReception.LIVRAISON;
        this.listeDesLivresCommande = new ArrayList<>();
        this.magasinLivraison = new Magasin(1, "null", "null");
    }

    public void setMagasinRetrait(Magasin magasin) {
        this.magasinLivraison = magasin;
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

        
        for (Livre livre : new HashSet<>(listeDesLivresCommande)) {
            int quantite = Collections.frequency(listeDesLivresCommande, livre);
            facture += "\n" + livre.toString().replace("Quantité: 0", "Quantité: " + quantite);
        }
        facture += "\nTotal livres : " + listeDesLivresCommande.size();
        double total = 0.0;
        for (Livre livre : listeDesLivresCommande) {
            total += livre.getPrix();
        }
        total = Math.round(total * 100.00) / 100.00;
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
        
        hash = 27 * hash + enligneValue;
        hash = 27 * hash + modeDeReception.hashCode();
        hash = 27 * hash + listeDesLivresCommande.hashCode();
        return hash;
    }
}