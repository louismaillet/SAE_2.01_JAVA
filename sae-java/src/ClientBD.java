import java.sql.Connection;
import java.sql.SQLException;

public class ClientBD {

    public static void ajouterClient(Connection conn, Client client) throws SQLException {
        String sql = "INSERT INTO CLIENT (idcli, nomcli, prenomcli, adressecli, codepostal, villecli) VALUES (?, ?, ?, ?, ?, ?)";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, client.getId());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getPrenom());
            pstmt.setString(4, client.getAdressecli());
            pstmt.setString(5, client.getCodepostal());
            pstmt.setString(6, client.getVillecli());
            pstmt.executeUpdate();
        }
    }

    public static int getDernierIdClient(ConnexionMySQL2 connexion) throws SQLException {
        String sql = "SELECT MAX(idcli) FROM CLIENT";
        try (var stmt = connexion.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public static Client getClientParId(Connection connexion, int idClient) {
        String sql = "SELECT * FROM CLIENT WHERE idcli = ?";
        try (var pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idClient);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("idcli"),
                        rs.getString("nomcli"),
                        rs.getString("prenomcli"),
                        rs.getString("adressecli"),
                        rs.getString("codepostal"),
                        rs.getString("villecli")
                    );
                } else {
                    return null; // Client not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle exception appropriately
        }
        
        
    }
    
}