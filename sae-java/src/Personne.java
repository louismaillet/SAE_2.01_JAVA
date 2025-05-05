public abstract class Personne {
    protected int id;
    protected String nom;
    protected String prenom;
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
