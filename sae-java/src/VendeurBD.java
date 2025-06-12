
import java.sql.Connection;




class VendeurBD {

    public static Vendeur getVendeurParId(Connection connexion, int idVendeur) {
        String sql = "SELECT * FROM VENDEUR WHERE idvend = ?";
        try (var pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idVendeur);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming you have methods to fetch Magasin and RoleVendeur by their IDs or from the ResultSet
                    int idVend = rs.getInt("idvend");
                    String nomVend = rs.getString("nomvend");
                    String prenomVend = rs.getString("prenomvend");

                    return new Vendeur(idVend, nomVend, prenomVend);
                     
                } else {
                    return null; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle exception appropriately
        }
    }

}
