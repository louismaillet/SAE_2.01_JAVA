package src;

import java.util.*;

public class Magasin {
    private int idmag;
    private String nommag;
    private String villemag;
    private Map<Livre,Integer> listeLivres = new HashMap<>();
    private List<Vendeur> listeEmployes = new ArrayList<>();
    

    public Magasin(int idmag, String nommag, String villemag) {
        this.idmag = idmag;
        this.nommag = nommag;
        this.villemag = villemag;
        this.listeLivres = new HashMap<>();
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

    public void addLivre(Livre livre, int quantite) {
        if (this.listeLivres.containsKey(livre)){
            this.listeLivres.put(livre, this.listeLivres.get(livre)+quantite);
        }
        else{
            listeLivres.put(livre, quantite);
        }
    }

    public Map<Livre, Integer> getListeLivres() {
        return this.listeLivres;
    }


    @Override
    public String toString() {
        return "Le magasin " + this.nommag + " est situé à " + this.villemag + " et a pour ID " + this.idmag;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (this == o){ 
            return true;
        }
        if (!(o instanceof Magasin)){ 
            return false;
        }
        Magasin magasin = (Magasin) o;
        return idmag == magasin.idmag && Objects.equals(nommag, magasin.nommag) && Objects.equals(villemag, magasin.villemag);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + idmag;
        if (nommag == null) {
            hash = 31 * hash;
        } else {
            hash = 31 * hash + nommag.hashCode();
        }
        if (villemag == null) {
            hash = 31 * hash;
        } else {
            hash = 31 * hash + villemag.hashCode();
        }
        return hash;
    }
}