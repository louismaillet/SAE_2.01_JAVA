
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        SuperAdmin superAdmin = new SuperAdmin(Database.magasins, Database.admins, Database.clients, Database.vendeurs);

        boolean running = true;

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
                    menuVendeur(scanner);
                    break;
                case "3":
                    menuClient(scanner, superAdmin);
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

    private static void menuVendeur(Scanner scanner) {
        boolean vendeurRunning = true;
        while (vendeurRunning) {
            System.out.println("--- Menu Vendeur ---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Modifier un livre");
            System.out.println("3. Supprimer un livre");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    System.out.println("Ajout de livre (fonctionnalité à implémenter)");
                    break;
                case "2":
                    System.out.println("Modification de livre (fonctionnalité à implémenter)");
                    break;
                case "3":
                    System.out.println("Suppression de livre (fonctionnalité à implémenter)");
                    break;
                case "0":
                    vendeurRunning = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuClient(Scanner scanner, SuperAdmin superAdmin) {
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
                    System.out.println(superAdmin.getListeLivres());
                    break;
                case "2":
                Scanner achatScanner = new Scanner(System.in);
                    System.out.println("Achat de livre");
                    System.out.print("Entrez l'ID du livre à acheter : ");
                    int idLivre = achatScanner.nextInt();
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
