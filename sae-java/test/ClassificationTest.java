import org.junit.Test;
import static org.junit.Assert.*;

public class ClassificationTest {
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
}
