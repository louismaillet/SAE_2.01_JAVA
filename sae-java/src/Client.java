package src;
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

    public String getNomcli() {
        return this.nom;
    }
    public String getPrenomcli() {
        return this.prenom;
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

    public int getDernierIdClient() {
        return this.getId();
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

}
