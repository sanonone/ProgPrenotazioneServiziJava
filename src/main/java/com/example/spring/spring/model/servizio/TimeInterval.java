package com.example.spring.spring.model.servizio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Classe di supporto per rappresentare un intervallo di tempo [start, end)
public class TimeInterval {
    @JsonProperty("start")
    private LocalTime start; // Inclusivo
    @JsonProperty("end")
    private LocalTime end;   // Esclusivo

    // Formatter per parsing e output
    private static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TimeInterval() {
    }

    public TimeInterval(LocalTime start, LocalTime end) {
        //controllo di validità: l'inizio non può essere dopo o uguale alla fine
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("L'orario di inizio deve essere precedente all'orario di fine.");
        }
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }


    /**
     * Verifica se questo intervallo di tempo si sovrappone con un altro intervallo.
     * La sovrapposizione avviene se start1 < end2 E start2 < end1
     */
    public boolean overlaps(TimeInterval other) {//metodo implementato all'inizio ma poi non usato
        if (other == null) {
            return false;
        }
        // start1.isBefore(end2) && start2.isBefore(end1)
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public String toString() {
        return String.format("[%s - %s)",
                start.format(HH_MM_FORMATTER),
                end.format(HH_MM_FORMATTER));
    }

    // Metodi equals e hashCode (buona pratica se usi la classe in collezioni)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    /*
    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    // Metodo di utilità per creare un intervallo da una stringa "HH:mm - HH:mm"
    public static TimeInterval parse(String intervalString) {
        Objects.requireNonNull(intervalString, "La stringa dell'intervallo non può essere null");
        String[] parts = intervalString.split("\\s*-\\s*"); // Divide su " - "
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato stringa non valido. Usare 'HH:mm - HH:mm'. Ricevuto: " + intervalString);
        }
        LocalTime startTime = LocalTime.parse(parts[0].trim(), HH_MM_FORMATTER);
        LocalTime endTime = LocalTime.parse(parts[1].trim(), HH_MM_FORMATTER);
        return new TimeInterval(startTime, endTime);
    }

     */


}
