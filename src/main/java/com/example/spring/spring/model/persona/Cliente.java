package com.example.spring.spring.model.persona;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "clienti")
public class Cliente extends Persona {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    public Cliente() {
        super();

    }

    public Cliente(String nome, String cognome, int eta, long telefono, String email, String password) {
        super(nome, cognome, eta, telefono);
        this.email = email;
        this.password = password;
    }
    // Getters e Setters

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


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
