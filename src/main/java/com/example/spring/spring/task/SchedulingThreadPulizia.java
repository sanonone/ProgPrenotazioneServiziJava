package com.example.spring.spring.task;

import com.example.spring.spring.mongoHelper.PrenotazioneGiornalieraRepository;
import com.example.spring.spring.mongoHelper.PrenotazioneOrariaRepository;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.example.spring.spring.mongoHelper.SerOrarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SchedulingThreadPulizia {

    @Autowired
    private PrenotazioneGiornalieraRepository prenotazioneGiornalieraRepository;
    @Autowired
    private PrenotazioneOrariaRepository prenotazioneOrariaRepository;
    @Autowired
    private SerGiornalieroRepository serGiornalieroRepository;
    @Autowired
    private SerOrarioRepository serOrarioRepository;

    /**
     * Task schedulato da Spring che avvia un nuovo thread manuale ogni minuto.
     */
    @Scheduled(fixedRate = 20000) // Esegui questo metodo ogni 20 sec
    public void avvioThreadPulizia(){
        System.out.println("\n\nAvvio ThreadPulizia a " + LocalDateTime.now());

        // 1. Crea un'istanzao Runnable, passando le dipendenze necessarie
        //CleanupTask task = new CleanupTask(prenotazioneRepository, servizioRepository /*, altri */);
        ThreadPulizia task = new ThreadPulizia(prenotazioneGiornalieraRepository, prenotazioneOrariaRepository, serGiornalieroRepository, serOrarioRepository);

        // 2. Crea e avvia un nuovo Thread con Runnable
        Thread puliziaThread = new Thread(task, "PuliziaThread-" + ThreadLocalRandom.current().nextInt(0, 100));
        puliziaThread.start(); // Avvia l'esecuzione del metodo run() del Runnable in un nuovo thread

        System.out.println("Nuovo threadPulizia avviato.");
    }
}
