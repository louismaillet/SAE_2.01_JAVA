import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static int getDernierIdClient(ConnexionMySQL connexion) throws SQLException {
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
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Livre> onVousRecommande(Connection connexion, Client client) throws SQLException {
        Set<Livre> recommandationsUniques = new HashSet<>();
        Set<Livre> livresAchetesParClientActuel = new HashSet<>(LivreBD.getLivresAchetesParClient(connexion, client.getId()));

        Set<Integer> autresClientsIds = new HashSet<>();
        String sqlSelectAutresClients = "SELECT DISTINCT idcli FROM COMMANDE WHERE idcli != ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sqlSelectAutresClients)) {
            pstmt.setInt(1, client.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    autresClientsIds.add(rs.getInt("idcli"));
                }
            }
        }

        for (Integer autreClientId : autresClientsIds) {
            Set<Livre> livresAchetesParAutreClient = new HashSet<>(LivreBD.getLivresAchetesParClient(connexion, autreClientId));

            Set<Livre> livresCommuns = new HashSet<>(livresAchetesParClientActuel);
            livresCommuns.retainAll(livresAchetesParAutreClient);

            if (!livresCommuns.isEmpty()) {
                Set<Livre> livresARecommander = new HashSet<>(livresAchetesParAutreClient);
                livresARecommander.removeAll(livresAchetesParClientActuel);
                recommandationsUniques.addAll(livresARecommander);
            }
            if (recommandationsUniques.size() >= 10) {
                break;
            }
        }

        List<Livre> recommandationsListe = new ArrayList<>(recommandationsUniques);
        if (recommandationsListe.size() > 5) {
            return recommandationsListe.subList(0, 5); // la methode sublist retourne les 5 premiers livres nouvelle methode apprise !
        } else {
            return recommandationsListe;
        }
    }
}