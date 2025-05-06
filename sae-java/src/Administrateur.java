import java.util.*;

public class Administrateur extends Personne{
    List<Vendeur> listeVendeur = new ArrayList<>();
    List<Magasin> listeMagasin = new ArrayList<>();

    public Administrateur(int idAdmin, String nomAdmin, String prenomAdmin){
        super(idAdmin, nomAdmin, prenomAdmin);
    }

    public void creerCompteVendeur(String nomVend, String prenomVend, Magasin magasin, RoleVendeur role){
        if (listeVendeur.size() == 0){
            Vendeur vendeur = new Vendeur(0,  nomVend,  prenomVend,  magasin,  role);
        }
        else{
            Vendeur vendeur = new Vendeur(listeVendeur.get(listeVendeur.size()).getId() + 1,  nomVend,  prenomVend,  magasin,  role);
        }
    }

    public void ajouterLibrairie(int idmag, String nommag, String villemag){
        if (listeMagasin.size() == 0){
            Magasin magasin = new Magasin(0, nommag,  villemag);
        }
        else{
            Magasin magasin = new Magasin(listeMagasin.get(listeMagasin.size()).getIdmag(), nommag, villemag);
        }
    }

    public void gererStockGlobaux(){}

    public void consulterStatistique(){}


}
