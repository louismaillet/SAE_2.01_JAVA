import org.junit.Test;
import static org.junit.Assert.*;

public class MagasinTest {

    // test des getter + tostring
    @Test
    public void testGetter() {
        Magasin magasin = new Magasin(1, "Magasin Test", "Paris");
        assertEquals(1, magasin.getIdmag());
        assertEquals("Magasin Test", magasin.getNommag());
        assertEquals("Paris", magasin.getVillemag());
        String rep = "Le magasin Magasin Test est situé à Paris et a pour ID 1";
        assertEquals(rep, magasin.toString());
    }
    @Test
    public void testGetter2() {
        Magasin magasin2 = new Magasin(2, "Magasin Test 2", "Lyon");
        assertNotEquals(1, magasin2.getIdmag());
        assertNotEquals("Magasin Test", magasin2.getNommag());
        assertNotEquals("Paris", magasin2.getVillemag());
        String rep = "Le magasin Magasin Test 2 est situé à Lyon et a pour ID 1";
        assertNotEquals(rep, magasin2.toString());
    }
    

}