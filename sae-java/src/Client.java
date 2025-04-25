import java.util.ArrayList;
import java.util.List;

public class Client {
    private int idcli;
    private String nomcli;
    private String prenomcli;
    private String adressecli;
    private String codepostal;
    private String villecli;
    private List<Livre> livresDejaAcheter = new ArrayList<>();


    public Client(int idcli, String nomcli, String prenomcli, String adressecli, String codepostal, String villecli) {
        this.idcli = idcli;
        this.nomcli = nomcli;
        this.prenomcli = prenomcli;
        this.adressecli = adressecli;
        this.codepostal = codepostal;
        this.villecli = villecli;
        livresDejaAcheter = new ArrayList<>();
    }

    public int getIdcli() {
        return idcli;
    }

    public String getNomcli() {
        return nomcli;
    }

    public String getPrenomcli() {
        return prenomcli;
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
