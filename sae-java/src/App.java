import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        EffacerTerminale.clearConsole(); // Clear console at startup
        ConnexionMySQL connexion = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connexion = new ConnexionMySQL(); 
            connexion.connecter("servinfo-maria", "DBmaillet", "maillet", "maillet");
            // Check if the connection was successful
            if (connexion.getConnexion() == null) {
                System.out.println("❌ Erreur : La connexion à la base de données a échoué. Veuillez vérifier les paramètres de connexion.");
                return; // Exit if no database connection
            }
            
            boolean running = true;
            while (running) {
            
                System.out.println("╔═════════════════════════════════════╗");
                System.out.println("║        BIENVENUE DANS LA LIBRAIRIE  ║");
                System.out.println("╠═════════════════════════════════════╣");
                System.out.println("║ 1. Administrateur                   ║");
                System.out.println("║ 2. Vendeur                          ║");
                System.out.println("║ 3. Client                           ║");
                System.out.println("║ 0. Quitter                          ║");
                System.out.println("╚═════════════════════════════════════╝");
                System.out.print("Choisissez votre rôle : ");

                String choix = scanner.nextLine(); // Read user's choice

                switch (choix) {
                    case "1":
                        menuAdministrateur(scanner, connexion);
                        break;
                    case "2":
                        menuVendeur(scanner, connexion);
                        break;
                    case "3":
                        menuClient(scanner, connexion);
                        break;
                    case "0":
                        running = false; // Set flag to exit loop
                        System.out.println("👋 Au revoir !");
                        break;
                    default:
                        System.out.println("⚠️ Choix invalide. Veuillez entrer un numéro valide.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Erreur de pilote JDBC : " + e.getMessage());
            System.err.println("Assurez-vous que le pilote JDBC est correctement configuré et présent dans le classpath.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur de base de données : " + e.getMessage());
            System.err.println("Veuillez vérifier que la base de données est en ligne et accessible.");
        } finally {
            if (scanner != null) {
                scanner.close(); // Close the scanner to release resources
            }
            // Removed the `connexion.deconnecter()` call as it was not in the original
            // and caused a compilation error.
        }
    }

    /**
     * Displays the administrator menu and handles admin actions.
     * @param scanner The Scanner object for user input.
     * @param connexion The database connection object.
     */
    private static void menuAdministrateur(Scanner scanner, ConnexionMySQL connexion) {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          MENU ADMINISTRATEUR           ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Créer un compte vendeur             ║");
            System.out.println("║ 2. Ajouter une nouvelle librairie      ║");
            System.out.println("║ 3. Consulter les stocks globaux        ║");
            System.out.println("║ 4. Consulter les statistiques de vente ║");
            System.out.println("║ 5. Consulter les données               ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 0. Retour au menu principal            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Votre choix : ");
            String choix = scanner.nextLine();

            try {
                switch (choix) {
                    case "1":
                        EffacerTerminale.clearConsole();
                        System.out.println("--- Création d'un compte vendeur ---");
                        System.out.print("Nom du vendeur: ");
                        String nomVend = scanner.nextLine();
                        System.out.print("Prénom du vendeur: ");
                        String prenomVend = scanner.nextLine();

                        System.out.println("\nListe des librairies disponibles:");
                        List<Magasin> magasins = MagasinBD.chargerMagasins(connexion.getConnexion());
                        for (Magasin m : magasins) {
                            System.out.println(m.getIdmag() + " - " + m.getNommag() + " (" + m.getVillemag() + ")");
                        }
                        System.out.print("Entrez l'ID de la librairie d'affectation: ");
                        int idMag = Integer.parseInt(scanner.nextLine());
                        Magasin magasinAffecte = MagasinBD.getMagasinParId(connexion.getConnexion(), idMag);

                        if (magasinAffecte == null) {
                            System.out.println("❌ Erreur: Librairie non trouvée.");
                            break;
                        }

                        RoleVendeur role = RoleVendeur.VENDEUR;
                        int nouveauIdVendeur = AdministrateurBD.getDernierIdVendeur(connexion.getConnexion()) + 1;

                        Vendeur nouveauVendeur = new Vendeur(nouveauIdVendeur, nomVend, prenomVend, magasinAffecte, role);
                        AdministrateurBD.creerCompteVendeur(connexion.getConnexion(), nouveauVendeur);
                        System.out.println("✅ Compte vendeur créé avec succès pour " + prenomVend + " " + nomVend + " (ID: " + nouveauIdVendeur + ")");
                        break;

                    case "2":
                        EffacerTerminale.clearConsole();
                        System.out.println("--- Ajout d'une nouvelle librairie ---");
                        System.out.print("Nom de la librairie: ");
                        String nomMag = scanner.nextLine();
                        System.out.print("Ville de la librairie: ");
                        String villeMag = scanner.nextLine();

                        int nouveauIdMagasin = AdministrateurBD.getDernierIdMagasin(connexion.getConnexion()) + 1;
                        Magasin nouvelleLibrairie = new Magasin(nouveauIdMagasin, nomMag, villeMag);
                        AdministrateurBD.ajouterLibrairie(connexion.getConnexion(), nouvelleLibrairie);
                        System.out.println("✅ Librairie ajoutée avec succès (ID: " + nouveauIdMagasin + ")");
                        break;

                    case "3":
                        EffacerTerminale.clearConsole();
                        System.out.println("--- Stocks Globaux de tous les livres ---");
                        List<Livre> stocks = AdministrateurBD.getStocksGlobaux(connexion.getConnexion());
                        // Original display format preserved
                        System.out.printf("%-15s | %-40s | %s%n", "ISBN", "Titre", "Quantité en Stock"); //permet de avoir une meilleur affichage 
                        System.out.println("-".repeat(70));
                        for (Livre l : stocks) {
                            System.out.printf("%-15d | %-40s | %d%n", l.getIsbn(), l.getTitre(), l.getQuantite());
                        }
                        break;

                    case "4":
                        EffacerTerminale.clearConsole();
                        System.out.println("--- Statistiques de Vente ---");
                        

                        System.out.println("\nLivres les plus vendus:");
                        List<String> bestSellers = AdministrateurBD.getLivresPlusVendus(connexion.getConnexion());
                        if (bestSellers.isEmpty()) {
                            System.out.println("Aucune vente enregistrée.");
                        } else {
                            for(String s : bestSellers) {
                                System.out.println("- " + s);
                            }
                        }
                        double chiffreAffaires = AdministrateurBD.getChiffreAffairesTotal(connexion.getConnexion());
                        System.out.printf("Chiffre d'affaires total: %.2f €%n", chiffreAffaires);
                        break;
                    
                    case "5":
                        boolean consultationRunning = true;
                        while (consultationRunning) {
                        System.out.println("--- Consultation des données ---");
                        System.out.println("1. Consulter les vendeurs");
                        System.out.println("2. Consulter les librairies");
                        System.out.println("3. Consulter tous les livres");
                        System.out.println("0. Retour");
                        System.out.print("Votre choix : ");
                        String choixData = scanner.nextLine();
                        switch (choixData) {
                            case "1":
                                EffacerTerminale.clearConsole();
                                System.out.println("--- Liste des vendeurs ---");
                                List<Vendeur> vendeurs = AdministrateurBD.getVendeurs(connexion.getConnexion());
                                if (vendeurs.isEmpty()) {
                                    System.out.println("Aucun vendeur trouvé.");
                                } else {
                                    for (Vendeur v : vendeurs) {
                                        System.out.println(v.getId() + " - " + v.getNom() + " " + v.getPrenom() + " (" + v.getMagasin().getNommag() + ")");
                                    }
                                }
                                break;
                            case "2":
                                EffacerTerminale.clearConsole();
                                System.out.println("--- Liste des librairies ---");
                                List<Magasin> magasinsData = MagasinBD.chargerMagasins(connexion.getConnexion());
                                if (magasinsData.isEmpty()) {
                                    System.out.println("Aucune librairie trouvée.");
                                } else {
                                    for (Magasin m : magasinsData) {
                                        System.out.println(m.getIdmag() + " - " + m.getNommag() + " (" + m.getVillemag() + ")");
                                    }
                                }
                                break;
                            case "3":
                                
                                EffacerTerminale.clearConsole();
                                System.out.println("--- Liste de tous les livres ---");
                                List<Livre> livres = LivreBD.chargerLivres(connexion.getConnexion());
                                if (livres.isEmpty()) {
                                    System.out.println("Aucun livre trouvé.");
                                } else {
                                    for (Livre l : livres) {
                                        System.out.println(l);
                                    }
                                }
                                break;
                            case "0":
                                EffacerTerminale.clearConsole();
                                System.out.println("↩️ Retour au menu administrateur...");
                                consultationRunning = false;
                                break;
                            default:
                                System.out.println("⚠️ Choix invalide.");
                        }
                    }
                    break;



                    case "0":
                        adminRunning = false;
                        System.out.println("↩️ Retour au menu principal...");
                        break;

                    default:
                        System.out.println("⚠️ Choix invalide.");
                }
                if (adminRunning) {
                    System.out.println("\nAppuyez sur Entrée pour continuer...");
                    scanner.nextLine();
                    EffacerTerminale.clearConsole(); 
                }
            } catch (SQLException e) {
                System.out.println("❌ Erreur de base de données: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("❌ Erreur: Veuillez entrer un nombre valide.");
            }
        }
    }

    
    private static void menuVendeur(Scanner scanner, ConnexionMySQL connexion) {
        EffacerTerminale.clearConsole();
        boolean vendeurRunning = true;
        System.out.println("Connectez-vous en tant que vendeur.");
        int idVendeur = 0;
        Vendeur vendeur = null;

        while(vendeur == null) {
            System.out.print("Entrez votre ID vendeur : ");
            try {
                idVendeur = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                vendeur = VendeurBD.getVendeurParId(connexion.getConnexion(), idVendeur);
                if(vendeur == null) {
                    EffacerTerminale.clearConsole();
                    System.out.println("❌ Vendeur non trouvé avec l'ID : " + idVendeur + ". Veuillez entrer un ID vendeur valide.");
                } else {
                    EffacerTerminale.clearConsole();
                    System.out.println("🎉 Bienvenue " + vendeur.getNom() + " " + vendeur.getPrenom() + " !");
                }
            } catch (InputMismatchException e) {
                EffacerTerminale.clearConsole();
                System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ID vendeur.");
                scanner.nextLine(); // Consume invalid input
            }
        }


        while (vendeurRunning) {
            System.out.println("Bonjour "+ vendeur.getNom() + " " + vendeur.getPrenom());
            System.out.println("--- Menu Vendeur ---");
            System.out.println("1. Voir les livres de la librairie " + vendeur.getMagasin().getNommag());
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Supprimer un livre");
            System.out.println("4. Gérer le stock de livres");
            System.out.println("5. Acheter un livre pour un client");
            System.out.println("6. Vérifier la disponibilité d’un livre");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    EffacerTerminale.clearConsole();
                    System.out.println("Liste des livres dans la librairie " + vendeur.getMagasin().getNommag() + ":");
                    List<Livre> livres = LivreBD.chargerLivresParMagasin(connexion.getConnexion(), vendeur.getMagasin().getIdmag());
                    if (livres.isEmpty()) {
                        System.out.println("Aucun livre trouvé dans cette librairie.");
                    } else {
                        for (Livre l : livres) {
                            System.out.println(l);
                        }
                    }
                    break;
                case "2":
                    EffacerTerminale.clearConsole();
                    System.out.println("Ajouter un livre");
                    System.out.println("Entrez le nom du livre :");
                    String nomLivre = scanner.nextLine();

                    System.out.println("Entrez le nombre de pages :");
                    int nbPages = 0;
                    try {
                        nbPages = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Nombre de pages invalide, veuillez entrer un nombre entier.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("Entrez la date de publication :");
                    String datePubli = scanner.nextLine();
                    System.out.println("Entrez le prix :");

                    String prixStr = scanner.nextLine();
                    double prix;
                    try {
                        prix = Double.parseDouble(prixStr);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Prix invalide, veuillez entrer un nombre valide.");
                        break;
                    }
                    long nouvelIsbn = LivreBD.getDernierISBN(connexion.getConnexion()) + 1;
                    Livre livre = new Livre(nouvelIsbn, nomLivre, nbPages, datePubli, prix, 0);
                    try {
                        LivreBD.ajouterLivre(connexion.getConnexion(), livre, vendeur.getMagasin().getIdmag());
                        EffacerTerminale.clearConsole();
                        System.out.println("✅ Livre ajouté avec succès (ISBN généré : " + nouvelIsbn + ").");
                    } catch (Exception e) {
                        EffacerTerminale.clearConsole();
                        System.out.println("❌ Erreur lors de l'ajout du livre : " + e.getMessage());
                    }
                    break;
                case "3":
                    EffacerTerminale.clearConsole();
                    System.out.println("Suppression de livre");
                    System.out.println("Entrez l'ISBN du livre à supprimer :");
                    long isbnASupprimer = 0;
                    try {
                        isbnASupprimer = scanner.nextLong();
                        scanner.nextLine();
                        MagasinBD.supprimerLivreMagasin(connexion.getConnexion(), isbnASupprimer, vendeur.getMagasin().getIdmag());
                        EffacerTerminale.clearConsole();
                        System.out.println("✅ Livre supprimé avec succès.");
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ISBN.");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("❌ Erreur lors de la suppression du livre : " + e.getMessage());
                    }
                    break;
                case "4":
                    EffacerTerminale.clearConsole();
                    System.out.println("Gérer le stock de livres");
                    System.out.println("Entrez l'ISBN du livre à gérer :");
                    long isbnLivre = 0;
                    boolean isbnPasTrouve = true; // Flag to check if ISBN was found
                    Livre livreStock = null;

                    while (isbnPasTrouve) {
                        try {
                            isbnLivre = scanner.nextLong();
                            scanner.nextLine(); // Consommer le retour à la ligne
                            livreStock = LivreBD.getLivreParISBN(connexion.getConnexion(), isbnLivre);
                            isbnPasTrouve = false; // Reset flag if input is valid
                            
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ISBN.");
                            scanner.nextLine(); // Consommer l'entrée invalide
                            isbnPasTrouve = true; // Set flag to true to repeat the loop
                            break;
                        }
                    }
                   

                    if (livreStock == null) {
                        EffacerTerminale.clearConsole();
                        System.out.println("❌ Livre non trouvé avec l'ISBN : " + isbnLivre);
                        break;
                    }
                    System.out.println("Livre trouvé : " + livreStock.getTitre());
                    System.out.println("Entrez la nouvelle quantité en stock :");
                    int nouvelleQuantite = 0;
                    try {
                        nouvelleQuantite = scanner.nextInt();
                        scanner.nextLine(); // Consommer le retour à la ligne
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour la quantité.");
                        scanner.nextLine();
                        break;
                    }
                    livreStock.setQuantite(nouvelleQuantite);
                    MagasinBD.setQuantiteLivre(connexion.getConnexion(), vendeur.getMagasin().getIdmag(), isbnLivre, nouvelleQuantite);
                    // Re-commented this line to restore original behavior, as it was commented out.
                    // LivreBD.mettreAJourLivre(connexion.getConnexion(), livreStock);
                    EffacerTerminale.clearConsole();
                    System.out.println("✅ Stock mis à jour avec succès.");
                    break;
                case "5":
            // Passer une commande pour un client en magasin
            System.out.println("Entrez l'ID du client :");
            int idClient = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Entrez l'ISBN du livre à commander :");
            long isbnCmd = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Quantité :");
            int qteCmd = scanner.nextInt();
            scanner.nextLine();
            // À implémenter : CommandeBD.passerCommandeClient(conn, idClient, idMagasin, isbn, qte)
            CommandeBD.passerCommandeClient(connexion.getConnexion(), idClient, vendeur.getMagasin().getIdmag(), isbnCmd, qteCmd);
            System.out.println("✅ Commande passée pour le client.");
            break;

                case "6":
            // Vérifier la disponibilité d’un livre dans la librairie
            System.out.println("Entrez l'ISBN du livre à vérifier :");
            long isbnVerif = scanner.nextLong();
            scanner.nextLine();
            int dispo = MagasinBD.getQuantiteLivre(connexion.getConnexion(), vendeur.getMagasin().getIdmag(), isbnVerif);
            if (dispo > 0) {
                System.out.println("✅ Livre disponible (" + dispo + " en stock).");
            } else {
                System.out.println("❌ Livre non disponible.");
            }
            break;
                case "0":
                    vendeurRunning = false;
                    break;
                default:
                    System.out.println("⚠️ Choix invalide.");
            }
        }
    }

    
    private static void menuClient(Scanner scanner, ConnexionMySQL connexion) throws SQLException {
        EffacerTerminale.clearConsole();
        Client currentClient = null;

        while (currentClient == null) {
            System.out.println("--- Accès Client ---");
            System.out.println("1. Se connecter");
            System.out.println("2. Créer un compte");
            System.out.print("Votre choix : ");
            String choixCompte = scanner.nextLine();

            if (choixCompte.equals("2")) {
                System.out.print("Entrez votre nom : ");
                String nomClient = scanner.nextLine();
                System.out.print("Entrez votre prenom : ");
                String prenomClient = scanner.nextLine();
                System.out.print("Entrez votre adresse : ");
                String adresseClient = scanner.nextLine();
                System.out.print("Entrez votre code postal : ");
                String codePostalClient = scanner.nextLine();
                System.out.print("Entrez votre ville : ");
                String villeClient = scanner.nextLine();
                // Reverted to original `connexion` parameter for getDernierIdClient
                Client client = new Client(ClientBD.getDernierIdClient(connexion) + 1, nomClient, prenomClient, adresseClient, codePostalClient, villeClient);
                try {
                    ClientBD.ajouterClient(connexion.getConnexion(), client);
                    EffacerTerminale.clearConsole();
                    System.out.println("✅ Compte client créé avec succès.");
                    currentClient = client; // Set currentClient upon successful creation
                } catch (SQLException e) {
                    System.out.println("❌ Erreur lors de la création du compte : " + e.getMessage());
                    return;
                }
            } else if (choixCompte.equals("1")) {
                System.out.print("Entrez votre ID client : ");
                int idClient = 0;
                try {
                    idClient = scanner.nextInt();
                    scanner.nextLine(); // Consommer le retour à la ligne
                } catch (InputMismatchException e) {
                    EffacerTerminale.clearConsole();
                    System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ID client.");
                    scanner.nextLine();
                    continue; // Continue the loop to prompt again
                }

                currentClient = ClientBD.getClientParId(connexion.getConnexion(), idClient);
                if (currentClient == null) {
                    EffacerTerminale.clearConsole();
                    System.out.println("❌ Client non trouvé avec l'ID : " + idClient);
                } else {
                    EffacerTerminale.clearConsole();
                    System.out.println("🎉 Bienvenue " + currentClient.getNom() + " " + currentClient.getPrenom() + " !");
                }
            } else {
                System.out.println("⚠️ Choix invalide.");
                return; // Exit client menu if invalid initial choice
            }
        }


        Commande nouvelleCommande = new Commande();
        try {
            nouvelleCommande.setNumcom(connexion.getConnexion());
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la création de la commande : " + e.getMessage());
            return;
        }
        boolean modeReceptionRunning = true;
        ModeReception modeReception = null;
        Magasin magasinChoisi = null;
        while (modeReceptionRunning){
        System.out.println("Choisissez le mode de réception :");
        System.out.println("1. Livraison");
        System.out.println("2. Retrait en magasin");
        String modeReceptionStr = scanner.nextLine();
        switch (modeReceptionStr) {
            case "1":
                System.out.println("Mode de réception : Livraison");
                modeReception = ModeReception.LIVRAISON;
                modeReceptionRunning = false; 
                break;

            case "2":
                System.out.println("Mode de réception : Retrait en magasin");
                modeReception = ModeReception.ENMAGASIN;
                modeReceptionRunning = false;
                System.out.println("Veuillez choisir un magasin pour le retrait :");
                for (Magasin m : MagasinBD.chargerMagasins(connexion.getConnexion())) {
                    System.out.println(m.getIdmag() + " - " + m.getNommag() + " (" + m.getVillemag() + ")");
                }
                System.out.print("Entrez l'ID du magasin : ");
                int idMagasin = 0;
                try {
                    idMagasin = scanner.nextInt();
                    scanner.nextLine(); // Consommer le retour à la ligne
                    magasinChoisi = MagasinBD.getMagasinParId(connexion.getConnexion(), idMagasin);
                    EffacerTerminale.clearConsole();

                    if (magasinChoisi == null) {
                        System.out.println("❌ Magasin introuvable, commande annulée.");
                        return;
                    }

                    System.out.println("Vous avez choisi : " + magasinChoisi.getNommag() + " (" + magasinChoisi.getVillemag() + ")");
                    nouvelleCommande.setMagasinRetrait(magasinChoisi); // Set the chosen store for pickup
                } catch (InputMismatchException e) {
                    EffacerTerminale.clearConsole();

                    System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ID du magasin.");
                    scanner.nextLine();
                    System.out.println("Magasin introuvable, commande annulée.");
                    return;
                }
                break;
            default:
                EffacerTerminale.clearConsole();
                System.out.println("❌ Veuillez choisir un mode de réception valide (1 ou 2).");
                return; 
                
        }
        }
        
        nouvelleCommande.setModeDeReception(modeReception);
        // Removed setMagasinRetrait as it was not in the original code and caused error.

        System.out.println("📦 Nouvelle commande créée (ID : " + nouvelleCommande.getIdCommande() + ")");

        boolean clientRunning = true;
        while (clientRunning) {
            System.out.println("--- Menu Client ---");
            System.out.println("1. Consulter les livres");
            System.out.println("2. Acheter un livre");
            System.out.println("3. Consulter mon panier");
            System.out.println("4. Recommandation de livre");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                if (magasinChoisi != null) {
                    EffacerTerminale.clearConsole();
                    System.out.println("Consultation des livres dans le magasin : " + magasinChoisi.getNommag());
                    System.out.println("Liste des livres disponibles :");
                    // Méthode spécifique non trouvée, affichage de tous les livres
                    List<Livre> livres = LivreBD.chargerLivresParMagasin(connexion.getConnexion(), magasinChoisi.getIdmag());
                    for (Livre l : livres) {
                        System.out.println(l);
                    }
                    break;
                } else {
                    EffacerTerminale.clearConsole();
                    System.out.println("Liste des livres disponibles dans la librairie :");
                    List<Livre> livres = LivreBD.chargerLivres(connexion.getConnexion());
                    for (Livre l : livres) {
                        System.out.println(l);
                    }
                    break; 
                }
                case "2":
                    System.out.println("Achat de livre");
                    System.out.println("Entrez l'ISBN du livre à acheter :");
                    long isbn = 0;
                    try {
                        isbn = scanner.nextLong();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour l'ISBN.");
                        scanner.nextLine();
                        break;
                    }

                    try {
                        Livre livreAchete = LivreBD.getLivreParISBN(connexion.getConnexion(), isbn);
                        System.out.println("Combien de livres voulez-vous acheter ?");
                         
                        int quantite = 0;
                        try {
                            quantite = scanner.nextInt();
                            if (livreAchete.getQuantite()-quantite < 0) {
                                System.out.println("❌ Quantité demandée supérieure à la quantité disponible en stock.");
                                scanner.nextLine(); // Consommer le retour à la ligne
                                break;
                            }
                            scanner.nextLine(); // Consommer le retour à la ligne
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Saisie invalide. Veuillez entrer un nombre entier pour la quantité.");
                            scanner.nextLine();
                            break;
                        }

                        // Original logic for adding to cart
                        livreAchete.setQuantite(quantite); // This line sets quantity on the retrieved book
                        if (livreAchete != null) {
                            nouvelleCommande.ajouterLivreACommande(livreAchete);
                            System.out.println("✅ Livre ajouté à la commande : " + livreAchete.getTitre());
                        } else {
                            System.out.println("❌ Livre non trouvé avec l'ISBN : " + isbn);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Erreur lors de l'achat du livre : " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("Consultation du panier");
                    System.out.println(nouvelleCommande.editerFacture());
                    break;
                case "4":
                    EffacerTerminale.clearConsole();
                    System.out.println("Voici une recommandation de livre :");
                    // Re-commented this line to restore original behavior
                    // Livre livreRecommande = LivreBD.getLivreRecommande(connexion.getConnexion());
                    // Added a placeholder message since the method is commented
                    List<Livre> recommandations = ClientBD.onVousRecommande(connexion.getConnexion(), currentClient);
                    if (recommandations.isEmpty()) {
                        System.out.println("Aucune recommandation disponible pour le moment.");
                    } else {
                        for (Livre livre : recommandations) {
                            System.out.println(livre);
                        }
                    }
                    break;
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
                    System.out.println("⚠️ Choix invalide.");
            }
        }
    }

    
}