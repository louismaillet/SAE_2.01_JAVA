import java.util.*;

public class SuperAdmin {
    private List<Magasin> listeMagasins;
    private List<Administrateur> listeAdministrateurs;
    private List<Client> listeClients;
    private List<Vendeur> listeVendeurs;
    private List<Livre> listeLivres = new ArrayList<>();

    public SuperAdmin(List<Magasin> listeMagasins, List<Administrateur> listeAdministrateurs, List<Client> listeClients, List<Vendeur> listeVendeurs){
        this.listeMagasins = listeMagasins;
        this.listeAdministrateurs = listeAdministrateurs;
        this.listeClients = listeClients;
        this.listeVendeurs = listeVendeurs;
    }

    public void setListeLivres(List<Livre> livres) {
        this.listeLivres = livres;
    }

    public List<Livre> getListeLivres() {
        return this.listeLivres;
    }
}