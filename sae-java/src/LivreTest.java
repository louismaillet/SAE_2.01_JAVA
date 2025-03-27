import org.junit.Test;
import static org.junit.Assert.*;


public class LivreTest {

    @Test
    public void testGetIsbn() {
        Livre livre = new Livre("12", "Livre Test", 100, "2023-01-01", 29.99);
        assert "12".equals(livre.getIsbn());
        Livre livre2 = new Livre("1", "deuxieme Livre Test", 1, "2023-01-02", 20);
        assert "1".equals(livre2.getIsbn());
        Livre livre3 = new Livre("0", "Troisieme Livre Test", 1, "2023-01-03", 20);
        assert !"15".equals(livre3.getIsbn());
    }

    @Test
    public void testGetTitre() {
        Livre livre = new Livre("12", "Livre Test", 100, "2023-01-01", 29.99);
        assert "Livre Test".equals(livre.getTitre());
        Livre livre2 = new Livre("1", "deuxieme Livre Test", 1, "2023-01-02", 20);
        assert "deuxieme Livre Test".equals(livre2.getTitre());
        Livre livre3 = new Livre("0", "Troisieme Livre Test", 1, "2023-01-03", 20);
        assert !"quatrieme Livre Test".equals(livre3.getTitre());
    }

    @Test
    public void testGetNbPages() {
        Livre livre = new Livre("12", "Livre Test", 100, "2023-01-01", 29.99);
        assert 100 == livre.getNbPages();
        Livre livre2 = new Livre("1", "deuxieme Livre Test", 1, "2023-01-02", 20);
        assert 1 == livre2.getNbPages();
        Livre livre3 = new Livre("0", "Troisieme Livre Test", 1, "2023-01-03", 20);
        assert 0 != livre3.getNbPages();
    }

    @Test
    public void testGetDatePubli() {
        Livre livre = new Livre("12", "Livre Test", 100, "2023-01-01", 29.99);
        assert "2023-01-01".equals(livre.getDatePubli());
        Livre livre2 = new Livre("1", "deuxieme Livre Test", 1, "2023-01-02", 20);
        assert "2023-01-02".equals(livre2.getDatePubli());
        Livre livre3 = new Livre("0", "Troisieme Livre Test", 1, "2023-01-03", 20);
        assert !"2023-01-04".equals(livre3.getDatePubli());
    }

    @Test
    public void testGetPrix() {
        Livre livre = new Livre("12", "Livre Test", 100, "2023-01-01", 29.99);
        assert 29.99 == livre.getPrix();
        Livre livre2 = new Livre("1", "deuxieme Livre Test", 1, "2023-01-02", 20);
        assert 20 == livre2.getPrix();
        Livre livre3 = new Livre("0", "Troisieme Livre Test", 1, "2023-01-03", 20);
        assert 19 == livre3.getPrix();
    }

    


    
}
/** 
javac -cp "lib/*" -d bin src/*.java
java -cp "bin:lib/*" org.junit.runner.JUnitCore LivreTest 
*/