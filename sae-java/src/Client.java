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
        return this.livresDejaAcheter;
    }
    public void addLivre(Livre livre) {
        this.livresDejaAcheter.add(livre);
    }
    
}
