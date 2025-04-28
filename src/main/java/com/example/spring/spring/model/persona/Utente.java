package com.example.spring.spring.model.persona;

import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.spring.spring.gestori.GestoreServiziGiornalieri;
import com.example.spring.spring.model.servizio.Servizi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Document(collection = "utenti")
public class Utente extends Persona {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("ruolo")
    private String ruolo;
    @JsonProperty("dataAssunzione")
    private LocalDate dataAssunzione;

    SerGiornalieroRepository serGiornalieroRepository;

    public Utente() {//per json con conjackson
        super();
    }

    public Utente(String nome, String cognome, int eta, int telefono, String username, String password, String ruolo, String dataAssunzione) {
        super(nome, cognome, eta, telefono);
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataAssunzione = LocalDate.parse(dataAssunzione, formatter);

        } catch (DateTimeParseException e) {
            System.out.println("Errore nella conversione della data: " + e.getMessage());
        }


    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /*
    @Override
    public ServiziGiornalieri creaServizioGiornaliero(String nome, String descrizione, double prezzo, int disponibilita) {
        // ... implementazione ...
        ServiziGiornalieri servizio = new ServiziGiornalieri(nome, descrizione, prezzo, disponibilita);
        return servizio;
        //serGiornalieroRepository.save(servizio);

    }



    @Override
    public List<ServiziGiornalieri> visualizzaServiziGiornalieriCreati() {
        // ... implementazione ...
        List<ServiziGiornalieri> servizi=serGiornalieroRepository.findAll();
        return servizi;
    }

    @Override
    public void modificaServizioGiornaliero(ServiziGiornalieri servizio) {
        // ... implementazione ...
        ServiziGiornalieri servizioDaModificare = serGiornalieroRepository.findById(servizio.getId()).orElse(null);
        if(servizioDaModificare != null){
            serGiornalieroRepository.save(servizio);
        }
        else{
            System.out.println("Servizio da modificare non trovato");
        }
    }

    @Override
    public void eliminaServizioGiornaliero(String id) {
        // ... implementazione ...
        if(serGiornalieroRepository.existsById(id)) {
            serGiornalieroRepository.deleteById(id);
            System.out.println("Servizio eliminato");
        }
        else{
            System.out.println("Servizio non trovato");
        }
    }

    @Override
    public ServiziGiornalieri servizioGiornalieroById(String id) {
        // ... implementazione ...
        ServiziGiornalieri servizio = serGiornalieroRepository.findById(id).orElse(null);
        return servizio;
    }
*/

}
