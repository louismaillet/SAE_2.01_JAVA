package src;

import java.util.*;

public class Administrateur extends Personne{
    List<Vendeur> listeVendeur = new ArrayList<>();
    List<Magasin> listeMagasin = new ArrayList<>();

    /**
     * Constructeur de la classe Administrateur.
     * @param idAdmin L'identifiant de l'administrateur.
     * @param nomAdmin Le nom de l'administrateur.
     * @param prenomAdmin Le prénom de l'administrateur.
     */
    public Administrateur(int idAdmin, String nomAdmin, String prenomAdmin){
        super(idAdmin, nomAdmin, prenomAdmin);
    }

    /**
     * Constructeur de la classe Administrateur.
     * @param nomAdmin Le nom de l'administrateur.
     * @param prenomAdmin Le prénom de l'administrateur.
     */
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
    /**
     * Ajoute un compte vendeur à la liste des vendeurs.
     * @param vendeur Le vendeur à ajouter.
     */
    public void creerCompteVendeur(Vendeur vendeur){
        this.listeVendeur.add(vendeur);
    }
    /**
     * Retourne la liste des vendeurs.
     * @return La liste des vendeurs.
     */
    public void supprimerCompteVendeur(Vendeur vendeur) {
        if (this.listeVendeur.contains(vendeur)){
            listeVendeur.remove(vendeur);
        }
    }
    /**
     * Supprime un compte vendeur de la liste des vendeurs.
     * @param nomVend Le nom du vendeur à supprimer.
     * @param prenomVend Le prénom du vendeur à supprimer.
     * @param magasin Le magasin du vendeur à supprimer.
     * @param role Le rôle du vendeur à supprimer.
     */
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
    /**
     * Retourne la liste des vendeurs.
     * @return La liste des vendeurs.
     */
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
    /**
     * Ajoute une librairie à la liste des librairies.
     * @param magasin Le magasin à ajouter.
     */
    public void ajouterLibrairie(Magasin magasin){
        listeMagasin.add(magasin);
    }
    /** 
     * Retourne la liste des librairies.
     * @return La liste des librairies.
     */
    public List<Magasin> getListeMagasin(){
        return this.listeMagasin;
    }
    /**
     * Retourne la liste des vendeurs.
     * @return La liste des vendeurs.
     *  
     * */
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