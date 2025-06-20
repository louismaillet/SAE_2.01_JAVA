package src;

public class Classification {
    private int id;
    private String nomclass;
    
    public Classification(int id, String nomclass) {
        this.id = id;
        this.nomclass = nomclass;
    }
    public int getId() {
        return this.id;
    }
    public String getNomclass() {
        return this.nomclass;
    }
    @Override
    public String toString() {
        return "L'id de la classification est " + this.id + " et le nom de la classification est " + this.nomclass;
    }

}
