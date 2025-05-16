
import java.util.*;
import java.util.List;
import java.util.Map;

public class SuperAdmin{
    private List<Magasin> listeMagasins;
    private List<Administrateur> listeAdministrateurs;
    private List<Client> listeClients;
    private List<Vendeur> listeVendeurs;
        
    
    public SuperAdmin(List<Magasin> listeMagasins, List<Administrateur> listeAdministrateurs, List<Client> listeClients, List<Vendeur> listeVendeurs){
        this.listeMagasins = listeMagasins;
        this.listeAdministrateurs = listeAdministrateurs;
        this.listeClients = listeClients;
        this.listeVendeurs = listeVendeurs;
    }
    
    public List<Livre> getListeLivres(){
        List<Livre> listeLivres = new ArrayList<>();
        for (Magasin magasin : this.listeMagasins) {
            for (Map.Entry<Livre, Integer> entry : magasin.getListeLivres().entrySet()) {
                if (entry.getValue() > 0) {
                    listeLivres.add(entry.getKey());
                }
            }
        }
        return listeLivres;
    }
}
