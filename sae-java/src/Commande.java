public class Commande{
    private int numcom;
    private String date;
    private boolean enligne;
    private ModeReception modeDeReception;

    public Commande (int numco, String date, boolean enligne, ModeReception modeDeReception){
        this.numcom = numco;
        this.date = date;
        this.enligne = enligne;
        this.modeDeReception = modeDeReception;
    }

    public String editerFacture(){
        String facture = "-------------------------------------------------------------------";
        return facture;
    }
}