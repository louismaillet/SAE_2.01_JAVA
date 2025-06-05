import java.sql.*;
import java.util.*;


public class LivreBD {
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
    
    public static void ajouterLivre(Connection connexion, Livre livre) {
        String sql = "INSERT INTO LIVRE (isbn, titre, nbPages, datePubli, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, livre.getIsbn());
            pstmt.setString(2, livre.getTitre());
            pstmt.setInt(3, livre.getNbPages());
            pstmt.setString(4, livre.getDatePubli());
            pstmt.setDouble(5, livre.getPrix());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }
    public static void supprimerLivre(Connection connexion, long isbn) {
        String sql = "DELETE FROM LIVRE WHERE isbn = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    public static long getDernierISBN(Connection connexion) {
        String sql = "SELECT MAX(isbn) AS dernier_isbn FROM LIVRE";
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                long dernierIsbn = rs.getLong("dernier_isbn");
                System.out.println("Dernier ISBN : " + dernierIsbn);
                return dernierIsbn;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ISBN : " + e.getMessage());
        }
        return 0;
    }

    public static Livre getLivreParISBN(Connection connexion, long isbn) {
        String sql = "SELECT * FROM LIVRE WHERE isbn = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, 0);
                System.out.println(livre);
                return livre;
            } else {
                System.out.println("Aucun livre trouvé avec l'ISBN : " + isbn);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du livre : " + e.getMessage());
        }
        return null;
    }


}
