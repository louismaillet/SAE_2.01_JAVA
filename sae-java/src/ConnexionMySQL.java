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
                "jdbc:mysql://"+nomServeur+":3306/"+nomBase,
                nomLogin,motDePasse);
            //ICI CODE DU PROGRAMME
            this.connecte=this.mysql!=null;
            //if (isConnecte(this)
            //System.out.println("JE SUIS CONNECTER OUAI");
        }
        catch (SQLException ex){
            System.out.println("Msg:"+ex.getMessage()+
            ex.getErrorCode());
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
    return this.mysql; // ou le nom de ton attribut Connection
}
}