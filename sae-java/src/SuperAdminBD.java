import java.sql.*;
import java.util.*;

public class SuperAdminBD {

    public static List<Livre> chargerLivres(Connection connexion) {
        List<Livre> livres = new ArrayList<>();
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM LIVRE");
            while(rs.next()){
                long isbn = rs.getLong("isbn");
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, 0);
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
        return livres;
    }
}
