import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdministrateurBD {

    
    public static int getDernierIdVendeur(Connection conn) throws SQLException {
        String sql = "SELECT MAX(idvendeur) FROM VENDEUR";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    
    public static void creerCompteVendeur(Connection conn, Vendeur vendeur) throws SQLException {
        String sql = "INSERT INTO VENDEUR (idvendeur, nomvendeur, prenomvendeur, idmag) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vendeur.getId());
            pstmt.setString(2, vendeur.getNom());
            pstmt.setString(3, vendeur.getPrenom());
            pstmt.setInt(4, vendeur.getMagasin().getIdmag());
            pstmt.executeUpdate();
        }
    }

    
    public static int getDernierIdMagasin(Connection conn) throws SQLException {
        String sql = "SELECT MAX(idmag) FROM MAGASIN";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    
    public static void ajouterLibrairie(Connection conn, Magasin magasin) throws SQLException {
        String sql = "INSERT INTO MAGASIN (idmag, nommag, villemag) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, magasin.getIdmag());
            pstmt.setString(2, magasin.getNommag());
            pstmt.setString(3, magasin.getVillemag());
            pstmt.executeUpdate();
        }
    }

    
    public static List<Livre> getStocksGlobaux(Connection conn) throws SQLException {
        List<Livre> stocks = new ArrayList<>();
        String sql = "SELECT isbn, titre, nbpages, datepubli, prix, SUM(qte) AS qte " +
                     "FROM LIVRE NATURAL JOIN POSSEDER " +
                     "GROUP BY isbn, titre, nbpages, datepubli, prix " +
                     "ORDER BY titre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stocks.add(new Livre(
                    rs.getLong("isbn"),
                    rs.getString("titre"),
                    rs.getInt("nbpages"),
                    rs.getString("datepubli"),
                    rs.getDouble("prix"),
                    rs.getInt("qte")
                ));
            }
        }
        return stocks;
    }

    
    public static double getChiffreAffairesTotal(Connection conn) throws SQLException {
        String sql = "SELECT SUM(qte * prix) AS chiffre_affaires " +
                     "FROM DETAILCOMMANDE NATURAL JOIN LIVRE";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("chiffre_affaires");
            }
        }
        return 0.0;
    }

    
    public static List<String> getLivresPlusVendus(Connection conn) throws SQLException {
        List<String> bestSellers = new ArrayList<>();
        String sql = "SELECT titre, SUM(qte) AS total_vendu " +
                 "FROM DETAILCOMMANDE NATURAL JOIN LIVRE " +
                 "GROUP BY titre " +
                 "ORDER BY total_vendu DESC " +
                 "LIMIT 10";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String bestSeller = String.format("'%s' - %d exemplaires vendus",
                    rs.getString("titre"),
                    rs.getInt("total_vendu"));
                bestSellers.add(bestSeller);
            }
        }
        return bestSellers;
    }


    public static List<Vendeur> getVendeurs(Connection conn) throws SQLException {
        List<Vendeur> vendeurs = new ArrayList<>();
        String sql = "SELECT * FROM VENDEUR NATURAL JOIN MAGASIN";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int idvend = rs.getInt("idvendeur");
                String nomvendeur = rs.getString("nomvendeur");
                String prenomvendeur = rs.getString("prenomvendeur");
                int idmag = rs.getInt("idmag");

                Vendeur vendeur = new Vendeur(idvend, nomvendeur, prenomvendeur, new Magasin(idmag, rs.getString("nommag"), rs.getString("villemag")), RoleVendeur.VENDEUR);
                vendeurs.add(vendeur);
            }
        }
        return vendeurs;
    }
}
