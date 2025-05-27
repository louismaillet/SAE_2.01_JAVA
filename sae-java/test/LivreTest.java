import org.junit.Test;
import static org.junit.Assert.*;

public class LivreTest {

    @Test
    public void testGetIsbn() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99,8);
        assertEquals(12, livre.getIsbn());
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20,8);
        assertEquals(1, livre2.getIsbn());
        Livre livre3 = new Livre(0, "Troisieme Livre Test", 1, "2023-01-03", 20);
        assertNotEquals(15, livre3.getIsbn());
    }

    @Test
    public void testGetTitre() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99);
        assertEquals("Livre Test", livre.getTitre());
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20);
        assertEquals("deuxieme Livre Test", livre2.getTitre());
        Livre livre3 = new Livre(0, "Troisieme Livre Test", 1, "2023-01-03", 20);
        assertNotEquals("quatrieme Livre Test", livre3.getTitre());
    }

    @Test
    public void testGetNbPages() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99);
        assertEquals(100, livre.getNbPages());
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20);
        assertEquals(1, livre2.getNbPages());
        Livre livre3 = new Livre(0, "Troisieme Livre Test", 1, "2023-01-03", 20);
        assertNotEquals(0, livre3.getNbPages());
    }

    @Test
    public void testGetDatePubli() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99);
        assertEquals("2023-01-01", livre.getDatePubli());
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20);
        assertEquals("2023-01-02", livre2.getDatePubli());
        Livre livre3 = new Livre(0, "Troisieme Livre Test", 1, "2023-01-03", 20);
        assertNotEquals("2023-01-04", livre3.getDatePubli());
    }

    @Test
    public void testGetPrix() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99);
        assertEquals(29.99, livre.getPrix(), 0.00);
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20.0);
        assertEquals(20.0, livre2.getPrix(), 0.00);
        Livre livre3 = new Livre(0, "Troisieme Livre Test", 1, "2023-01-03", 20.0);
        assertNotEquals(20.1, livre3.getPrix(), 0.00);
    }

    
    /*
    @Test
    public void testToString() {
        Livre livre = new Livre(12, "Livre Test", 100, "2023-01-01", 29.99);
        String rep = "Le livre Livre Test a pour ISBN 12, il fait 100 pages, a été publié le 2023-01-01 et coûte 29.99 euros";
        assertEquals(rep, livre.toString());
        Livre livre2 = new Livre(1, "deuxieme Livre Test", 1, "2023-01-02", 20.0);
        String rep2 = "Le livre deuxieme Livre Test a pour ISBN 1, il fait 1 pages, a été publié le 2023-01-02 et coûte 19.0 euros";
        assertNotEquals(rep2, livre2.toString());
    }
    */

}
