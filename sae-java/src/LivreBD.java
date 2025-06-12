import java.sql.*;
import java.util.*;


public class LivreBD {


    public static List<Livre> getLivresAchetesParClient(Connection connexion, int idClient) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM LIVRE " +
                     "NATURAL JOIN DETAILCOMMANDE " +
                     "NATURAL JOIN COMMANDE " +
                     "WHERE idcli = ? " +
                     "ORDER BY isbn";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idClient);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long isbn = rs.getLong("isbn");
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                if (datePubli == null) datePubli = "";
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("qte");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, quantite);
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres achetés par le client : " + e.getMessage());
        }
        return livres;
    }
    
    public static List<Livre> chargerLivres(Connection connexion) {
        List<Livre> livres = new ArrayList<>();
        try {
            String sql = "SELECT LIVRE.isbn, titre, nbPages, datePubli, prix, SUM(qte) AS quantite_totale FROM LIVRE NATURAL JOIN POSSEDER GROUP BY LIVRE.isbn, titre, nbPages, datePubli, prix ORDER BY LIVRE.isbn";
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                long isbn = rs.getLong("isbn");
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("quantite_totale");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, quantite);
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
        return livres;
    }

    public static List<Livre> chargerLivresParMagasin(Connection connexion, int idmag) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT LIVRE.isbn, titre, nbPages, datePubli, prix, POSSEDER.qte FROM LIVRE NATURAL JOIN POSSEDER WHERE idmag = ? ORDER BY LIVRE.isbn";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idmag);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long isbn = rs.getLong("isbn");
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("qte");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, quantite);
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres par magasin : " + e.getMessage());
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
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Aucun livre supprimé (ISBN introuvable).");
            }
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
        String sql = "SELECT LIVRE.isbn, titre, nbPages, datePubli, prix, SUM(qte) AS qte " +
                 "FROM LIVRE NATURAL JOIN POSSEDER " +
                 "WHERE LIVRE.isbn = ? " +
                 "GROUP BY LIVRE.isbn, titre, nbPages, datePubli, prix";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("qte");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, quantite);
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

    public static Livre getLivreParIsbnEtMagasin(Connection connexion, long isbn, int idmag) {
        String sql = "SELECT LIVRE.isbn, titre, nbPages, datePubli, prix, SUM(qte) AS qte " +
                 "FROM LIVRE NATURAL JOIN POSSEDER " +
                 "WHERE LIVRE.isbn = ? AND idmag = ? " +
                 "GROUP BY LIVRE.isbn, titre, nbPages, datePubli, prix";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            pstmt.setInt(2, idmag);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datePubli");
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("qte");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix, quantite);
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

    public static void ajouterLivre(Connection connexion, Livre livre, int idmag) {
        String sqlLivre = "INSERT INTO LIVRE (isbn, titre, nbPages, datePubli, prix) VALUES (?, ?, ?, ?, ?)";
        String sqlPosseder = "INSERT INTO POSSEDER (isbn, idmag, qte) VALUES (?, ?, ?)";
        try {
            connexion.setAutoCommit(false);

            try (PreparedStatement pstmt = connexion.prepareStatement(sqlLivre)) {
                pstmt.setLong(1, livre.getIsbn());
                pstmt.setString(2, livre.getTitre());
                pstmt.setInt(3, livre.getNbPages());
                pstmt.setString(4, livre.getDatePubli());
                pstmt.setDouble(5, livre.getPrix());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
                
            }

            try (PreparedStatement pstmt = connexion.prepareStatement(sqlPosseder)) {
                pstmt.setLong(1, livre.getIsbn());
                pstmt.setInt(2, idmag);
                pstmt.setInt(3, livre.getQuantite());
                pstmt.executeUpdate();
            }

            connexion.commit();
        } catch (SQLException e) {
            try {
                connexion.rollback();
            } catch (SQLException ex) {
                System.out.println("Erreur lors du rollback : " + ex.getMessage());
            }
            System.out.println("Erreur lors de l'ajout du livre avec magasin : " + e.getMessage());
        } finally {
            try {
                connexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Erreur lors de la remise de l'auto-commit : " + e.getMessage());
            }
        }
    }

    public static void modifierQteLivre(Connection connexion, long isbn, int idmag, int nouvelleQuantite) {
        int quantiteActuelle = 0;
        String sqlSelect = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sqlSelect)) {
            pstmt.setLong(1, isbn);
            pstmt.setInt(2, idmag);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                quantiteActuelle = rs.getInt("qte");
                System.out.println("Quantité actuelle : " + quantiteActuelle);
            } else {
                System.out.println("Aucune entrée trouvée pour cet ISBN et ce magasin.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la quantité actuelle : " + e.getMessage());
        }

        String sql = "UPDATE POSSEDER SET qte = ? " +
                     "WHERE isbn = ? AND idmag = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, quantiteActuelle - nouvelleQuantite);
            pstmt.setLong(2, isbn);
            pstmt.setInt(3, idmag);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucune mise à jour effectuée (ISBN ou ID magasin introuvable).");
            } else {
                System.out.println("Quantité mise à jour avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la quantité du livre : " + e.getMessage());
        }   
    }



}
