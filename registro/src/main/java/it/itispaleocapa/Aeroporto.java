package it.itispaleocapa;
import java.util.*;
import java.util.Date;

public class Aeroporto {
    LinkedList<Volo> voli;
    LinkedList<Cliente> clienti;
    HashMap<String, LinkedList<String>> prenotazione;

    public Aeroporto() {
        prenotazione = new HashMap<String, LinkedList<String>>();
        voli = new LinkedList<Volo>();
        clienti = new LinkedList<Cliente>();
    }

    public void aggiungiCliente(Cliente cliente) {
        clienti.add(cliente);
        prenotazione.put(cliente.codiceCliente, new LinkedList<String>());
    }

    public void aggiungiPrenotazione(String codiceCliente, String codiceVolo) throws ClienteNullException {
        if (!prenotazione.containsKey(codiceCliente)) {
            throw new ClienteNullException();
        }
        prenotazione.get(codiceCliente).add(codiceVolo);
    }

    public void eliminaCliente(String codice) throws ClienteNullException {
        if (!prenotazione.containsKey(codice)) {
            throw new ClienteNullException();
        }

        Iterator<Cliente> iterator = clienti.iterator();

        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            if (cliente.codiceCliente.equals(codice)) {
                iterator.remove();
                prenotazione.remove(codice);
                break;
            }
        }
    }

    public void eliminaPrenotazione(String codiceCliente, String codiceVolo) throws ClienteNullException {
        if (!prenotazione.containsKey(codiceCliente)) {
            throw new ClienteNullException();
        }

        prenotazione.get(codiceCliente).remove(codiceVolo);
    }

    public void eliminaVolo(String codiceV) throws VoloNullException {
        Iterator<Volo> iterator = voli.iterator();

        while (iterator.hasNext()) {
            Volo volo = iterator.next();
            if (volo.codiceVolo.equals(codiceV)) {
                iterator.remove();
                break;
            }
        }

        if (!iterator.hasNext()) {
            throw new VoloNullException();
        }
    }

    public void modificaDatiCliente(String codiceCliente, String nuovoNome, String nuovoCognome, String nuovaNazione, String nuovaCitta, String nuovaDataNascita) throws ClienteNullException {
        for (Cliente cliente : clienti) {
            if (cliente.codiceCliente.equals(codiceCliente)) {
                cliente.nome = nuovoNome;
                cliente.cognome = nuovoCognome;
                cliente.nazioneNascita = nuovaNazione;
                cliente.cittaNascita = nuovaCitta;
                cliente.dataNascita = nuovaDataNascita;
                return; 
            }
        }

        throw new ClienteNullException();
    }

    public Cliente ricercaClienteCognomeNome(String cognome, String nome) {
        for (Cliente cliente : clienti) {
            if (cliente.cognome.equals(cognome) && cliente.nome.equals(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public void modificaDatiVolo(String codiceVolo, String nuovoAeroportoPartenza, String nuovoAeroportoArrivo, Date nuovaDataVolo, String nuovaOraPartenza, String nuovaOraArrivo, String nuovoPosto, int nuovoCosto) throws VoloNullException {
        for (Volo volo : voli) {
            if (volo.codiceVolo.equals(codiceVolo)) {
                volo.aeroportoPartenza = nuovoAeroportoPartenza;
                volo.aeroportoArrivo = nuovoAeroportoArrivo;
                volo.dataVolo = nuovaDataVolo;
                volo.oraPartenza = nuovaOraPartenza;
                volo.oraArrivo = nuovaOraArrivo;
                volo.posto = nuovoPosto;
                volo.costoVolo = nuovoCosto;
                return; 
            }
        }

        throw new VoloNullException();
    }

    public Volo ricercaVoloCodice(String codiceVolo) {
        for (Volo volo : voli) {
            if (volo.codiceVolo.equals(codiceVolo)) {
                return volo;
            }
        }
        return null;
    }

    public Volo ricercaVoloDate(Date data, String aeroportoPartenza, String aeroportoArrivo, String oraPartenza) {
        for (Volo volo : voli) {
            if (volo.dataVolo.equals(data) &&
                volo.aeroportoPartenza.equals(aeroportoPartenza) &&
                volo.aeroportoArrivo.equals(aeroportoArrivo) &&
                volo.oraPartenza.equals(oraPartenza)) {
                return volo;
            }
        }
        return null;
    }

    public LinkedList<String> ricercaPrenotazioniCliente(String codiceCliente) {
        return prenotazione.getOrDefault(codiceCliente, new LinkedList<String>());
    }

    public LinkedList<String> ricercaPrenotazioniVolo(String codiceVolo) {
        LinkedList<String> prenotazioni = new LinkedList<String>();
        for (Map.Entry<String, LinkedList<String>> entry : prenotazione.entrySet()) {
            if (entry.getValue().contains(codiceVolo)) {
                prenotazioni.add(entry.getKey());
            }
        }
        return prenotazioni;
    }
}