import org.junit.Test;
import static org.junit.Assert.*;

public class ClassificationTest {
    // test getter + toString
    @Test 
    public void testGetId() {
        Classification classification = new Classification(1, "Science");
        assertEquals(1, classification.getId());

        Classification classification2 = new Classification(2, "Fiction");
        assertEquals(2, classification2.getId());

        Classification classification3 = new Classification(3, "Histoire");
        assertNotEquals(4, classification3.getId());
    }

    @Test
    public void testGetNomclass() {
        Classification classification = new Classification(1, "Science");
        assertEquals("Science", classification.getNomclass());

        Classification classification2 = new Classification(2, "Fiction");
        assertEquals("Fiction", classification2.getNomclass());

        Classification classification3 = new Classification(3, "Histoire");
        assertNotEquals("Math", classification3.getNomclass());
    }
    @Test
    public void testToString() {
        Classification classification = new Classification(1, "Science");
        String rep = "L'id de la classification est 1 et le nom de la classification est Science";
        assertEquals(rep, classification.toString());

        Classification classification2 = new Classification(2, "Fiction");
        String rep2 = "L'id de la classification est 2 et le nom de la classification est Fiction";
        assertEquals(rep2, classification2.toString());

        Classification classification3 = new Classification(3, "Histoire");
        String rep3 = "L'id de la classification est 3 et le nom de la classification est Histoire";
        assertEquals(rep3, classification3.toString());
    }
}
