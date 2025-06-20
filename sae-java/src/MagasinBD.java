package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MagasinBD {   
    public static List<Magasin> chargerMagasins(Connection connexion) {
        List<Magasin> magasins = new ArrayList<>();
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MAGASIN ORDER BY idmag");
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

    public static boolean setQuantiteLivre(Connection connexion, int idmag, long isbnLivre, int nouvelleQuantite) {
        String sql = "UPDATE POSSEDER SET qte = ? WHERE idmag = ? AND isbn = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, nouvelleQuantite);
            pstmt.setInt(2, idmag);
            pstmt.setLong(3, isbnLivre);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la quantité de livre : " + e.getMessage());
        }
        return false;
    }
    public static boolean supprimerLivreMagasin(Connection connexion, long isbn, int idmag) {
        String sql = "DELETE FROM POSSEDER WHERE isbn = ? AND idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            pstmt.setInt(2, idmag);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
        return false;
    }

    public static Magasin getMagasinParNom(Connection connexion, String nommag) {
        Magasin magasin = null;
        String sql = "SELECT * FROM MAGASIN WHERE nommag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, nommag);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int idmag = rs.getInt("idmag");
                String villemag = rs.getString("villemag");
                magasin = new Magasin(idmag, nommag, villemag);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du magasin par nom : " + e.getMessage());
        }
        return magasin;
    }
    
    public static int getDernierIdMagasin(Connection connexion) {
        String sql = "SELECT MAX(idmag) AS dernier_id FROM MAGASIN";
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int dernierId = rs.getInt("dernier_id");
                System.out.println("Dernier ID de magasin : " + dernierId);
                return dernierId;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ID de magasin : " + e.getMessage());
        
        }
        return -1;
    }

    public static List<Livre> getLivresAchetesParClientDansMagasin(Connection connexion, int idClient, int idMagasin) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE WHERE COMMANDE.idcli = ? AND COMMANDE.idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idClient);
            pstmt.setString(2, String.valueOf(idMagasin)); // si idMagasin est un int, sinon passe directement la chaîne
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Livre livre = new Livre(
                    rs.getLong("isbn"),
                    rs.getString("titre"),
                    rs.getInt("nbpages"),
                    rs.getInt("datepubli"),
                    rs.getDouble("prix"),
                    rs.getInt("qte")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public static Map<Integer, Integer> getQuantiteLivreDansTousMagasins(Connection connexion, long isbn) {
        Map<Integer, Integer> stocksParMagasin = new LinkedHashMap<>(); 
        String sql = "SELECT idmag, qte FROM POSSEDER WHERE isbn = ? AND qte > 0 ORDER BY idmag ASC";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stocksParMagasin.put(rs.getInt("idmag"), rs.getInt("qte"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des quantités par magasin pour ISBN " + isbn + ": " + e.getMessage());
            e.printStackTrace();
        }
        return stocksParMagasin;
    }

}