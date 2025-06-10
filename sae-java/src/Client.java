import java.util.ArrayList;
import java.util.List;

public class Client extends Personne{
    private String adressecli;
    private String codepostal;
    private String villecli;
    private List<Livre> livresDejaAchetes = new ArrayList<>();


    public Client(int idcli, String nomcli, String prenomcli, String adressecli, String codepostal, String villecli) {
        super(idcli, nomcli, prenomcli);
        this.adressecli = adressecli;
        this.codepostal = codepostal;
        this.villecli = villecli;
        this.livresDejaAchetes = new ArrayList<>();
    }

    public String getAdressecli() {
        return this.adressecli;
    }

    public String getCodepostal() {
        return this.codepostal;
    }

    public String getVillecli() {
        return this.villecli;
    }
    public List<Livre> getLivresDejaAcheter() {
        return this.livresDejaAchetes;
    }
    public void addLivre(Livre livre) {
        this.livresDejaAchetes.add(livre);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Client)){
            return false;
        }
        Client tmp = (Client) obj;
        return this.nom.equals(tmp.nom) && this.prenom.equals(tmp.prenom) && this.adressecli.equals(tmp.adressecli) && this.codepostal.equals(tmp.codepostal);
    }
    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + this.nom.hashCode();
        result = 31 * result + this.prenom.hashCode();
        result = 31 * result + this.adressecli.hashCode();
        result = 31 * result + this.codepostal.hashCode();
        return result;
    }

    public List<Livre> onVousRecommande() {
        
    /*
     public List<Livre> onVousRecommande(Client client) {
    Set<Livre> livresClient = new HashSet<>(client.getLivresDejaAcheter());
    Set<Livre> recommandations = new HashSet<>();

    for (Client autreClient : this.listeClients) {
        if (autreClient.equals(client)) continue;
        // Vérifie s'il y a au moins un livre en commun
        Set<Livre> livresAutre = new HashSet<>(autreClient.getLivresDejaAcheter());
        livresAutre.retainAll(livresClient);
        if (!livresAutre.isEmpty()) {
            // Ajoute les livres de l'autre client non possédés par le client cible
            for (Livre l : autreClient.getLivresDejaAcheter()) {
                if (!livresClient.contains(l)) {
                    recommandations.add(l);
                }
            }
        }
    }
    return new ArrayList<>(recommandations);
} */
return new ArrayList<>();
}
}
