public class Magasin {
    private int idmag;
    private String nommag;
    private String villemag;

    public Magasin(int idmag, String nommag, String villemag) {
        this.idmag = idmag;
        this.nommag = nommag;
        this.villemag = villemag;
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

    // toString method
    @Override
    public String toString() {
        return "Le magasin " + nommag + " est situé à " + villemag + " et a pour ID " + idmag;
    }
}
