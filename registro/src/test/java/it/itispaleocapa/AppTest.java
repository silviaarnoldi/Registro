package it.itispaleocapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertEquals(1, aeroporto.clienti.size());
        assertEquals("RossiLuigi123", cliente.codiceCliente);
    }

    @Test
    public void testModificaDatiCliente() {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        aeroporto.aggiungiCliente(cliente);

        String nuovoNome = "Mario";
        String nuovoCognome = "Bianchi";
        String nuovoNazione = "argentina";
        String nuovoCitta = "Mendoza";
        String nuovoDataDiNascita = "1991-03-23";
        
        try {
            aeroporto.modificaDatiCliente(cliente.codiceCliente, nuovoNome, nuovoCognome,nuovoNazione,nuovoCitta,nuovoDataDiNascita);
            Cliente clienteModificato = aeroporto.ricercaClienteCodice(cliente.codiceCliente);
            assertEquals(nuovoNome, clienteModificato.nome);
            assertEquals(nuovoCognome, clienteModificato.cognome);
            assertEquals(nuovoNazione, clienteModificato.nazioneNascita);
            assertEquals(nuovoCitta, clienteModificato.cittaNascita);
            assertEquals(nuovoDataDiNascita, clienteModificato.dataNascita);
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }

    @Test
    public void testEliminaCliente() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.eliminaCliente(cliente.codiceCliente);
        assertNull(aeroporto.ricercaClienteCodice(cliente.codiceCliente));
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
        assertEquals(cognome, clienteTrovato.cognome);
        assertEquals(nome, clienteTrovato.nome);
    }

    @Test
    public void testModificaDatiVolo() {
        Volo volo = new Volo("V001", "Roma", "Parigi", new Date(), "10:00", "13:00", "A1", 250);
        aeroporto.voli.add(volo);
        String nuovoAeroportoPartenza = "Milano";
        String nuovoAeroportoArrivo = "Londra";

        try {
            aeroporto.modificaDatiVolo(volo.codiceVolo, nuovoAeroportoPartenza, nuovoAeroportoArrivo, new Date(), "11:00", "14:00", "B2", 300);
            Volo voloModificato = aeroporto.ricercaVoloCodice(volo.codiceVolo);
            assertEquals(nuovoAeroportoPartenza, voloModificato.aeroportoPartenza);
            assertEquals(nuovoAeroportoArrivo, voloModificato.aeroportoArrivo);
        } catch (VoloNullException e) {
            fail("VoloNullException");
        }
    }

    @Test
    public void testEliminaVolo() throws VoloNullException {
        Volo volo = new Volo("V001", "Roma", "Parigi", new Date(), "10:00", "13:00", "A1", 250);
        aeroporto.voli.add(volo);
        aeroporto.eliminaVolo(volo.codiceVolo);
        assertNull(aeroporto.ricercaVoloCodice(volo.codiceVolo));
    }

    @Test
    public void testRicercaVoloDate() {
        Date data = new Date();
        String aeroportoPartenza = "Roma";
        String aeroportoArrivo = "Parigi";
        String oraPartenza = "10:00";

        Volo volo = new Volo("V001", aeroportoPartenza, aeroportoArrivo, data, oraPartenza, "13:00", "A1", 250);
        aeroporto.voli.add(volo);

        Volo voloTrovato = aeroporto.ricercaVoloDate(data, aeroportoPartenza, aeroportoArrivo, oraPartenza);
        assertNotNull(voloTrovato);
        assertEquals(data, voloTrovato.dataVolo);
        assertEquals(aeroportoPartenza, voloTrovato.aeroportoPartenza);
        assertEquals(aeroportoArrivo, voloTrovato.aeroportoArrivo);
        assertEquals(oraPartenza, voloTrovato.oraPartenza);
    }

    @Test
    public void testAggiungiPrenotazione() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Volo volo = new Volo("V002", "Roma", "Parigi", new Date(), "12:00", "15:00", "B3", 280);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.voli.add(volo);

        try {
            aeroporto.aggiungiPrenotazione(cliente.codiceCliente, volo.codiceVolo);
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniCliente(cliente.codiceCliente);
            assertTrue(prenotazioni.contains(volo.codiceVolo));
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }

    @Test
    public void testEliminaPrenotazione() throws ClienteNullException {
        Cliente cliente = new Cliente("Rossi", "Luigi", "Italia", "Roma", "1990-01-15", 123);
        Volo volo = new Volo("V002", "Roma", "Parigi", new Date(), "12:00", "15:00", "B3", 280);
        aeroporto.aggiungiCliente(cliente);
        aeroporto.voli.add(volo);

        try {
            aeroporto.aggiungiPrenotazione(cliente.codiceCliente, volo.codiceVolo);
            aeroporto.eliminaPrenotazione(cliente.codiceCliente, volo.codiceVolo);
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniCliente(cliente.codiceCliente);
            assertFalse(prenotazioni.contains(volo.codiceVolo));
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
        aeroporto.voli.add(volo1);
        aeroporto.voli.add(volo2);

        try {
            aeroporto.aggiungiPrenotazione(cliente.codiceCliente, volo1.codiceVolo);
            aeroporto.aggiungiPrenotazione(cliente.codiceCliente, volo2.codiceVolo);
            LinkedList<String> prenotazioni = aeroporto.ricercaPrenotazioniVolo(volo1.codiceVolo);
            assertTrue(prenotazioni.contains(cliente.codiceCliente));
        } catch (ClienteNullException e) {
            fail("ClienteNullException");
        }
    }
}