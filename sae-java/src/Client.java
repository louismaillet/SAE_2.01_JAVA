import java.util.ArrayList;
import java.util.List;

public class Client extends Personne{
    private String adressecli;
    private String codepostal;
    private String villecli;
    private List<Livre> livresDejaAcheter = new ArrayList<>();


    public Client(int idcli, String nomcli, String prenomcli, String adressecli, String codepostal, String villecli) {
        super(idcli, nomcli, prenomcli);
        this.adressecli = adressecli;
        this.codepostal = codepostal;
        this.villecli = villecli;
        livresDejaAcheter = new ArrayList<>();
    }

    public String getAdressecli() {
        return adressecli;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public String getVillecli() {
        return villecli;
    }
    public List<Livre> getLivresDejaAcheter() {
        return livresDejaAcheter;
    }
    public void addLivre(Livre livre) {
        livresDejaAcheter.add(livre);
    }
    
}
