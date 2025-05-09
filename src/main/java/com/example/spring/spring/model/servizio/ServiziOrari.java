package com.example.spring.spring.model.servizio;

import com.example.spring.spring.interfacce.Prenotabile;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.parser.AppConfigParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "serviziOrari")
public class ServiziOrari extends Servizi implements Prenotabile<PrenotazioneServizioOrario> {
    @JsonProperty("fasceOrarie")
    protected List<TimeInterval> fasceOrarie;
    @JsonProperty("disponibilitaPerFascia")
    private int disponibilitaPerFascia;

    // Mappa: data => (fascia <=> motorini già prenotati)
    @JsonProperty("prenotazioni")

    private Map<LocalDate, Map<String, Integer>> prenotazioni = new HashMap<>();

    public ServiziOrari() {
        super();
        this.fasceOrarie = new ArrayList<>();
        this.disponibilitaPerFascia = 0;
    }


    public ServiziOrari(String nome, String descrizione, double prezzo, List<TimeInterval> fasceOrarie, int disponibilitaPerFascia) {
        super(nome, descrizione, prezzo);

        this.disponibilitaPerFascia = disponibilitaPerFascia;

        if(fasceOrarie==null){
            System.out.println("Nessuna fascia oraria presente, il servizio userà le fasce predefinite");
            AppConfigParser parser = new AppConfigParser("/home/dash/VsCodeProgect/uni/spring/spring/src/main/resources/config.xml");
            this.fasceOrarie = new ArrayList<>(parser.getFascePerServizio());

        }else{
            this.fasceOrarie = fasceOrarie;
        }
    }



     //Verifica se la fascia oraria richiesta è valida per questo servizio.
     //return true se la fascia è presente nell'elenco delle fasce orarie del servizio, false altrimenti.
    public boolean isFasciaOrariaValida(TimeInterval fascia) {
        return this.fasceOrarie.contains(fascia);
    }



     //Verifica la disponibilità per una specifica data, fascia oraria e quantità.
     //return true se c'è abbastanza disponibilità, false altrimenti.
    public boolean verificaDisponibilita(LocalDate data, int numeroGiorni, TimeInterval fascia, int quantita) {
        // Controllo preliminare se la fascia oraria richiesta è valida per questo servizio.
        if (!isFasciaOrariaValida(fascia)) {
            System.err.printf("Errore: La fascia oraria %s non è offerta per questo servizio.%n", fascia);
            return false;
        }
        if (quantita <= 0) {
            System.err.println("Errore: La quantità da prenotare deve essere positiva.");
            return false;
        }

        LocalDate dataFine = data.plusDays(numeroGiorni<2?0:numeroGiorni-1);
        LocalDate dataCorrente = data;

        //stringa per la fascia, usata per confrontarla con le fasce presenti nella mappa
        String fasciaKey = fascia.toString();

        while (!dataCorrente.isAfter(dataFine)) {//itera su date e fasce per verificare che ci siano abbastanza posti disponibili
            // Ottieni la mappa delle prenotazioni per la data specificata. Prende la data
            Map<String, Integer> prenotazioniPerFasciaInData = prenotazioni.getOrDefault(dataCorrente, Map.of());

            // Ottieni il numero di prenotazioni già esistenti per quella specifica fascia oraria. Fascia interna alla data presa prima
            int prenotazioniAttuali = prenotazioniPerFasciaInData.getOrDefault(fasciaKey, 0); // <-- Corretto qui

            // Verifica se aggiungendo la nuova quantità si supera la disponibilità per fascia.
            boolean disponibile = (prenotazioniAttuali + quantita) <= this.disponibilitaPerFascia;

            if (!disponibile) {
                System.out.printf("Non disponibile per data %s, fascia %s. Prenotazioni attuali: %d, Richiesta: %d, Disponibilità: %d%n",
                        dataCorrente, fascia, prenotazioniAttuali, quantita, this.disponibilitaPerFascia);
                return false;
            } else {
                System.out.printf("Disponibile per data %s, fascia %s. Prenotazioni attuali: %d, Richiesta: %d, Disponibilità: %d%n",
                        dataCorrente, fascia, prenotazioniAttuali, quantita, this.disponibilitaPerFascia);
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        System.out.println("Disponibilità verificata per tutte le date.");
        return true; // Ritorna true solo se disponibile per TUTTE le date nel range
    }


    //Conferma e registra una prenotazione per una data, fascia oraria e quantità.
    public void confermaPrenotazione(LocalDate data, int numeroGiorni, TimeInterval fascia, int quantita) {
        // Controllo di sicurezza (anche se la logica esterna dovrebbe già aver verificato)
        if (quantita <= 0 || !isFasciaOrariaValida(fascia)) {
            System.err.println("Tentativo di conferma prenotazione non valida.");
            return;
        }

        LocalDate dataFine = data.plusDays(numeroGiorni<2?0:numeroGiorni-1);
        LocalDate dataCorrente = data;

        while (!dataCorrente.isAfter(dataFine)) {


            // Ottengo la mappa delle prenotazioni per la data specificata.
            // Se non esiste, la creo e aggiungo alla mappa principale. computeIfAbsent gestisce il caso in cui la data non abbia ancora prenotazioni.
            Map<String, Integer> prenotazioniPerFasciaInData = prenotazioni.computeIfAbsent(dataCorrente, k -> new HashMap<>());

            // Aggiorna il conteggio per la fascia oraria specifica aggiungendo la quantità richiesta.
            // getOrDefault gestisce il caso in cui la fascia non abbia ancora prenotazioni.
            prenotazioniPerFasciaInData.put(fascia.toString(), prenotazioniPerFasciaInData.getOrDefault(fascia.toString(), 0) + quantita);

            System.out.printf("Prenotazione confermata per %d posti/oggetti in data %s, fascia %s. Totale prenotazioni ora: %d%n",
                    quantita, data, fascia, prenotazioniPerFasciaInData.get(fascia.toString()));

            dataCorrente = dataCorrente.plusDays(1);
        }

    }


    //metodo vero e proprio per prenotare il servizio che al suo interno chiama la verifica edf il conferma
    @Override
    public boolean prenota(PrenotazioneServizioOrario prenotazione) {
        // Validazione input prenotazione
        if (prenotazione == null || prenotazione.getDataInizio() == null || prenotazione.getFasciaOraria() == null || prenotazione.getQuantitaPrenotata() <= 0) {
            System.err.println("Errore: Dettagli della prenotazione non validi o incompleti.");
            return false;
        }

        LocalDate data = prenotazione.getDataInizio();
        TimeInterval fascia = prenotazione.getFasciaOraria();
        int quantita = prenotazione.getQuantitaPrenotata();
        int numeroGiorni = prenotazione.getNumeroGiorni();

        //Verifica disponibilità
        boolean disponibile = verificaDisponibilita(data, numeroGiorni, fascia, quantita);

        //Se disponibile, conferma la prenotazione
        if (disponibile) {
            confermaPrenotazione(data, numeroGiorni, fascia, quantita);
            return true;
        } else {
            //nulla perchè la print del problema è fatta nel metodo verificaDisponibilita
            return false;
        }
    }


    public List<TimeInterval> getFasceOrarie() {
        return new ArrayList<>(fasceOrarie); // Restituisce una copia
    }

    public int getDisponibilitaPerFascia() {
        return disponibilitaPerFascia;
    }

    public Map<LocalDate, Map<String, Integer>> getPrenotazioni() {
        // Restituisce una copia superficiale per evitare modifiche esterne dirette alla mappa principale
        return new HashMap<>(prenotazioni);
    }

    // Metodo per ottenere la disponibilità residua
    public int getDisponibilitaResidua(LocalDate data, TimeInterval fascia) {
        if (!isFasciaOrariaValida(fascia)) {
            return 0; // Non disponibile se la fascia non è valida per il servizio
        }
        Map<String, Integer> prenotazioniPerFasciaInData = prenotazioni.getOrDefault(data, Map.of());
        int prenotazioniAttuali = prenotazioniPerFasciaInData.getOrDefault(fascia.toString(), 0);
        return Math.max(0, this.disponibilitaPerFascia - prenotazioniAttuali);
    }



    private String convertTimeIntervalToKey(TimeInterval interval) {
        return interval.getStart().toString() + "-" + interval.getEnd().toString();
    }

    // Se hai bisogno di convertire da stringa a TimeInterval
    private TimeInterval convertKeyToTimeInterval(String key) {
        String[] parts = key.split("-");
        LocalTime start = LocalTime.parse(parts[0]);
        LocalTime end = LocalTime.parse(parts[1]);
        return new TimeInterval(start, end);
    }

    public void setFasceOrarie(List<TimeInterval> fasceOrarie) {
        this.fasceOrarie = fasceOrarie;
    }



}
