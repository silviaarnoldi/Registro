package it.itispaleocapa;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.util.*;

import org.junit.Test;


public class RegistroTest {
    private RegistroElettronico registro;

    
    public void setup() {
        registro = new RegistroElettronico();
        registro.studenti.put("arnoldiSilvia", new HashMap<>());
        registro.studenti.put("arzuffiSimone", new HashMap<>());
        registro.studenti.put("bonacinaGiorgio", new HashMap<>());
        registro.studenti.put("brescianiNicola", new HashMap<>());
        registro.studenti.put("chandraAdam", new HashMap<>());
        registro.studenti.get("arnoldiSilvia").put("italiano", new LinkedList<Integer>());
        registro.studenti.get("arzuffiSimone").put("italiano", new LinkedList<Integer>());
        registro.studenti.get("bonacinaGiorgio").put("italiano", new LinkedList<Integer>());
        registro.studenti.get("brescianiNicola").put("italiano", new LinkedList<Integer>());
        registro.studenti.get("chandraAdam").put("italiano", new LinkedList<Integer>());
        registro.studenti.get("arnoldiSilvia").put("informatica", new LinkedList<Integer>());
        registro.studenti.get("arzuffiSimone").put("informatica", new LinkedList<Integer>());
        registro.studenti.get("bonacinaGiorgio").put("informatica", new LinkedList<Integer>());
        registro.studenti.get("brescianiNicola").put("informatica", new LinkedList<Integer>());
        registro.studenti.get("chandraAdam").put("informatica", new LinkedList<Integer>());
        registro.studenti.get("arnoldiSilvia").put("storia", new LinkedList<Integer>());
        registro.studenti.get("arzuffiSimone").put("storia", new LinkedList<Integer>());
        registro.studenti.get("bonacinaGiorgio").put("storia", new LinkedList<Integer>());
        registro.studenti.get("brescianiNicola").put("storia", new LinkedList<Integer>());
        registro.studenti.get("chandraAdam").put("storia", new LinkedList<Integer>());
    }
    @Test
    public void testRegistraVoto() throws Exception {
        RegistroElettronico registro = new RegistroElettronico();
        
        registro.registraVoto("arnoldiSilvia", "italiano", 7);
        assertEquals(1, registro.studenti.get("arnoldiSilvia").get("italiano").size());
        assertEquals(7, (int)registro.studenti.get("arnoldiSilvia").get("italiano").get(0));
        // test eccezione NomeException
        try {
            registro.registraVoto("rossiMario", "italiano", 6);
           fail("l'ecezzione NomeException è partita");
        } catch (NomeException e) {
            // pass
        }
        // test eccezione MateriaException
        try {
            registro.registraVoto("arnoldiSilvia", "francese", 6);
            fail("l'ecezzione MateriaException è partita");
        } catch (MateriaException e) {
            // pass
        }
    }

    @Test
    public void testVediVoti() throws Exception {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("arnoldiSilvia", "italiano", 7);
        registro.registraVoto("arnoldiSilvia", "storia", 8);
        registro.registraVoto("arzuffiSimone", "italiano", 6);
        registro.registraVoto("arzuffiSimone", "storia", 9);
        registro.vediVoti("arnoldiSilvia");
        // test eccezione NomeException
        try {
            registro.vediVoti("rossiMario");
            fail("l'ecezzione NomeException è partita");
        } catch (NomeException e) {
            // pass
        }
    }

    @Test
    public void testMediaTotale() throws Exception {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("arnoldiSilvia", "italiano", 7);
        registro.registraVoto("arnoldiSilvia", "storia", 8);
        registro.registraVoto("chandraAdam", "italiano", 6);
        registro.registraVoto("chandraAdam", "storia", 9);
        assertEquals(7, registro.mediaTotale("arnoldiSilvia"));
        assertEquals(7, registro.mediaTotale("chandraAdam"));
        // test eccezione NomeException
        try {
            registro.mediaTotale("rossiMario");
            fail("l'ecezzione NomeException è partita");
        } catch (NomeException e) {
            // pass
        }
    }
    @Test
    public void testMediaAlunnoMateria() throws NullMateriaVotiException,NullAlunnoVotiException,MateriaException,NomeException  {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("arnoldiSilvia", "italiano", 7);
        registro.registraVoto("bonacinaGiorgio", "storia", 9);
        assertEquals(7, registro.mediaAlunnoMateria("arnoldiSilvia","italiano"));
        assertEquals(9, registro.mediaAlunnoMateria("bonacinaGiorgio","storia"));
    }
    @Test
    public void testelencoInsufficienti() throws MateriaException,NomeException  {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("brescianiNicola", "italiano", 7);
        registro.registraVoto("brescianiNicola", "italiano", 5);
        registro.registraVoto("bonacinaGiorgio", "storia", 3);
        registro.registraVoto("bonacinaGiorgio", "italiano", 7);
        LinkedList<String> l=new LinkedList<String>();
        l.add("bonacinaGiorgio");
        assertEquals(l, registro.elencoInsufficienti());
    }
    @Test
    public void testalunniMediaMateriaInsufficiente() throws MateriaException,NomeException  {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("brescianiNicola", "italiano", 7);
        registro.registraVoto("bonacinaGiorgio", "storia", 3);
        registro.registraVoto("bonacinaGiorgio", "italiano", 7);
        registro.registraVoto("chandraAdam", "italiano", 2);
        LinkedList<String> l=new LinkedList<String>();
        l.add("bonacinaGiorgio");
        l.add("chandraAdam");
        assertEquals(l, registro.alunniMediaMateriaInsufficiente());
    }
    @Test
    public void teststudentiSenzaInsufficienze() throws MateriaException,NomeException  {
        RegistroElettronico registro = new RegistroElettronico();
        registro.registraVoto("bonacinaGiorgio", "storia", 3);
        registro.registraVoto("bonacinaGiorgio", "italiano", 7);
        registro.registraVoto("chandraAdam", "italiano", 2);
        registro.registraVoto("arnoldiSilvia", "italiano", 8);
        LinkedList<String> l=new LinkedList<String>();
        l.add("arnoldiSilvia");
        assertEquals(l, registro.alunniMediaMateriaInsufficiente());
    }
}

