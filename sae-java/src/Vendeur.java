public class Vendeur extends Personne{
    private Magasin magasin;
    private RoleVendeur role;
    public Vendeur(int idVend, String nomVend, String prenomVend, Magasin magasin, RoleVendeur role) {
        super(idVend, nomVend, prenomVend);
        this.magasin = magasin;
        this.role = RoleVendeur.role;
    }
    public Magasin getMagasin() {
        return this.magasin;
    }
    public RoleVendeur getRole() {
        return this.role;
    }
    public void ajouterLivreStock(int isbn, String titre, String auteur, String editeur, int annee, int prix, int quantite) {
        Livre LivreAjouter = new Livre(isbn, titre, auteur, editeur, annee, prix, quantite);
        boolean livreExistant = false;
        for (Livre livre : magasin.getLivres()) {
            if (livre.getIsbn() == isbn) {
            livre.setQuantite(quantite);
            livreExistant = true;
            break;
            }
        }
        if (!livreExistant) {
            magasin.addLivre(LivreAjouter);
        }
    }        
}
