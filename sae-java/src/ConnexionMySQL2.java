import java.sql.*;

class ConnexionMySQL2 {
    private Connection mysql = null;
    private boolean connecte = false;

    // Paramètres par défaut pour ta machine
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_DB = "Librairie";
    private static final String DEFAULT_USER = "louis";
    private static final String DEFAULT_PASS = "louisM22!";

    // Constructeur pour connexion directe à ta base locale
    ConnexionMySQL2() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        mysql = DriverManager.getConnection(
            "jdbc:mariadb://" + DEFAULT_HOST + ":3306/" + DEFAULT_DB + "?allowPublicKeyRetrieval=true",
            DEFAULT_USER, DEFAULT_PASS
        );
        connecte = mysql != null;
    }

    // Pour connexion personnalisée
    ConnexionMySQL2(String host, String db, String user, String pass) throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.mariadb.jdbc.Driver");
        mysql = DriverManager.getConnection(
            "jdbc:mariadb://" + host + ":3306/" + db        
            );
        connecte = mysql != null;
    }

    void close() throws SQLException {
        if (mysql != null) {
            mysql.close();
            connecte = false;
        }
    }

    boolean isConnecte() {
        return connecte;
    }

    Statement createStatement() throws SQLException {
        return mysql.createStatement();
    }

    PreparedStatement prepareStatement(String requete) throws SQLException {
        return mysql.prepareStatement(requete);
    }

    Connection getConnexion() {
        return mysql;
    }
    
    void connecter(String host, String db, String user, String pass) throws SQLException, ClassNotFoundException {
        //    Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.mariadb.jdbc.Driver");
        mysql = DriverManager.getConnection(
            "jdbc:mariadb://" + host + ":3306/" + db
        );
        connecte = mysql != null;
    }
}