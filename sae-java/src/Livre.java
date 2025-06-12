
public class Livre {
    private long isbn;
    private String titre;
    private int nbPages;
    private String datePubli;
    private double prix;
    private int quantite;

    public Livre(long isbn, String titre, int nbPages, String datePubli, double prix, int quantite) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
        this.quantite = quantite; 
    }
    // getteur setteur
    public long getIsbn() {
        return this.isbn;
    }
    public int getQuantite() {
        return this.quantite;
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

    public long getDernierISBN() {
        return this.isbn;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


@Override
public String toString() {
    String titreFormate = String.format("%-108s", this.titre);
    String isbnFormate = String.format("%-102s", this.isbn);
    String datePubliFormate = String.format("%-87s", this.datePubli);
    String prixFormate = String.format("%-102s", this.prix);
    String quantiteFormate = String.format("%-98s", this.quantite); // Placeholder for quantity

    return "\n" +
        "╔" + "═".repeat(110) + "╗\n" +
        
            
        "║ " + titreFormate + " ║\n" +
        "╚" + "═".repeat(110) + "╝\n" +
        "╔" + "═".repeat(110) + "╗\n" +
        "║ ISBN: " + isbnFormate + " ║\n" +
        "║ Pages: " + String.format("%-101s", this.nbPages) + " ║\n" +
        "║ Date de publication: " + datePubliFormate + " ║\n" +
        "║ Prix: " + prixFormate + " ║\n" +
        "║ Quantité: " + quantiteFormate + " ║\n" +
        "╚" + "═".repeat(110) + "╝\n";



           
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
    @Override
    public int hashCode() {
        return this.titre.hashCode() + this.nbPages + this.datePubli.hashCode() + (int) this.prix;
    }
	
}
