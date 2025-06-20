package src;
import java.sql.*;

public class ConnexionMySQL {
    private Connection mysql=null;
    private boolean connecte=false;

    public ConnexionMySQL() throws ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
    }

    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
        // si tout s'est bien passÃ© la connexion n'est plus nulle
        try{
            this.mysql=DriverManager.getConnection(
                "jdbc:mariadb://"+nomServeur+":3306/"+nomBase, // Changement ici: jdbc:mysql:// -> jdbc:mariadb://
                nomLogin,motDePasse);
            //ICI CODE DU PROGRAMME
            this.connecte=this.mysql!=null;
            //if (isConnecte(this)
            //System.out.println("JE SUIS CONNECTER OUAI");
        }
        catch (SQLException ex){
            System.err.println("Erreur SQL lors de la connexion:"); // Modification pour une meilleure lisibilité
            System.err.println("Message: " + ex.getMessage());
            System.err.println("Code d'erreur SQL: " + ex.getErrorCode());
            System.err.println("État SQL: " + ex.getSQLState());
            // ex.printStackTrace(); // Décommentez pour une trace complète si nécessaire
            }
    }

    public void close() throws SQLException {
        // fermer la connexion
        this.connecte=false;
    }

    public boolean isConnecte() { return this.connecte;}
    
    public Statement createStatement() throws SQLException {
        return this.mysql.createStatement();
    }

    public PreparedStatement prepareStatement(String requete) throws SQLException{
        return this.mysql.prepareStatement(requete);
    }
    public Connection getConnexion() {
    return this.mysql;
}
    
}