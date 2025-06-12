import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandeBD {

    public static int getDernierIdCommande(Connection conn) throws SQLException {
        String sql = "SELECT MAX(numcom) FROM COMMANDE";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        }
    }
    public static void nouvelleCommande(Connection conn, Commande commande, int idcli, String idmag, boolean enligne, boolean livraison) throws SQLException {
        String sql = "INSERT INTO COMMANDE (numcom, datecom, enligne, livraison, idcli, idmag) VALUES (?, ?, ?, ?, ?, ?)";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commande.getIdCommande());
            pstmt.setDate(2, java.sql.Date.valueOf(commande.getDate()));
            pstmt.setString(3, enligne ? "O" : "N");
            pstmt.setString(4, livraison ? "O" : "N");
            pstmt.setInt(5, idcli);
            pstmt.setString(6, idmag);
            pstmt.executeUpdate();
        }
    }

    static void passerCommandeClient(Connection connexion, int idClient, int idmag, long bnCmd, int qteCmd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}