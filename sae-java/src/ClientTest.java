public class ClientTest {
    @Test
    Client client = new Client(1, "Dupont", "Jean", "10 rue de la Paix", "75001", "Paris");
    assertEquals(1, client.getIdcli());
    assertEquals("Dupont", client.getNomcli());
    assertEquals("Jean", client.getPrenomcli());
    assertEquals("10 rue de la Paix", client.getAdressecli());
    assertEquals("75001", client.getCodepostal());
    assertEquals("Paris", client.getVillecli());
    Client client2 = new Client(2, "Martin", "Marie", "20 avenue des Champs-Elysées", "75008", "Paris");
    assertEquals(2, client2.getIdcli());
    assertEquals("Martin", client2.getNomcli());
    assertEquals("Marie", client2.getPrenomcli());






    
    
}
