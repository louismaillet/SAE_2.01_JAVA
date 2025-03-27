import org.junit.Test;
import static org.junit.Assert.*;


public class LivreTest {

    @Test
    public void testGetIsbn() {
        Livre livre = new Livre("1234567890123", "Test Book", 100, "2023-01-01", 29.99);
        assert "1234567890123".equals(livre.getIsbn());
        
        Livre livre2 = new Livre("1", "T", 1, "2023-01-02", 20);
        assert "1".equals(livre2.getIsbn());
    }
    


    
}
