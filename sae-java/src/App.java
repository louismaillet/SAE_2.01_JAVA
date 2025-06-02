import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            ConnexionMySQL connexion = new ConnexionMySQL();
            connexion.connecter("servinfo-maria", "DBmaillet", "maillet", "maillet");
            Scanner scanner = new Scanner(System.in);

            SuperAdmin superAdmin = new SuperAdmin(Database.magasins, Database.admins, Database.clients, Database.vendeurs);

            boolean running = true;
            if (connexion.getConnexion() == null) {
            System.out.println("Erreur : connexion à la base de données échouée !");
            return;
            }

            while (running) {
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Administrateur");
                System.out.println("2. Vendeur");
                System.out.println("3. Client");
                System.out.println("0. Quitter");
                System.out.print("Choisissez votre rôle: ");
                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        menuAdministrateur(scanner);
                        break;
                    case "2":
                        menuVendeur(scanner, connexion);
                        break;
                    case "3":
                        menuClient(scanner, connexion);
                        break;
                    case "0":
                        running = false;
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
            scanner.close();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) { // AJOUTE CE BLOC
        e.printStackTrace();
    }
}

    private static void menuAdministrateur(Scanner scanner) {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("1. Gérer les utilisateurs");
            System.out.println("2. Gérer les livres");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    System.out.println("1. Ajouter un compte Vendeur");
                    System.out.println("2. Supprimer un compte Vendeur");
                    System.out.println("0. Retour");
                    System.out.print("Votre choix: ");
                    String choixGererUtilisateur = scanner.nextLine();
                    switch (choixGererUtilisateur) {
                        case "1":
                            System.out.println("Méthode créer compte vendeur à ajouter");
                            
                            break;
                        case "2":
                            System.out.println("Gestion des livres (fonctionnalité à implémenter)");
                            break;
                        case "0":
                            adminRunning = false;
                            break;
                        default:
                            System.out.println("Choix invalide.");
                    }
                    break;
                case "2":
                    System.out.println("Gestion des livres (fonctionnalité à implémenter)");
                    break;
                case "0":
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuVendeur(Scanner scanner, ConnexionMySQL connexion) {
        boolean vendeurRunning = true;
        while (vendeurRunning) {
            System.out.println("--- Menu Vendeur ---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un livre");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    System.out.println("Ajouter un livre");
                    System.out.println("Entrez le nom du livre :");
                    String nomLivre = scanner.nextLine();
                    System.out.println("Entrez le nombre de pages :");
                    int nbPages = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("Entrez la date de publication :");
                    String datePubli = scanner.nextLine();
                    System.out.println("Entrez le prix :");
                    
                    String prixStr = scanner.nextLine();
                    double prix;
                    try {
                        prix = Double.parseDouble(prixStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Prix invalide, veuillez entrer un nombre valide.");
                        break;
                    }
                    long isbn = LivreBD.getDernierISBN(connexion.getConnexion()) + 1;
                    Livre livre = new Livre(isbn, nomLivre, nbPages, datePubli, prix, 0);
                    LivreBD.ajouterLivre(connexion.getConnexion(), livre);
                    break;
                case "2":
                    System.out.println("Suppression de livre");
                    System.out.println("Entrez l'ISBN du livre à supprimer :");
                    long isbnASupprimer = scanner.nextLong();
                    LivreBD.supprimerLivre(connexion.getConnexion(), isbnASupprimer);
                    System.out.println("Livre supprimé avec succès.");
                    break;
                case "0":
                    vendeurRunning = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuClient(Scanner scanner, ConnexionMySQL connexion) {
        boolean clientRunning = true;
        while (clientRunning) {
            System.out.println("--- Menu Client ---");
            System.out.println("1. Consulter les livres");
            System.out.println("2. Acheter un livre");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    System.out.println("Consultation des livres");
                    System.out.println("Liste des livres disponibles :");
                    System.out.println(LivreBD.chargerLivres(connexion.getConnexion()));
                
                    break;
                case "2":
                Scanner achatScanner = new Scanner(System.in);
                    System.out.println("Achat de livre");
                    System.out.println("Entrez l'ISBN du livre à acheter :");
                    long isbn = achatScanner.nextLong();
                    System.out.println("Livre ajouté au panier");
                    break;
                case "0":
                    clientRunning = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
      
    }

    }
}
