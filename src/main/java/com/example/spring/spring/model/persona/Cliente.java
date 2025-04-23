package com.example.spring.spring.model.persona;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "clienti")
public class Cliente extends Persona {
    @JsonProperty("email")
    private String email;

    public Cliente() {
        super();

    }

    public Cliente(String nome, String cognome, int eta, int telefono, String email) {
        super(nome, cognome, eta, telefono);
        this.email = email;
    }
    // Getters e Setters
    /*
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    */


    //prenotazione servizio
    //annulla prenotazione servizio
    //servizi prenotati
    //totale da pagare


    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", id='" + this.getId() + '\'' +
                ", nome='" + this.getNome() + '\'' + this.getCognome() + '\'' + '}';
    }
}
