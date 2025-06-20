package src;
import java.sql.*;

public class ConnexionMySQL {
    private Connection mysql=null;
    private boolean connecte=false;

    public ConnexionMySQL() throws ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
    }

    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
        try{
            this.mysql=DriverManager.getConnection(
                "jdbc:mariadb://"+nomServeur+":3306/"+nomBase,
                nomLogin,motDePasse);
    
            this.connecte=this.mysql!=null;
           
        }
        catch (SQLException ex){
            System.err.println("Erreur SQL lors de la connexion:");
            System.err.println("Message: " + ex.getMessage());
            System.err.println("Code d'erreur SQL: " + ex.getErrorCode());
            System.err.println("État SQL: " + ex.getSQLState());
            }
    }

    public void close() throws SQLException {
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