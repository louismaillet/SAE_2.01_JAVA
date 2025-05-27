import java.sql.*;

public class Executable {
    public static void main(String[] args) {
        // Création de livres
        Livre livre1 = new Livre(1111, "Le Petit Prince", 96, "1943-04-06", 10.5, 5);
        Livre livre2 = new Livre(2222, "1984", 328, "1949-06-08", 15.0, 3);

        // Création d'un magasin
        Magasin magasin = new Magasin(1, "Librairie Centrale", "Orléans");
        magasin.addLivre(livre1, 5);
        magasin.addLivre(livre2, 3);

        // Affichage des livres du magasin
        System.out.println("Livres en stock dans le magasin :");
        for (Livre l : magasin.getListeLivres().keySet()) {
            System.out.println(l + " | Quantité : " + magasin.getListeLivres().get(l));
        }

        // Création d'un vendeur
        RoleVendeur role = RoleVendeur.VENDEUR;
        Vendeur vendeur = new Vendeur(1, "Dupont", "Jean", magasin, role);

        // Le vendeur ajoute un livre au stock
        vendeur.ajouterLivreStock(3333, "L'Étranger", "Albert Camus", "Gallimard", 1942, 12, 4, 123, "1942-05-19");

        // Affichage après ajout
        System.out.println("\nAprès ajout par le vendeur :");
        for (Livre l : magasin.getListeLivres().keySet()) {
            System.out.println(l + " | Quantité : " + magasin.getListeLivres().get(l));
        }

        // Création d'un client
        Client client = new Client(1, "Martin", "Claire", "12 rue des Fleurs", "45000", "Orléans");
        client.addLivre(livre1);

        // Affichage des livres achetés par le client
        System.out.println("\nLivres déjà achetés par le client :");
        for (Livre l : client.getLivresDejaAcheter()) {
            System.out.println(l);
        }

        // Création d'un administrateur et ajout de librairies
        Administrateur admin = new Administrateur(1, "Admin", "Super");
        Magasin librairie = new Magasin(2, "Librairie du Centre", "Paris");
        admin.ajouterLibrairie(2, "Librairie Sud", "Tours");
        admin.ajouterLibrairie(librairie);

        System.out.println("\nListe des Librairies :");
        for (Magasin m : admin.getListeMagasin()) {
            System.out.println(m);
        }

        // Création d'une commande avec les nouveaux paramètres
        Commande commande = new Commande(1, "2024-06-10", true, ModeReception.LIVRAISON);
        commande.ajouterLivreACommande(livre1);
        commande.ajouterLivreACommande(livre2);

        System.out.println("\nFacture de la commande :");
        System.out.println(commande.editerFacture());


        try {
            ConnexionMySQL connexion = new ConnexionMySQL();
            connexion.connecter("servinfo-maria", "Librairie", "maillet", "maillet"); 
            System.out.println("Connexion : " + (connexion.isConnecte() ? "Réussie" : "Échouée"));
            // N'oubliez pas de fermer la connexion à la fin si besoin :
            Statement st= connexion.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM CLIENT");
            while(rs.next()){
                int idcli = rs.getInt("idcli");
                String nomcli = rs.getString("nomcli");
                String prenomcli = rs.getString("prenomcli");
                String adresseCli = rs.getString("adressecli");
                int codepostal = rs.getInt("codepostal");
                String villecli = rs.getString("villecli");

                System.out.printf("%-5d | %-15s | %-15s | %-30s  | %-8d | %-15s%n", 
                    idcli, nomcli, prenomcli, adresseCli, codepostal, villecli);
            }
                    connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }   
}