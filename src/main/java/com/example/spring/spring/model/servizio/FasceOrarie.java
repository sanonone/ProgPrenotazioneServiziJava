package com.example.spring.spring.model.servizio;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FasceOrarie {
    protected String inizioString;
    protected String fineString;
    LocalTime inizio;
    LocalTime fine;

    public FasceOrarie(String inizioString, String fineString) {
        this.inizioString = inizioString;
        this.fineString = fineString;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.inizio = LocalTime.parse(inizioString, timeFormatter);
        this.fine = LocalTime.parse(fineString, timeFormatter);
        //this.inizio = LocalTime.parse(inizioString);
        //this.fine = LocalTime.parse(fineString);
    }
    public String getInizioString() {
        return inizioString;
    }
    public void setInizioString(String inizioString) {
        this.inizioString = inizioString;
    }
    public String getFineString() {
        return fineString;
    }
    public void setFineString(String fineString) {
        this.fineString = fineString;
    }
    public LocalTime getInizio() {
        return inizio;
    }
    public void setInizio(String inizioString) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.inizio = LocalTime.parse(inizioString, timeFormatter);

    }
    public LocalTime getFine() {
        return fine;
    }
    public void setFine(String fineString) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.fine = LocalTime.parse(fineString, timeFormatter);
    }

}
