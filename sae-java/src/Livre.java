public class Livre {
    private long isbn;
    private String titre;
    private int nbPages;
    private String datePubli;
    private double prix;

    public Livre(long isbn, String titre, int nbPages, String datePubli, double prix, int quantite) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
    }
    // getteur setteur
    public long getIsbn() {
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
    @Override
    public String toString() {
        return this.titre + "\n  ISBN       : " + this.isbn + "\n  Publié le  : " + this.datePubli + "\n  Prix       : " + String.format("%.2f", this.prix) + " €\n--------------------------------\n";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Livre)){
            return false;
        }
        Livre tmp = (Livre) obj;
        return this.titre.equals(tmp.titre) && this.nbPages == tmp.nbPages && this.datePubli.equals(tmp.datePubli) && this.prix == tmp.prix;
    }

}
