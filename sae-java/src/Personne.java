public abstract class Personne {
    private int id;
    private String nom;
    private String prenom;
    public Personne(int id, String nom, String prenom){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public int getId() {
        return this.id;
    }
    public String getNom() {
        return this.nom;
    }
    public String getPrenom() {
        return this.prenom;
    }
    
}
