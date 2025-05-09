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

import static java.lang.Thread.sleep;

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
    }

    @Override
    public void run() {
        System.out.println("Esecuzione manuale del thread pulizia a " + LocalDateTime.now() + " nel thread: " + Thread.currentThread().getName());

        try {
            // Cancellazione Prenotazioni Passate
            System.out.println("Avvio pulizia prenotazioni passate...");

            List<PrenotazioneServizio> prenotazioniGpassate=prenotazioneGiornalieraRepository.findAll().stream().filter(//recupero tutte le prenotazioni antecedenti la data corrente
                    prenotazione ->{
                        // - Controlla se getDataInizio() non è null (buona pratica)
                        // - Controlla se la data di inizio della prenotazione è prima della dataLimite
                        return prenotazione.getDataInizio() != null && prenotazione.getDataInizio().plusDays(prenotazione.getNumeroGiorni()).isBefore(LocalDate.now());
                    }).toList();
            System.out.println("\nLista prenotazioni giornaliere da eliminare ("+prenotazioniGpassate.size()+"): ");
            prenotazioniGpassate.forEach(ele->{
                System.out.println("Id: "+ele.getId()+" Data: "+ele.getDataInizio().plusDays(ele.getNumeroGiorni()));
                //prenotazioneGiornalieraRepository.deleteById(ele.getId());
            });

            sleep(25000);//simulaziione attesa per far partire 2 thread accavallati

            List<PrenotazioneServizioOrario> prenotazioniOpassate=prenotazioneOrariaRepository.findAll().stream().filter(
                    prenotazione ->{
                        // - Controlla se getDataInizio() non è null (buona pratica)
                        // - Controlla se la data di inizio della prenotazione è prima della dataLimite
                        return prenotazione.getDataInizio() != null && prenotazione.getDataInizio().plusDays(prenotazione.getNumeroGiorni()).isBefore(LocalDate.now());
                    }).toList();
            System.out.println("\nLista prenotazioni orarie da eliminare ("+prenotazioniOpassate.size()+"): ");
            prenotazioniOpassate.forEach(ele->{
                System.out.println("Id: "+ele.getId()+" Data"+ele.getDataInizio().plusDays(ele.getNumeroGiorni()));
                //prenotazioneOrariaRepository.deleteById(ele.getId());
            });

            System.out.println("Fine pulizia prenotazioni passate.");



        } catch (Exception e) {
            System.err.println("Errore nel thread ThreadPulizia: " + e.getMessage());
        }

        System.out.println("ThreadPulizia completato."+ Thread.currentThread().getName()+"\n");
    }


}
