package com.example.spring.spring.model.servizio;

import com.example.spring.spring.interfacce.Prenotabile;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
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

    // Mappa: data → (fascia → motorini già prenotati)
    @JsonProperty("prenotazioni")

    private Map<LocalDate, Map<String, Integer>> prenotazioni = new HashMap<>();

    public ServiziOrari() {
        super();
        this.fasceOrarie = new ArrayList<>();
        this.disponibilitaPerFascia = 0;
    }


    public ServiziOrari(String nome, String descrizione, double prezzo, List<TimeInterval> fasceOrarie, int disponibilitaPerFascia) {
        super(nome, descrizione, prezzo);
        this.fasceOrarie = new ArrayList<>(fasceOrarie);;
        this.disponibilitaPerFascia = disponibilitaPerFascia;
    }



    /**
     * Verifica se la fascia oraria richiesta è valida per questo servizio.
     * @param fascia La fascia oraria da controllare.
     * @return true se la fascia è presente nell'elenco delle fasce orarie del servizio, false altrimenti.
     */
    public boolean isFasciaOrariaValida(TimeInterval fascia) {
        return this.fasceOrarie.contains(fascia);
    }


    /**
     * Verifica la disponibilità per una specifica data, fascia oraria e quantità.
     *
     * @param data Data della prenotazione.
     * @param fascia Fascia oraria richiesta.
     * @param quantita Numero di posti/oggetti da prenotare.
     * @return true se c'è abbastanza disponibilità, false altrimenti.
     */
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

        LocalDate dataFine = data.plusDays(numeroGiorni);
        LocalDate dataCorrente = data;

        // Ottieni la chiave stringa per la fascia, che useremo in modo consistente
        String fasciaKey = fascia.toString(); // <-- Genera la chiave stringa qui

        while (!dataCorrente.isAfter(dataFine)) {
            // Ottieni la mappa delle prenotazioni per la data specificata.
            Map<String, Integer> prenotazioniPerFasciaInData = prenotazioni.getOrDefault(dataCorrente, Map.of());

            // Ottieni il numero di prenotazioni già esistenti per quella specifica fascia oraria.
            // Usa la CHIAVE STRINGA qui, consistente con confermaPrenotazione
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

    /**
     * Conferma e registra una prenotazione per una data, fascia oraria e quantità.
     * Questo metodo dovrebbe essere chiamato SOLO DOPO aver verificato la disponibilità.
     *
     * @param data Data della prenotazione.
     * @param fascia Fascia oraria prenotata.
     * @param quantita Numero di posti/oggetti prenotati.
     */
    public void confermaPrenotazione(LocalDate data, int numeroGiorni, TimeInterval fascia, int quantita) {
        // Controllo di sicurezza (anche se la logica esterna dovrebbe già aver verificato)
        if (quantita <= 0 || !isFasciaOrariaValida(fascia)) {
            System.err.println("Tentativo di conferma prenotazione non valida.");
            // Potresti lanciare un'eccezione qui
            // throw new IllegalArgumentException("Quantità o fascia oraria non valida per la conferma.");
            return;
        }

        LocalDate dataFine = data.plusDays(numeroGiorni);
        LocalDate dataCorrente = data;

        while (!dataCorrente.isAfter(dataFine)) {


            // Ottieni la mappa delle prenotazioni per la data specificata.
            // Se non esiste, creala e aggiungila alla mappa principale.
            // computeIfAbsent è thread-safe per quanto riguarda l'inserimento della mappa interna,
            // ma non per l'aggiornamento del contatore interno.
            Map<String, Integer> prenotazioniPerFasciaInData = prenotazioni.computeIfAbsent(dataCorrente, k -> new HashMap<>());

            // Aggiorna il conteggio per la fascia oraria specifica.
            // getOrDefault gestisce il caso in cui la fascia non abbia ancora prenotazioni.
            prenotazioniPerFasciaInData.put(fascia.toString(), prenotazioniPerFasciaInData.getOrDefault(fascia.toString(), 0) + quantita);

            System.out.printf("Prenotazione confermata per %d posti/oggetti in data %s, fascia %s. Totale prenotazioni ora: %d%n",
                    quantita, data, fascia, prenotazioniPerFasciaInData.get(fascia.toString()));

            dataCorrente = dataCorrente.plusDays(1);
        }

    }


    /**
     * Implementazione del metodo dell'interfaccia Prenotabile.
     * Verifica la disponibilità e, se positiva, conferma la prenotazione.
     *
     * @param prenotazione L'oggetto PrenotazioneServizioOrario contenente i dettagli.
     * @return true se la prenotazione è stata effettuata con successo, false altrimenti.
     */
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

        // 1. Verifica disponibilità
        boolean disponibile = verificaDisponibilita(data, numeroGiorni, fascia, quantita);

        // 2. Se disponibile, conferma la prenotazione
        if (disponibile) {
            confermaPrenotazione(data, numeroGiorni, fascia, quantita);
            // Qui potresti voler aggiungere la prenotazione a una lista generale
            // o eseguire altre operazioni post-prenotazione.
            return true;
        } else {
            // La verificaDisponibilita stampa già il motivo del fallimento
            return false;
        }
    }

    // Getter (opzionali, ma utili per debug o visualizzazione)
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

    // Metodo per ottenere la disponibilità residua (utile per l'utente)
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

}
