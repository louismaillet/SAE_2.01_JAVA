import java.util.List;
import java.util.ArrayList;

public class Magasin {
    private int idmag;
    private String nommag;
    private String villemag;
    private List<Livre> livres = new ArrayList<>();
    

    public Magasin(int idmag, String nommag, String villemag) {
        this.idmag = idmag;
        this.nommag = nommag;
        this.villemag = villemag;
        this.livres = new ArrayList<>();
    }

    public int getIdmag() {
        return idmag;
    }

    public String getNommag() {
        return nommag;
    }

    public String getVillemag() {
        return villemag;
    }

    public void addLivre(Livre livre) {
        livres.add(livre);
    }



    @Override
    public String toString() {
        return "Le magasin " + nommag + " est situé à " + villemag + " et a pour ID " + idmag;
    }
}
