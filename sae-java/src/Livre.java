public class Livre {
    private int isbn;
    private String titre;
    private int nbPages;
    private String datePubli;
    private double prix;

    public Livre(int isbn, String titre, int nbPages, String datePubli, double prix) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    public int getNbPages() {
        return nbPages;
    }

    public String getDatePubli() {
        return datePubli;
    }

    public double getPrix() {
        return prix;
    }
    @Override
    public String toString() {
        return "Le livre " + titre + " a pour ISBN " + isbn + ", il fait " + nbPages + " pages, a été publié le " + datePubli + " et coûte " + prix + " euros";
    }
}
