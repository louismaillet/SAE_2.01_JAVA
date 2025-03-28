public class Client {
    private int idcli;
    private String nomcli;
    private String prenomcli;
    private String adressecli;
    private String codepostal;
    private String villecli;

    public Client(int idcli, String nomcli, String prenomcli, String adressecli, String codepostal, String villecli) {
        this.idcli = idcli;
        this.nomcli = nomcli;
        this.prenomcli = prenomcli;
        this.adressecli = adressecli;
        this.codepostal = codepostal;
        this.villecli = villecli;
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
    
}
