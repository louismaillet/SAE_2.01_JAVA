import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MagasinBD {   
    public static List<Magasin> chargerMagasins(Connection connexion) {
        List<Magasin> magasins = new ArrayList<>();
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MAGASIN");
            while(rs.next()){
                int idmag = rs.getInt("idmag");
                String nommag = rs.getString("nommag");
                String villemag = rs.getString("villemag");
                Magasin magasin = new Magasin(idmag, nommag, villemag);
                magasins.add(magasin);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des magasins : " + e.getMessage());
        }
        return magasins;
    } 
    public static Magasin getMagasinParId(Connection connexion, int idmag) {
        Magasin magasin = null;
        String sql = "SELECT * FROM MAGASIN WHERE idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idmag);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nommag = rs.getString("nommag");
                String villemag = rs.getString("villemag");
                magasin = new Magasin(idmag, nommag, villemag);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du magasin : " + e.getMessage());
        }
        return magasin;
    }

    public static int getQuantiteLivre(Connection connexion, int idmag, long bnVerif) {
        int quantite = 0;
        String sql = "SELECT qte FROM POSSEDER NATURAL JOIN MAGASIN WHERE idmag = ? AND isbn = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idmag);
            pstmt.setLong(2, bnVerif);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                quantite = rs.getInt("qte");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la quantité de livre : " + e.getMessage());
        }
        return quantite;
    }

    static void setQuantiteLivre(Connection connexion, int idmag, long isbnLivre, int nouvelleQuantite) {
        String sql = "UPDATE POSSEDER SET qte = ? WHERE idmag = ? AND isbn = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, nouvelleQuantite);
            pstmt.setInt(2, idmag);
            pstmt.setLong(3, isbnLivre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la quantité de livre : " + e.getMessage());
        }
    }
    public static void supprimerLivreMagasin(Connection connexion, long isbn, int idmag) {
        String sql = "DELETE FROM POSSEDER WHERE isbn = ? AND idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            pstmt.setInt(2, idmag);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }


}