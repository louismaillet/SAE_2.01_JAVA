import java.util.*;

public class Administrateur extends Personne{
    List<Vendeur> listeVendeur = new ArrayList<>();
    List<Magasin> listeMagasin = new ArrayList<>();

    public Administrateur(int idAdmin, String nomAdmin, String prenomAdmin){
        super(idAdmin, nomAdmin, prenomAdmin);
    }

    public void creerCompteVendeur(String nomVend, String prenomVend, Magasin magasin, RoleVendeur role){
        if (listeVendeur.isEmpty()){
            Vendeur vendeur = new Vendeur(0,  nomVend,  prenomVend,  magasin,  role);
            this.listeVendeur.add(vendeur);
        }
        else{
            Vendeur vendeur = new Vendeur(listeVendeur.get(listeVendeur.size()).getId() + 1,  nomVend,  prenomVend,  magasin,  role);
            this.listeVendeur.add(vendeur);
        }
    }

    public void creerCompteVendeur(Vendeur vendeur){
        this.listeVendeur.add(vendeur);
    }

    public void supprimerCompteVendeur(Vendeur vendeur) {
        if (this.listeVendeur.contains(vendeur)){
            listeVendeur.remove(vendeur);
        }
    }

    public void supprimerCompteVendeur(String nomVend, String prenomVend, Magasin magasin, RoleVendeur role) {
        Iterator<Vendeur> iterator = listeVendeur.iterator();
        while (iterator.hasNext()) {
            Vendeur v = iterator.next();
            if (v.getNom().equals(nomVend) &&
                v.getPrenom().equals(prenomVend) &&
                v.getMagasin().equals(magasin) &&
                v.getRole().equals(role)) {
                iterator.remove();
                break;
            }
        }
    }

    public void ajouterLibrairie(int idmag, String nommag, String villemag){
        if (listeMagasin.isEmpty()){
            Magasin magasin = new Magasin(0, nommag,  villemag);
            listeMagasin.add(magasin);
        }
        else{
            Magasin magasin = new Magasin(listeMagasin.get(listeMagasin.size()).getIdmag(), nommag, villemag);
            listeMagasin.add(magasin);
        }
    }

    public void ajouterLibrairie(Magasin magasin){
        listeMagasin.add(magasin);
    }

    public List<Magasin> getListeMagasin(){
        return this.listeMagasin;
    }

    public void gererStockGlobaux(){}

    public void consulterStatistique(){}






    public List<Livre> getListeLivres(){
        List<Livre> listeLivres = new ArrayList<>();
        for (Magasin magasin : listeMagasin) {
            for (Map.Entry<Livre, Integer> entry : magasin.getListeLivres().entrySet()) {
                if (entry.getValue() > 0) {
                    listeLivres.add(entry.getKey());
                }
            }
        }
        return listeLivres;
    }
}