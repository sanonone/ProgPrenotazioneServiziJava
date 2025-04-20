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
        // Aggiungiamo un controllo di validità: l'inizio non può essere dopo o uguale alla fine
        // (questo non gestisce intervalli che scavalcano la mezzanotte, vedi nota sotto)
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
     * La sovrapposizione avviene se start1 < end2 E start2 < end1.
     * L'estremo finale dell'intervallo è considerato escluso.
     *
     * @param other L'altro intervallo di tempo da confrontare.
     * @return true se gli intervalli si sovrappongono, false altrimenti.
     */
    public boolean overlaps(TimeInterval other) {
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


    /*
    Esempio uso

    // Creazione degli intervalli usando LocalTime.of o il parsing
        TimeInterval intervalloA = new TimeInterval(LocalTime.of(6, 0), LocalTime.of(10, 0)); // 06:00 - 10:00
        TimeInterval intervalloB = TimeInterval.parse("10:00 - 12:00"); // 10:00 - 12:00
        TimeInterval intervalloC = TimeInterval.parse("09:00 - 11:00"); // 09:00 - 11:00 (sovrapposto ad A e B)
        TimeInterval intervalloD = TimeInterval.parse("14:00 - 16:00"); // 14:00 - 16:00 (non sovrapposto)
        TimeInterval intervalloE = TimeInterval.parse("07:00 - 09:00"); // 07:00 - 09:00 (sovrapposto ad A)

        System.out.println("Intervallo A: " + intervalloA);
        System.out.println("Intervallo B: " + intervalloB);
        System.out.println("Intervallo C: " + intervalloC);
        System.out.println("Intervallo D: " + intervalloD);
        System.out.println("Intervallo E: " + intervalloE);
        System.out.println("--- Confronti ---");

        // Caso utente: A e B non si sovrappongono perché toccano solo l'estremo
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloA, intervalloB, intervalloA.overlaps(intervalloB)); // false

        // Caso sovrapposizione parziale: A e C
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloA, intervalloC, intervalloA.overlaps(intervalloC)); // true (dalle 09:00 alle 10:00)

        // Caso sovrapposizione parziale: B e C
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloB, intervalloC, intervalloB.overlaps(intervalloC)); // true (dalle 10:00 alle 11:00)

        // Caso nessuna sovrapposizione: A e D
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloA, intervalloD, intervalloA.overlaps(intervalloD)); // false

         // Caso E è contenuto in A
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloA, intervalloE, intervalloA.overlaps(intervalloE)); // true
        System.out.printf("%s si sovrappone a %s? %b\n", intervalloE, intervalloA, intervalloE.overlaps(intervalloA)); // true

     */
}
