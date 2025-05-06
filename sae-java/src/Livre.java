public class Livre {
    private int isbn;
    private String titre;
    private int nbPages;
    private String datePubli;
    private double prix;
    private int quantite;

    public Livre(int isbn, String titre, int nbPages, String datePubli, double prix, int quantite) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
    }
    // getteur setteur
    public int getIsbn() {
        return this.isbn;
    }

    public String getTitre() {
        return this.titre;
    }

    public int getNbPages() {
        return this.nbPages;
    }

    public String getDatePubli() {
        return this.datePubli;
    }

    public double getPrix() {
        return this.prix;
    }

    public int getQuantite() {
        return this.quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    @Override
    public String toString() {
        return "Le livre " + titre + " a pour ISBN " + isbn + ", il fait " + nbPages + " pages, a été publié le " + datePubli + " et coûte " + prix + " euros";
    }
}
