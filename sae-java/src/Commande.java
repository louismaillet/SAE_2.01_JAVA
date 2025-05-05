public class Commande{
    private int numcom;
    private String date;
    private boolean enligne;
    private ModeReception M2R;

    public Commande (int numco, String date, boolean enligne, ModeReception Mode){
        this.numcom = numco;
        this.date = date;
        this.enligne = enligne;
        this.M2R = Mode;
    }

    public String editerFacture(){
        String facture = "-------------------------------------------------------------------"
    }
}