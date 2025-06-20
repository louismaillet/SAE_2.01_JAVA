package src;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TestLibrairie {

    @Test
    public void testLivreConstructorAndGetters() {
        Livre livre = new Livre(1234567890L, "The Great Book", 300, 2023, 25.50, 10);
        assertEquals(1234567890L, livre.getIsbn());
        assertEquals("The Great Book", livre.getTitre());
        assertEquals(300, livre.getNbPages());
        assertEquals(2023, livre.getDatePubli());
        assertEquals(25.50, livre.getPrix(), 0.001);
        assertEquals(10, livre.getQuantite());
    }

    @Test
    public void testLivreSetQuantite() {
        Livre livre = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        livre.setQuantite(15);
        assertEquals(15, livre.getQuantite());
    }

    @Test
    public void testLivreEquals() {
        Livre livre1 = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        Livre livre2 = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        Livre livre3 = new Livre(456L, "Another Book", 200, 2021, 15.0, 3);
        Livre livre4 = new Livre(123L, "Test Livre Different Price", 100, 2020, 12.0, 5);

        assertTrue(livre1.equals(livre2));
        assertFalse(livre1.equals(livre3));
        assertFalse(livre1.equals(livre4));
        assertFalse(livre1.equals(null));
        assertFalse(livre1.equals("String"));
    }

    @Test
    public void testLivreHashCode() {
        Livre livre1 = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        Livre livre2 = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        Livre livre3 = new Livre(456L, "Another Book", 200, 2021, 15.0, 3);

        assertEquals(livre1.hashCode(), livre2.hashCode());
        assertNotEquals(livre1.hashCode(), livre3.hashCode());
    }

    @Test
    public void testLivreToString() {
        Livre livre = new Livre(123L, "Test Livre", 100, 2020, 10.0, 5);
        String expectedToStringPart = "║ Test Livre";
        assertTrue(livre.toString().contains(expectedToStringPart));
        assertTrue(livre.toString().contains("ISBN: 123"));
    }

    @Test
    public void testMagasinConstructorAndGetters() {
        Magasin magasin = new Magasin(1, "Librairie Centrale", "Paris");
        assertEquals(1, magasin.getIdmag());
        assertEquals("Librairie Centrale", magasin.getNommag());
        assertEquals("Paris", magasin.getVillemag());
        assertNotNull(magasin.getListeLivres());
        assertTrue(magasin.getListeLivres().isEmpty());
    }

    @Test
    public void testMagasinAddLivreNewBook() {
        Magasin magasin = new Magasin(1, "Librairie Centrale", "Paris");
        Livre livre1 = new Livre(101L, "Book A", 200, 2020, 10.0, 5);
        magasin.addLivre(livre1, 10);
        assertEquals(1, magasin.getListeLivres().size());
        assertEquals(Integer.valueOf(10), magasin.getListeLivres().get(livre1));
    }

    @Test
    public void testMagasinAddLivreExistingBook() {
        Magasin magasin = new Magasin(1, "Librairie Centrale", "Paris");
        Livre livre1 = new Livre(101L, "Book A", 200, 2020, 10.0, 5);
        magasin.addLivre(livre1, 10);
        magasin.addLivre(livre1, 5);
        assertEquals(1, magasin.getListeLivres().size());
        assertEquals(Integer.valueOf(15), magasin.getListeLivres().get(livre1));
    }

    @Test
    public void testMagasinEquals() {
        Magasin magasin1 = new Magasin(1, "Librairie Centrale", "Paris");
        Magasin magasin2 = new Magasin(1, "Librairie Centrale", "Paris");
        Magasin magasin3 = new Magasin(2, "Librairie du Coin", "Lyon");
        Magasin magasin4 = new Magasin(1, "Librairie Centrale", "Marseille");

        assertTrue(magasin1.equals(magasin2));
        assertFalse(magasin1.equals(magasin3));
        assertFalse(magasin1.equals(magasin4));
        assertFalse(magasin1.equals(null));
        assertFalse(magasin1.equals("String"));
    }

    @Test
    public void testMagasinHashCode() {
        Magasin magasin1 = new Magasin(1, "Librairie Centrale", "Paris");
        Magasin magasin2 = new Magasin(1, "Librairie Centrale", "Paris");
        Magasin magasin3 = new Magasin(2, "Librairie du Coin", "Lyon");

        assertEquals(magasin1.hashCode(), magasin2.hashCode());
        assertNotEquals(magasin1.hashCode(), magasin3.hashCode());
    }

    @Test
    public void testVendeurConstructorsAndGetters() {
        Magasin magasin = new Magasin(1, "Magasin A", "Ville A");
        Vendeur vendeur1 = new Vendeur(1, "Dupont", "Jean", magasin, RoleVendeur.RESPONSABLE_MAGASIN);
        assertEquals(1, vendeur1.getId());
        assertEquals("Dupont", vendeur1.getNom());
        assertEquals("Jean", vendeur1.getPrenom());
        assertEquals(magasin, vendeur1.getMagasin());
        assertEquals(RoleVendeur.RESPONSABLE_MAGASIN, vendeur1.getRole());

        Vendeur vendeur2 = new Vendeur(2, "Martin", "Sophie");
        assertEquals(2, vendeur2.getId());
        assertEquals("Martin", vendeur2.getNom());
        assertEquals("Sophie", vendeur2.getPrenom());
        assertNull(vendeur2.getMagasin());
        assertEquals(RoleVendeur.VENDEUR, vendeur2.getRole());
    }

    @Test
    public void testVendeurSetMagasin() {
        Vendeur vendeur = new Vendeur(1, "Dupont", "Jean");
        Magasin magasin = new Magasin(1, "Magasin A", "Ville A");
        vendeur.setMagasin(magasin);
        assertEquals(magasin, vendeur.getMagasin());
    }

    @Test
    public void testVendeurAjouterLivreStockNewBook() {
        Magasin magasin = new Magasin(1, "Magasin Test Stock", "TestVille");
        Vendeur vendeur = new Vendeur(1, "Vend", "Test", magasin, RoleVendeur.VENDEUR);
        int initialStockSize = magasin.getListeLivres().size();

        vendeur.ajouterLivreStock(111L, "New Book Title", "New Author", "New Publisher", 2024, 20, 5, 250, 20240101);

        assertEquals(initialStockSize + 1, magasin.getListeLivres().size());
        Livre newBook = new Livre(111L, "New Book Title", 250, 20240101, 20, 5);
        assertEquals(Integer.valueOf(5), magasin.getListeLivres().get(newBook));
    }

    @Test
    public void testVendeurAjouterLivreStockExistingBook() {
        Magasin magasin = new Magasin(1, "Magasin Test Stock", "TestVille");
        Vendeur vendeur = new Vendeur(1, "Vend", "Test", magasin, RoleVendeur.VENDEUR);
        Livre existingBook = new Livre(111L, "Existing Book", 200, 2020, 15.0, 10);
        magasin.addLivre(existingBook, 10);

        vendeur.ajouterLivreStock(111L, "Existing Book", "Existing Author", "Existing Publisher", 2020, 15, 7, 200, 20200101);

        assertEquals(1, magasin.getListeLivres().size());
        assertEquals(Integer.valueOf(17), magasin.getListeLivres().get(existingBook));
    }

    @Test
    public void testVendeurEquals() {
        Magasin magasin1 = new Magasin(1, "Magasin A", "Ville A");
        Magasin magasin2 = new Magasin(2, "Magasin B", "Ville B");

        Vendeur vendeur1 = new Vendeur(1, "Dupont", "Jean", magasin1, RoleVendeur.VENDEUR);
        Vendeur vendeur2 = new Vendeur(1, "Dupont", "Jean", magasin1, RoleVendeur.VENDEUR);
        Vendeur vendeur3 = new Vendeur(2, "Martin", "Sophie", magasin2, RoleVendeur.RESPONSABLE_MAGASIN);
        Vendeur vendeur4 = new Vendeur(1, "Dupont", "Jean", magasin2, RoleVendeur.VENDEUR);

        assertTrue(vendeur1.equals(vendeur2));
        assertFalse(vendeur1.equals(vendeur3));
        assertFalse(vendeur1.equals(vendeur4));
        assertFalse(vendeur1.equals(null));
        assertFalse(vendeur1.equals("String"));
    }

    @Test
    public void testClientConstructorAndGetters() {
        Client client = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        assertEquals(1, client.getId());
        assertEquals("Durand", client.getNomcli());
        assertEquals("Marie", client.getPrenomcli());
        assertEquals("10 Rue de la Paix", client.getAdressecli());
        assertEquals("75001", client.getCodepostal());
        assertEquals("Paris", client.getVillecli());
        assertNotNull(client.getLivresDejaAcheter());
        assertTrue(client.getLivresDejaAcheter().isEmpty());
    }

    @Test
    public void testClientAddLivre() {
        Client client = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        Livre livre1 = new Livre(101L, "Book A", 200, 2020, 10.0, 1);
        client.addLivre(livre1);
        assertEquals(1, client.getLivresDejaAcheter().size());
        assertTrue(client.getLivresDejaAcheter().contains(livre1));
    }

    @Test
    public void testClientEquals() {
        Client client1 = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        Client client2 = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        Client client3 = new Client(2, "Lefevre", "Paul", "20 Avenue des Champs", "75008", "Paris");
        Client client4 = new Client(1, "Durand", "Marie", "15 Rue de la Paix", "75001", "Paris");

        assertTrue(client1.equals(client2));
        assertFalse(client1.equals(client3));
        assertFalse(client1.equals(client4));
        assertFalse(client1.equals(null));
        assertFalse(client1.equals("String"));
    }

    @Test
    public void testClientHashCode() {
        Client client1 = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        Client client2 = new Client(1, "Durand", "Marie", "10 Rue de la Paix", "75001", "Paris");
        Client client3 = new Client(2, "Lefevre", "Paul", "20 Avenue des Champs", "75008", "Paris");

        assertEquals(client1.hashCode(), client2.hashCode());
        assertNotEquals(client1.hashCode(), client3.hashCode());
    }

}