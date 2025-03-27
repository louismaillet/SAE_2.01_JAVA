import org.junit.Test;
import static org.junit.Assert.*;


public class ClientTest {

    @Test
    public void testClientDetails() {
        Client client = new Client(1, "Dupont", "Jean", "10 rue de la Paix", "75001", "Paris");
        assertEquals(1, client.getIdcli());
        assert("Dupont".equals(client.getNomcli()));
        assertEquals("Jean", client.getPrenomcli());
        assertEquals("10 rue de la Paix", client.getAdressecli());
        assertEquals("75001", client.getCodepostal());
        assertEquals("Paris", client.getVillecli());
    }

    @Test
    public void testClient2Details() {
        Client client2 = new Client(2, "Martin", "Marie", "20 rue de la Liberté", "75002", "Paris");
        assertEquals(2, client2.getIdcli());
        assertEquals("Martin", client2.getNomcli());
        assertEquals("Marie", client2.getPrenomcli());
        assertEquals("20 rue de la Liberté", client2.getAdressecli());
        assertEquals("75002", client2.getCodepostal());
        assertEquals("Paris", client2.getVillecli());
    
    }
    @Test
    public void testClient3Details() {
        Client client3 = new Client(3, "Durand", "Pierre", "30 rue de la République", "75003", "Paris");
        assertEquals(3, client3.getIdcli());
        assertEquals("Durand", client3.getNomcli());
        assertEquals("Pierre", client3.getPrenomcli());
        assertEquals("30 rue de la République", client3.getAdressecli());
        assertEquals("75003", client3.getCodepostal());
        assertEquals("Paris", client3.getVillecli());
    }
}