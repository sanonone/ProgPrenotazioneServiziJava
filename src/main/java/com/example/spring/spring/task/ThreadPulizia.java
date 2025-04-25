package com.example.spring.spring.task;

import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.mongoHelper.PrenotazioneGiornalieraRepository;
import com.example.spring.spring.mongoHelper.PrenotazioneOrariaRepository;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.example.spring.spring.mongoHelper.SerOrarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadPulizia implements Runnable {

    private final PrenotazioneGiornalieraRepository prenotazioneGiornalieraRepository;
    private final PrenotazioneOrariaRepository prenotazioneOrariaRepository;
    private final SerGiornalieroRepository serGiornalieroRepository;
    private final SerOrarioRepository serOrarioRepository;

    // Costruttore per iniettare manualmente le dipendenze
    public ThreadPulizia(PrenotazioneGiornalieraRepository prenotazioneGiornalieraRepository,
                         PrenotazioneOrariaRepository prenotazioneOrariaRepository,
                         SerGiornalieroRepository serGiornalieroRepository,
                         SerOrarioRepository serOrarioRepository) {
        this.prenotazioneGiornalieraRepository = prenotazioneGiornalieraRepository;
        this.prenotazioneOrariaRepository = prenotazioneOrariaRepository;
        this.serGiornalieroRepository = serGiornalieroRepository;
        this.serOrarioRepository = serOrarioRepository;
        // Inizializza gli altri repository/servizi
    }

    @Override
    public void run() {
        System.out.println("Esecuzione manuale del thread pulizia a " + LocalDateTime.now() + " nel thread: " + Thread.currentThread().getName());

        try {
            // --- Operazione 1: Cancellazione Prenotazioni Passate ---
            System.out.println("Avvio pulizia prenotazioni passate...");
            // Implementa la logica per trovare e cancellare prenotazioni vecchie.
            // Esempio concettuale (usando i repository iniettati):
            //List<?> prenotazioniPassate = prenotazioneGiornalieraRepository.findByDataFineBefore(LocalDateTime.now()); // Usa il repository
            List<PrenotazioneServizio> prenotazioniGpassate=prenotazioneGiornalieraRepository.findAll().stream().filter(
                    prenotazione ->{
                        // Questa è l'espressione lambda (il Predicate)
                        // Per ogni oggetto 'prenotazione' nello stream:
                        // - Controlla se getDataInizio() non è null (buona pratica)
                        // - Controlla se la data di inizio della prenotazione è prima della dataLimite
                        return prenotazione.getDataInizio() != null && prenotazione.getDataInizio().plusDays(prenotazione.getNumeroGiorni()).isBefore(LocalDate.now());
                    }).toList();
            prenotazioniGpassate.forEach(ele->{
                System.out.println("lista prenotazioni G passate: "+ele.getId()+" "+ele.getDataInizio().plusDays(ele.getNumeroGiorni()));
                //prenotazioneGiornalieraRepository.deleteById(ele.getId());
            });


            List<PrenotazioneServizioOrario> prenotazioniOpassate=prenotazioneOrariaRepository.findAll().stream().filter(
                    prenotazione ->{
                        // Questa è l'espressione lambda (il Predicate)
                        // Per ogni oggetto 'prenotazione' nello stream:
                        // - Controlla se getDataInizio() non è null (buona pratica)
                        // - Controlla se la data di inizio della prenotazione è prima della dataLimite
                        return prenotazione.getDataInizio() != null && prenotazione.getDataInizio().plusDays(prenotazione.getNumeroGiorni()).isBefore(LocalDate.now());
                    }).toList();
            prenotazioniOpassate.forEach(ele->{
                System.out.println("lista prenotazioni O passate: "+ele.getId()+" "+ele.getDataInizio().plusDays(ele.getNumeroGiorni()));
                //prenotazioneOrariaRepository.deleteById(ele.getId());
            });

            // prenotazioneRepository.deleteAll(prenotazioniPassate); // Usa il repository
            System.out.println("Fine pulizia prenotazioni passate."); // + prenotazioniPassate.size() + " cancelled.");

            // --- Operazione 2: Chiusura Disponibilità (se raggiunto il limite) ---
            System.out.println("Avvio controllo disponibilità servizi...");
            // Implementa la logica qui, usando i repository/servizi iniettati.
            // Esempio concettuale:
            // List<?> servizi = /* servizioRepository.findAll() */; // Usa il repository
            // for (Object servizio : servizi) { // Cast al tuo tipo Servizio
            //      if (/* tuoServizio.hasReachedCapacity() */) {
            //          // tuoServizio.setAvailable(false);
            //          // servizioRepository.save(tuoServizio);
            //      }
            // }
            System.out.println("Fine controllo disponibilità servizi.");

        } catch (Exception e) {
            // Gestione robusta degli errori nel thread manuale
            System.err.println("Errore nel thread ThreadPulizia: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("ThreadPulizia completato.\n\n");
    }


}
