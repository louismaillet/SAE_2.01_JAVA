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
}