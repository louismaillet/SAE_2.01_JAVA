import java.util.List;
import java.util.ArrayList;

public class Magasin {
    private int idmag;
    private String nommag;
    private String villemag;
    private List<Livre> listeLivres = new ArrayList<>();
    private List<Vendeur> listeEmployes = new ArrayList<>();
    

    public Magasin(int idmag, String nommag, String villemag) {
        this.idmag = idmag;
        this.nommag = nommag;
        this.villemag = villemag;
        this.listeLivres = new ArrayList<>();
        this.listeEmployes = new ArrayList<>();
    }

    public int getIdmag() {
        return this.idmag;
    }

    public String getNommag() {
        return this.nommag;
    }

    public String getVillemag() {
        return this.villemag;
    }

    public void addLivre(Livre livre) {
        listeLivres.add(livre);
    }

    public List<Livre> getListeLivres() {
        return this.listeLivres;
    }


    @Override
    public String toString() {
        return "Le magasin " + nommag + " est situé à " + villemag + " et a pour ID " + idmag;
    }
}
