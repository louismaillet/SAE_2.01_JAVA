package src;

public class Vendeur extends Personne{
    private Magasin magasin;
    private RoleVendeur role;
    public Vendeur(int idVend, String nomVend, String prenomVend, Magasin magasin, RoleVendeur role) {
        super(idVend, nomVend, prenomVend);
        this.magasin = magasin;
        this.role = role;
    }

    Vendeur(int idVend, String nomVend, String prenomVend) {
        super(idVend, nomVend, prenomVend);
        this.magasin = null; 
        this.role = RoleVendeur.VENDEUR; 
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public String getNom() {
        return this.nom;
    }
    
    public Magasin getMagasin() {
        return this.magasin;
    }
    public RoleVendeur getRole() {
        return this.role;
    }
    public void ajouterLivreStock(long isbn, String titre, String auteur, String editeur, int annee, int prix, int quantite, int nbPages, int datePubli) {
        Livre LivreAjouter = new Livre(isbn,  titre,  nbPages,  datePubli,  prix,  quantite);
        boolean livreExistant = false;
        for (Livre livre : magasin.getListeLivres().keySet()) {
            if (livre.getIsbn() == isbn) {
                magasin.getListeLivres().put(livre, magasin.getListeLivres().get(livre) + quantite);
                livreExistant = true;
                break;
            }
        }
        if (!livreExistant) {
            magasin.getListeLivres().put(LivreAjouter, quantite);
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vendeur)){
            return false;
        }
        Vendeur tmp = (Vendeur) obj;
        return this.nom.equals(tmp.nom) && this.prenom.equals(tmp.prenom) && this.magasin.equals(tmp.magasin) && this.role.equals(tmp.role);
    }
}

