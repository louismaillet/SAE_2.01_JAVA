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
                    // Générer un nouvel ISBN automatiquement
                    long nouvelIsbn = LivreBD.getDernierISBN(connexion.getConnexion()) + 1;
                    Livre livre = new Livre(nouvelIsbn, nomLivre, nbPages, datePubli, prix, 0);
                    try {
                        LivreBD.ajouterLivre(connexion.getConnexion(), livre);
                        System.out.println("Livre ajouté avec succès (ISBN généré : " + nouvelIsbn + ").");
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
                    }
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
        Commande nouvelleCommande = new Commande();
        try {
            nouvelleCommande.setNumcom(connexion.getConnexion());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la commande : " + e.getMessage());
            return;
        }

        System.out.println("Choisissez le mode de réception :");
        System.out.println("1. Livraison");
        System.out.println("2. Retrait en magasin");
        String modeReceptionStr = scanner.nextLine();
        ModeReception modeReception = ModeReception.LIVRAISON;
        Magasin magasinChoisi = null;
        if (modeReceptionStr.equals("2")) {
            modeReception = ModeReception.ENMAGASIN;
            // Afficher la liste des magasins
            System.out.println("Veuillez choisir un magasin pour le retrait :");
            for (Magasin m : MagasinBD.chargerMagasins(connexion.getConnexion())) {
                System.out.println(m.getIdmag() + " - " + m.getNommag() + " (" + m.getVillemag() + ")");
            }
            System.out.print("Entrez l'ID du magasin : ");
            int idMagasin = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne
            for (Magasin m : Database.magasins) {
                if (m.getIdmag() == idMagasin) {
                    magasinChoisi = m;
                    break;
                }
            }
            if (magasinChoisi == null) {
                System.out.println("Magasin introuvable, commande annulée.");
                return;
            }
            System.out.println("Vous avez choisi : " + magasinChoisi.getNommag() + " (" + magasinChoisi.getVillemag() + ")");
        }
        nouvelleCommande.setModeDeReception(modeReception);

        System.out.println("Nouvelle commande créée (ID : " + nouvelleCommande.getIdCommande() + ")");

        boolean clientRunning = true;
        while (clientRunning) {
            System.out.println("--- Menu Client ---");
            System.out.println("1. Consulter les livres");
            System.out.println("2. Acheter un livre");
            System.out.println("3. Consulter mon panier");
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
                    System.out.println("Achat de livre");
                    System.out.println("Entrez l'ISBN du livre à acheter :");
                    long isbn = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        Livre livreAchete = LivreBD.getLivreParISBN(connexion.getConnexion(), isbn);
                        if (livreAchete != null) {
                            nouvelleCommande.ajouterLivreACommande(livreAchete);
                            System.out.println("Livre ajouté à la commande : " + livreAchete.getTitre());
                        } else {
                            System.out.println("Livre non trouvé avec l'ISBN : " + isbn);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'achat du livre : " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("Consultation du panier");
                    System.out.println(nouvelleCommande.editerFacture());
                    break;
                case "4":
                    System.out.println("Voici une recommandation de livre :");
                    //Livre livreRecommande = LivreBD.getLivreRecommande(connexion.getConnexion());
                case "0":
                    System.out.println("Etes-vous sûr de vouloir quitter le panier sera supprimé ?");
                    System.out.println("1. Oui");
                    System.out.println("2. Non");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equals("1")) {
                        System.out.println("Panier vidé.");
                        clientRunning = false;
                    } else {
                        System.out.println("Panier conservé.");
                    }
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
