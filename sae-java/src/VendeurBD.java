package src;

import java.sql.Connection;



public class VendeurBD {

    public static Vendeur getVendeurParId(Connection connexion, int idVendeur) {
        String sql = "SELECT * FROM VENDEUR WHERE idvendeur = ?";
        try (var pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idVendeur);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idVend = rs.getInt("idvendeur");
                    String nomVend = rs.getString("nomvendeur");
                    String prenomVend = rs.getString("prenomvendeur");
                    int idMag = rs.getInt("idmag");
                    Magasin magasin = MagasinBD.getMagasinParId(connexion, idMag);
                    return new Vendeur(idVend, nomVend, prenomVend, magasin, RoleVendeur.VENDEUR);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Magasin getMagasinParId(Connection connexion, int idMagasin) {
        String sql = "SELECT * FROM MAGASIN WHERE idmag = ?";
        try (var pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idMagasin);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idMag = rs.getInt("idmag");
                    return MagasinBD.getMagasinParId(connexion, idMag);
                    
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
    public static int getDernierIdVendeur(Connection connexion) {
        String sql = "SELECT MAX(idvendeur) AS max_id FROM VENDEUR";
        try (var pstmt = connexion.prepareStatement(sql);
             var rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id");
            } else {
                return 0; // Aucun vendeur trouvé
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // En cas d'erreur, retourne 0
        }
    }
    
}
