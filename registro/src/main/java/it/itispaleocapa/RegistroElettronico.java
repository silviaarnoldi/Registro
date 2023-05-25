package it.itispaleocapa;
import java.util.*;
import java.util.function.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RegistroElettronico
{
    HashMap<String, HashMap<String, LinkedList<Integer>>> studenti;
    
    public RegistroElettronico()
    { 
        studenti = new HashMap<>();
        crearegistro();
        
        
        
    }
    private void crearegistro(){
        studenti.put("arnoldiSilvia", new HashMap<>());
        studenti.put("arzuffiSimone", new HashMap<>());
        studenti.put("bonacinaGiorgio", new HashMap<>());
        studenti.put("brescianiNicola", new HashMap<>());
        studenti.put("chandraAdam", new HashMap<>());
        studenti.get("arnoldiSilvia").put("italiano", new LinkedList<Integer>());
        studenti.get("arzuffiSimone").put("italiano", new LinkedList<Integer>());
        studenti.get("bonacinaGiorgio").put("italiano", new LinkedList<Integer>());
        studenti.get("brescianiNicola").put("italiano", new LinkedList<Integer>());
        studenti.get("chandraAdam").put("italiano", new LinkedList<Integer>());
        studenti.get("arnoldiSilvia").put("informatica", new LinkedList<Integer>());
        studenti.get("arzuffiSimone").put("informatica", new LinkedList<Integer>());
        studenti.get("bonacinaGiorgio").put("informatica", new LinkedList<Integer>());
        studenti.get("brescianiNicola").put("informatica", new LinkedList<Integer>());
        studenti.get("chandraAdam").put("informatica", new LinkedList<Integer>());
        studenti.get("arnoldiSilvia").put("storia", new LinkedList<Integer>());
        studenti.get("arzuffiSimone").put("storia", new LinkedList<Integer>());
        studenti.get("bonacinaGiorgio").put("storia", new LinkedList<Integer>());
        studenti.get("brescianiNicola").put("storia", new LinkedList<Integer>());
        studenti.get("chandraAdam").put("storia", new LinkedList<Integer>());
    }
    public void registraVoto(String nome, String materia, int voto) throws NomeException,MateriaException{ //se non esiste il nome o la meteria parte l'ecezzione
        if(studenti.get(nome)==null){
            throw new NomeException();
        }
        if(studenti.get(nome).get(materia)==null){
            throw new MateriaException();
        }
        studenti.get(nome).get(materia).add(voto);
    }
    public void vediVoti(String nomeA)throws NomeException {
        if(studenti.get(nomeA)==null){
            throw new NomeException();
        }
        BiConsumer<String,HashMap> stampa=(nome, valutazioni)->{
            if (nome.equals(nomeA)) {
                System.out.println("Voti di " + nome + ":");
                valutazioni.forEach((materia, voti) -> {
                    System.out.println(" " + materia + ": " + voti);
                });
            }
        };
        studenti.forEach(stampa);
    } 
    public int mediaTotale(String nome)throws NomeException{
        if(studenti.get(nome)==null){
            throw new NomeException();
        }
        HashMap<String, LinkedList<Integer>> votiMateria = studenti.get(nome);
        int totale = 0;
        int count = 0;
        for (String materia : votiMateria.keySet()) {
            LinkedList<Integer> voti = votiMateria.get(materia);
            totale += voti.stream().mapToInt(Integer::intValue).sum();
            count += voti.size();
        }
        return totale/count;
    }
    public int mediaAlunnoMateria(String nomeA, String materia) throws NullAlunnoVotiException,NullMateriaVotiException {
        HashMap<String, LinkedList<Integer>> votiA= studenti.get(nomeA);
        if (votiA == null) {
             throw new NullAlunnoVotiException();
        }

        LinkedList<Integer> votiMateria = votiA.get(materia);
        if (votiMateria == null) {
            throw new NullMateriaVotiException();
        }

        int somma = votiMateria.stream().reduce(0, (a, b) -> a + b);
        return somma / votiMateria.size();
    }

   public LinkedList<String> elencoInsufficienti() {
        LinkedList<String> insufficienti = new LinkedList<>();
        studenti.forEach((nome, votiMateria) -> {
            boolean alunnoInsufficiente = false;
            for (Map.Entry<String, LinkedList<Integer>> entry : votiMateria.entrySet()) {
                String materia = entry.getKey();
                LinkedList<Integer> voti = entry.getValue();
                if (!voti.isEmpty()) {
                    int media = calcoloMedia(voti);
                    if (media < 6) {
                        alunnoInsufficiente = true;
                        break;
                    }
                }
            }
            if (alunnoInsufficiente) {
                insufficienti.add(nome);
            }
        });
        return insufficienti;
    }
    private int calcoloMedia(LinkedList<Integer> voti) {
        int somma = 0;
        for (int voto : voti) {
            somma += voto;
        }
        return somma / voti.size();
    }
    public LinkedList<String> alunniMediaMateriaInsufficiente() {
        LinkedList<String> alunni = new LinkedList<String>();
        Function<LinkedList<Integer>, Integer> mediaVoti = voti -> {
            int totale = voti.stream().mapToInt(Integer::intValue).sum();
            return voti.isEmpty() ? 0 : totale / voti.size();
        };
        studenti.forEach((nome, voti) -> {
            boolean mediaInsufficiente = false;
            for (String materia : voti.keySet()) {
                LinkedList<Integer> votiMateria = voti.get(materia);
                if (!votiMateria.isEmpty()) {
                    int media = mediaVoti.apply(votiMateria);
                    if (media < 6) {
                        mediaInsufficiente = true;
                        break;
                    }
                }
            }
            if (mediaInsufficiente) {
                alunni.add(nome);
            }
        });
        return alunni;
    }
    public LinkedList<String> studentiSenzaInsufficienze() {
    LinkedList<String> s= new LinkedList<String>();
    studenti.forEach((nome, votiMateria) -> {
        boolean insuff = false;
        for (LinkedList<Integer> voti : votiMateria.values()) {
            for (int voto : voti) {
                if (voto < 6) {
                    insuff = true;
                    break;
                }
            }
            if (insuff) {
                break;
            }
        }
        if (!insuff) {
            s.add(nome);
        }
    });
    return s;
    }    

}

