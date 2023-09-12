package it.itispaleocapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
class AppTest {
    Aeroporto aeroporto;

    @BeforeEach
    public void setUp() {
        aeroporto = new Aeroporto();
    }

    @Test
    public void testAggiungiCliente() {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        aeroporto.aggiungiCliente(cliente);
        assertEquals(1, aeroporto.getClienti().size());
        assertEquals("RossiLuigi123", cliente.getCodiceCliente());
    }

    @Test
    public void testModificaDatiCliente() {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        aeroporto.aggiungiCliente(cliente);

        String nuovoNome = "Mario";
        String nuovoCognome = "Bianchi";
        
        try {
            aeroporto.modificaDatiCliente(cliente.getCodiceCliente(), nuovoNome, nuovoCognome);
            Cliente clienteModificato = aeroporto.ricercaClienteCodice(cliente.getCodiceCliente());
            assertEquals(nuovoNome, clienteModificato.getNome());
            assertEquals(nuovoCognome, clienteModificato.getCognome());
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }

    @Test
    public void testEliminaCliente() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.eliminaCliente(cliente.getCodiceCliente());
        assertNull(aeroporto.ricercaClienteCodice(cliente.getCodiceCliente()));
    }

    @Test
    public void testRicercaClienteCognomeNome() {
        Cliente cliente1 = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Cliente cliente2 = new Cliente("Benefici", "Margherita", "Italia", "Milano", "1985-07-22", 456);
        aeroporto.aggiungiCliente(cliente1);
        aeroporto.aggiungiCliente(cliente2);

        String cognome = "Benefici";
        String nome = "Margherita";

        Cliente clienteTrovato = aeroporto.ricercaClienteCognomeNome(cognome, nome);
        assertNotNull(clienteTrovato);
        assertEquals(cognome, clienteTrovato.getCognome());
        assertEquals(nome, clienteTrovato.getNome());
    }

    @Test
    public void testAggiungiVolo() {
        Volo volo = new Volo("V001", "Roma", "Parigi", new Date(), "10:00", "13:00", "A1", 250);
        aeroporto.aggiungiVolo(volo);
        assertEquals(1, aeroporto.getVoli().size());
    }

    @Test
    public void testModificaDatiVolo() {
        Volo volo = new Volo("V001", "Roma", "Parigi", new Date(), "10:00", "13:00", "A1", 250);
        aeroporto.aggiungiVolo(volo);

        String nuovoAeroportoPartenza = "Milano";
        String nuovoAeroportoArrivo = "Londra";

        try {
            aeroporto.modificaDatiVolo(volo.getCodiceVolo(), nuovoAeroportoPartenza, nuovoAeroportoArrivo, new Date(), "11:00", "14:00", "B2", 300);
            Volo voloModificato = aeroporto.ricercaVoloCodice(volo.getCodiceVolo());
            assertEquals(nuovoAeroportoPartenza, voloModificato.getAeroportoPartenza());
            assertEquals(nuovoAeroportoArrivo, voloModificato.getAeroportoArrivo());
        } catch (VoloNullException e) {
            fail("VoloNullException");
        }
    }

    @Test
    public void testEliminaVolo() throws VoloNullException {
        Volo volo = new Volo("V001", "Roma", "Parigi", new Date(), "10:00", "13:00", "A1", 250);
        aeroporto.aggiungiVolo(volo);
        aeroporto.eliminaVolo(volo.getCodiceVolo());
        assertNull(aeroporto.ricercaVoloCodice(volo.getCodiceVolo()));
    }

    @Test
    public void testRicercaVoloDate() {
        Date data = new Date();
        String aeroportoPartenza = "Roma";
        String aeroportoArrivo = "Parigi";
        String oraPartenza = "10:00";

        Volo volo = new Volo("V001", aeroportoPartenza, aeroportoArrivo, data, oraPartenza, "13:00", "A1", 250);
        aeroporto.aggiungiVolo(volo);

        Volo voloTrovato = aeroporto.ricercaVoloDate(data, aeroportoPartenza, aeroportoArrivo, oraPartenza);
        assertNotNull(voloTrovato);
        assertEquals(data, voloTrovato.getDataVolo());
        assertEquals(aeroportoPartenza, voloTrovato.getAeroportoPartenza());
        assertEquals(aeroportoArrivo, voloTrovato.getAeroportoArrivo());
        assertEquals(oraPartenza, voloTrovato.getOraPartenza());
    }

    @Test
    public void testAggiungiPrenotazione() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Volo volo = new Volo("V002", "Roma", "Parigi", new Date(), "12:00", "15:00", "B3", 280);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.aggiungiVolo(volo);

        try {
            aeroporto.aggiungiPrenotazione(cliente.getCodiceCliente(), volo.getCodiceVolo());
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniCliente(cliente.getCodiceCliente());
            assertTrue(prenotazioni.contains(volo.getCodiceVolo()));
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }

    @Test
    public void testEliminaPrenotazione() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Volo volo = new Volo("V002", "Roma", "Parigi", new Date(), "12:00", "15:00", "B3", 280);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.aggiungiVolo(volo);

        try {
            aeroporto.aggiungiPrenotazione(cliente.getCodiceCliente(), volo.getCodiceVolo());
            aeroporto.eliminaPrenotazione(cliente.getCodiceCliente(), volo.getCodiceVolo());
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniCliente(cliente.getCodiceCliente());
            assertFalse(prenotazioni.contains(volo.getCodiceVolo()));
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }

    @Test
    public void testRicercaPrenotazioniVolo() {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Volo volo1 = new Volo("V002", "Roma", "Parigi", new Date(), "12:00", "15:00", "B3", 280);
        Volo volo2 = new Volo("V003", "Roma", "Londra", new Date(), "14:00", "17:00", "C2", 320);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.aggiungiVolo(volo1);
        aeroporto.aggiungiVolo(volo2);

        try {
            aeroporto.aggiungiPrenotazione(cliente.getCodiceCliente(), volo1.getCodiceVolo());
            aeroporto.aggiungiPrenotazione(Acliente.getCodiceCliente(), volo2.getCodiceVolo());
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniVolo(volo1.getCodiceVolo());
            assertTrue(prenotazioni.contains(cliente.getCodiceCliente()));
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }
}