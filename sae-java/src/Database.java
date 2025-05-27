import java.util.*;

public class Database {
    public static List<Client> clients = new ArrayList<>();
    public static List<Vendeur> vendeurs = new ArrayList<>();
    public static List<Livre> livres = new ArrayList<>();
    public static List<Magasin> magasins = new ArrayList<>();
    public static List<Administrateur> admins = new ArrayList<>();

    static {
        // Création de 10 livres
        Livre livre1 = new Livre(1001, "Le Petit Prince", 96, "1943-04-06", 10.5, 5);
        Livre livre2 = new Livre(1002, "1984", 328, "1949-06-08", 15.0, 3);
        Livre livre3 = new Livre(1003, "L'Étranger", 123, "1942-05-19", 12.0, 4);
        Livre livre4 = new Livre(1004, "Harry Potter", 350, "1997-06-26", 20.0, 10);
        Livre livre5 = new Livre(1005, "Le Seigneur des Anneaux", 1200, "1954-07-29", 25.0, 7);
        Livre livre6 = new Livre(1006, "Fahrenheit 451", 249, "1953-10-19", 13.0, 6);
        Livre livre7 = new Livre(1007, "Les Misérables", 1463, "1862-04-03", 18.0, 8);
        Livre livre8 = new Livre(1008, "Le Comte de Monte-Cristo", 1312, "1846-08-28", 17.0, 9);
        Livre livre9 = new Livre(1009, "La Peste", 320, "1947-06-10", 14.0, 5);
        Livre livre10 = new Livre(1010, "Le Rouge et le Noir", 576, "1830-11-01", 16.0, 4);
        livres.addAll(Arrays.asList(livre1, livre2, livre3, livre4, livre5, livre6, livre7, livre8, livre9, livre10));

        // Création de 5 magasins
        Magasin magasin1 = new Magasin(1, "Librairie Centrale", "Orléans");
        Magasin magasin2 = new Magasin(2, "Librairie du Centre", "Paris");
        Magasin magasin3 = new Magasin(3, "Librairie Sud", "Tours");
        Magasin magasin4 = new Magasin(4, "Librairie Nord", "Lille");
        Magasin magasin5 = new Magasin(5, "Librairie Ouest", "Nantes");
        magasins.addAll(Arrays.asList(magasin1, magasin2, magasin3, magasin4, magasin5));

        // Répartition des livres dans les magasins
        magasin1.addLivre(livre1, 5);
        magasin1.addLivre(livre2, 3);
        magasin1.addLivre(livre3, 2);

        magasin2.addLivre(livre4, 7);
        magasin2.addLivre(livre5, 4);
        magasin2.addLivre(livre6, 6);

        magasin3.addLivre(livre7, 8);
        magasin3.addLivre(livre8, 5);

        magasin4.addLivre(livre9, 9);
        magasin4.addLivre(livre10, 4);

        magasin5.addLivre(livre1, 2);
        magasin5.addLivre(livre5, 3);
        magasin5.addLivre(livre9, 1);

        // Création de 5 vendeurs
        Vendeur vendeur1 = new Vendeur(1, "Dupont", "Jean", magasin1, RoleVendeur.VENDEUR);
        Vendeur vendeur2 = new Vendeur(2, "Durand", "Sophie", magasin2, RoleVendeur.VENDEUR);
        Vendeur vendeur3 = new Vendeur(3, "Martin", "Paul", magasin3, RoleVendeur.VENDEUR);
        Vendeur vendeur4 = new Vendeur(4, "Bernard", "Julie", magasin4, RoleVendeur.VENDEUR);
        Vendeur vendeur5 = new Vendeur(5, "Petit", "Luc", magasin5, RoleVendeur.VENDEUR);
        vendeurs.addAll(Arrays.asList(vendeur1, vendeur2, vendeur3, vendeur4, vendeur5));

        // Création de 5 clients
        Client client1 = new Client(1, "Martin", "Claire", "12 rue des Fleurs", "45000", "Orléans");
        Client client2 = new Client(2, "Bernard", "Luc", "5 avenue des Champs", "75000", "Paris");
        Client client3 = new Client(3, "Durand", "Sophie", "8 rue Victor Hugo", "37000", "Tours");
        Client client4 = new Client(4, "Petit", "Julien", "3 rue Nationale", "59000", "Lille");
        Client client5 = new Client(5, "Lefevre", "Emma", "10 rue de la Loire", "44000", "Nantes");
        clients.addAll(Arrays.asList(client1, client2, client3, client4, client5));
        client1.addLivre(livre1);
        client1.addLivre(livre2);
        client2.addLivre(livre4);
        client3.addLivre(livre7);
        client4.addLivre(livre9);
        client5.addLivre(livre5);

        // Création de 2 administrateurs
        Administrateur admin1 = new Administrateur(1, "Admin", "Super");
        Administrateur admin2 = new Administrateur(2, "Boss", "Max");
        admins.addAll(Arrays.asList(admin1, admin2));
        admin1.ajouterLibrairie(magasin1);
        admin1.ajouterLibrairie(magasin2);
        admin2.ajouterLibrairie(magasin3);
        admin2.ajouterLibrairie(magasin4);
        admin2.ajouterLibrairie(magasin5);
    }
}