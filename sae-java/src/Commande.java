package src;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Commande{
    private List<Livre> listeDesLivresCommande;
    private int numcom;
    private LocalDate date;
    private ModeReception modeDeReception;
    private Magasin magasinLivraison;

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

    public ModeReception getModeDeReception() {
        return modeDeReception;
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

    public List<Livre> getListeDesLivresCommande() {
        return listeDesLivresCommande;
    }

    public String editerFacture(){

        String facture = "-------------------------------------------------------------------";


        facture += "\nNumÃ©ro de commande : " + numcom;
        facture += "\nDate : " + date;
        facture += "\nMode de rÃ©ception : " + modeDeReception;
        int nombreLivre = 0;
        double factureTotale = 0.0;
        
        for (Livre livre : listeDesLivresCommande) {
            int quantite = livre.getQuantite();
            facture += "\n" + livre.toString().replace("QuantitÃ©: 0", "QuantitÃ©: " + quantite);
            factureTotale += quantite * livre.getPrix();
            nombreLivre += quantite;
        }
        
        facture += "\nTotal livres : " + nombreLivre;
        factureTotale = Math.round(factureTotale * 100.00) / 100.00;
        facture += "\nTotal facture : " + factureTotale + " â¬";

        
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

    public void ajouterLivreACommande(Livre livreAchete, int quantite) {
        
        livreAchete.setQuantite(quantite);
        this.listeDesLivresCommande.add(livreAchete);
    }

    public Magasin getMagasin() {
        return magasinLivraison;
    }

    public int getQuantiteLivreDansPanier(long isbn) {
        int quantite = 0;
        for (Livre livre : listeDesLivresCommande) {
            if (livre.getIsbn() == isbn) {
                quantite += livre.getQuantite();
            }
        }
        return quantite;
    }
    
}