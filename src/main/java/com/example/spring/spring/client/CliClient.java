package com.example.spring.spring.client;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.model.servizio.ServiziOrari;
import com.example.spring.spring.model.servizio.TimeInterval;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CliClient {

    public static void main(String[] args) {

        ChiamateCliente chiamateCliente = new ChiamateCliente();
        ChiamateUtente chiamateUtente = new ChiamateUtente();
        ChiamateServiziGiornalieri chiamateServiziGiornalieri = new ChiamateServiziGiornalieri();
        ChiamateServiziOrari chiamateServiziOrari = new ChiamateServiziOrari();
        ChiamatePrenotazioneGiornaliera chiamatePrenotazioneGiornaliera = new ChiamatePrenotazioneGiornaliera();
        ChiamatePrenotazioneOraria chiamatePrenotazioneOraria = new ChiamatePrenotazioneOraria();


        int scelta;

        System.out.println("Benvenuto");
        System.out.println("Scegli un'opzione:");
        System.out.println("1. Accedi come cliente");
        System.out.println("2. Accedi come utente");
        System.out.println("0. Esci");

        Scanner scanner = new Scanner(System.in);
        scelta = scanner.nextInt();

        while (scelta != 0) {
            switch (scelta) {
                case 1:
                    Cliente cliente = null;
                    Boolean ospite = true;
                    System.out.println("Hai scelto di accedere come cliente.");
                    System.out.println("Effettua il login, se non hai un account registrati o entra come ospite");
                    System.out.println("1. Accedi");
                    System.out.println("2. Registrati");
                    System.out.println("3. Entra come ospite");
                    int sceltaAccesso = scanner.nextInt();
                    scanner.nextLine();
                    if(sceltaAccesso == 1){//login
                        System.out.println("Inserisci la tua email:");
                        String mail = scanner.next();
                        System.out.println("Inserisci la tua password:");
                        String password = scanner.next();
                        Cliente cl = chiamateCliente.loginCliente(mail, password);
                        if (cl != null ) {
                            cliente=cl;
                            ospite=false;
                        }else{
                            ospite=true;
                            System.out.println("Email o password errati");
                            break;

                        }
                    }else if(sceltaAccesso == 2){//registrati
                        System.out.println("Inserisci il tuo nome:");
                        String nome = scanner.nextLine();
                        System.out.println("Inserisci il tuo cognome:");
                        String cognome = scanner.nextLine();
                        System.out.println("Inserisci la tua età:");
                        int eta = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Inserisci il tuo numero di telefono:");
                        long telefono = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Inserisci il tuo indirizzo email:");
                        String email = scanner.nextLine();
                        System.out.println("Inserisci la password:");
                        String password = scanner.nextLine();
                        Cliente cl = chiamateCliente.creaCliente(nome, cognome, eta, telefono, email,password);
                        cliente = cl;
                    }else if(sceltaAccesso == 3) {//ospite
                        ospite = true;
                    }
                    System.out.println("Scegli un'opzione:");
                    System.out.println("1. Prenota un servizio giornaliero");
                    System.out.println("2. Prenota un servizio orario");
                    System.out.println("999. Esci");
                    int sceltaCliente = scanner.nextInt();
                    scanner.nextLine();
                    while (sceltaCliente != 999) {
                        switch (sceltaCliente) {
                            case 1:
                                System.out.println("Hai scelto di prenotare un servizio giornaliero.");
                                List<ServiziGiornalieri> ser = chiamateServiziGiornalieri.getAllServiziGiornalieri();
                                AtomicInteger contatore= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                ser.forEach( servizio -> {
                                    System.out.println(contatore+")\nNome: " + servizio.getNome()+"\nDescrizione: "+servizio.getDescrizione()+"\nPrezzo: "+servizio.getPrezzo()+"\n");
                                    contatore.getAndIncrement();
                                });
                                System.err.println("\nScegli il servizio da prenotare, digita il numero corrispondente tra 0 e "+ (ser.size()-1));
                                System.err.println("999. Esci\n");
                                int sceltaServizio = scanner.nextInt();
                                scanner.nextLine();
                                if(sceltaServizio<0 || sceltaServizio>(ser.size()-1) && sceltaServizio != 999){
                                    System.out.println("Hai inserito un numero non valido");
                                    break;
                                }

                                while(sceltaServizio != 999){
                                    System.out.println("Hai scelto di prenotare il servizio numero " + sceltaServizio);
                                    if(sceltaAccesso == 3) {//ospite
                                        System.out.println("Inserisci il tuo nome:");
                                        String nome = scanner.nextLine();
                                        System.out.println("Inserisci il tuo cognome:");
                                        String cognome = scanner.nextLine();
                                        System.out.println("Inserisci la tua età:");
                                        System.out.println("Inserisci la data di prenotazione (formato: dd/MM/yyyy):");
                                        String data = scanner.nextLine();
                                        System.out.println("Inserisci il numero di giorni di prenotazione:");
                                        int giorni = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Inserisci la quantità da prenotare (da 1 a " + ser.get(sceltaServizio).getDisponibilita() + "):");
                                        int quantita = scanner.nextInt();
                                        scanner.nextLine();

                                        ServiziGiornalieri servizio = ser.get(sceltaServizio);
                                        PrenotazioneServizio prenotazione = new PrenotazioneServizio(servizio.getId(), "0000", servizio.getNome(), nome, cognome, data, giorni, quantita, servizio.getPrezzo() * quantita * giorni);

                                        chiamatePrenotazioneGiornaliera.creaPrenotazioneGiornaliera(prenotazione);
                                    }else{
                                        System.out.println("Inserisci la data di prenotazione (formato: dd/MM/yyyy):");
                                        String data = scanner.nextLine();
                                        System.out.println("Inserisci il numero di giorni di prenotazione:");
                                        int giorni = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Inserisci la quantità da prenotare (da 1 a " + ser.get(sceltaServizio).getDisponibilita() + "):");
                                        int quantita = scanner.nextInt();
                                        scanner.nextLine();

                                        ServiziGiornalieri servizio = ser.get(sceltaServizio);
                                        PrenotazioneServizio prenotazione = new PrenotazioneServizio(servizio.getId(), cliente.getId(), servizio.getNome(), cliente.getNome(), cliente.getCognome(), data, giorni, quantita, servizio.getPrezzo() * quantita * giorni);

                                        chiamatePrenotazioneGiornaliera.creaPrenotazioneGiornaliera(prenotazione);
                                    }

                                    System.err.println("\nScegli il servizio da prenotare, digita il numero corrispondente tra 0 e "+ (ser.size()-1));
                                    System.err.println("999. Esci\n");
                                    sceltaServizio = scanner.nextInt();
                                    scanner.nextLine();
                                }

                                break;
                            case 2:
                                System.out.println("Hai scelto di prenotare un servizio orario.");

                                List<ServiziOrari> serO = chiamateServiziOrari.getAllServiziOrari();
                                AtomicInteger contatoreO= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                serO.forEach( servizio -> {
                                    System.out.println(contatoreO+")\nNome: " + servizio.getNome()+"\nDescrizione: "+servizio.getDescrizione()+"\nPrezzo: "+servizio.getPrezzo()+"\nFasce Orarie: "+servizio.getFasceOrarie());
                                    contatoreO.getAndIncrement();
                                });
                                System.err.println("\nScegli il servizio da prenotare, digita il numero corrispondente tra 0 e "+ (serO.size()-1));
                                System.err.println("999. Esci\n");
                                int sceltaServizioO = scanner.nextInt();
                                scanner.nextLine();
                                if(sceltaServizioO<0 || sceltaServizioO>(serO.size()-1) && sceltaServizioO != 999){
                                    System.out.println("Hai inserito un numero non valido");
                                    break;
                                }

                                while(sceltaServizioO != 999){
                                    System.out.println("Hai scelto di prenotare il servizio numero " + sceltaServizioO);
                                    if(sceltaAccesso == 3) {//ospite
                                        System.out.println("Inserisci il tuo nome:");
                                        String nome = scanner.nextLine();
                                        System.out.println("Inserisci il tuo cognome:");
                                        String cognome = scanner.nextLine();
                                        System.out.println("Inserisci la data di prenotazione (formato: dd/MM/yyyy):");
                                        String data = scanner.nextLine();
                                        System.out.println("Inserisci il numero di giorni di prenotazione:");
                                        int giorni = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Inserisci la quantità da prenotare (da 1 a "+serO.get(sceltaServizioO).getDisponibilitaPerFascia()+"):");
                                        int quantita = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("\nInserisci il numero della fascia oraria (da 0 a "+serO.get(sceltaServizioO).getFasceOrarie().size()+")");
                                        int i;
                                        for(i=0; i<serO.get(sceltaServizioO).getFasceOrarie().size(); i++){
                                            System.out.println(i+") "+serO.get(sceltaServizioO).getFasceOrarie().get(i).getStart()+"-"+serO.get(sceltaServizioO).getFasceOrarie().get(i).getEnd());
                                        }
                                        int fasciaSelezionata = scanner.nextInt();
                                        //Cliente cliente = chiamateCliente.creaCliente(nome, cognome, eta, telefono, email,"pass");
                                        ServiziOrari servizio = serO.get(sceltaServizioO);
                                        TimeInterval fasciaOraria= new TimeInterval(servizio.getFasceOrarie().get(fasciaSelezionata).getStart(),servizio.getFasceOrarie().get(fasciaSelezionata).getEnd());
                                        PrenotazioneServizioOrario prenotazione = new PrenotazioneServizioOrario(servizio.getId(), "0000", servizio.getNome(), nome, cognome, data, giorni, quantita, servizio.getPrezzo()*quantita*giorni, fasciaOraria);

                                        chiamatePrenotazioneOraria.creaPrenotazioneOraria(prenotazione);


                                    }else{
                                        System.out.println("Inserisci la data di prenotazione (formato: dd/MM/yyyy):");
                                        String data = scanner.nextLine();
                                        System.out.println("Inserisci il numero di giorni di prenotazione:");
                                        int giorni = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Inserisci la quantità da prenotare (da 1 a "+serO.get(sceltaServizioO).getDisponibilitaPerFascia()+"):");
                                        int quantita = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("\nInserisci il numero della fascia oraria (da 0 a "+serO.get(sceltaServizioO).getFasceOrarie().size()+")");
                                        int i;
                                        for(i=0; i<serO.get(sceltaServizioO).getFasceOrarie().size(); i++){
                                            System.out.println(i+") "+serO.get(sceltaServizioO).getFasceOrarie().get(i).getStart()+"-"+serO.get(sceltaServizioO).getFasceOrarie().get(i).getEnd());
                                        }
                                        int fasciaSelezionata = scanner.nextInt();
                                        //Cliente cliente = chiamateCliente.creaCliente(nome, cognome, eta, telefono, email,"pass");
                                        ServiziOrari servizio = serO.get(sceltaServizioO);
                                        TimeInterval fasciaOraria= new TimeInterval(servizio.getFasceOrarie().get(fasciaSelezionata).getStart(),servizio.getFasceOrarie().get(fasciaSelezionata).getEnd());
                                        PrenotazioneServizioOrario prenotazione = new PrenotazioneServizioOrario(servizio.getId(), cliente.getId(), servizio.getNome(), cliente.getNome(), cliente.getCognome(), data, giorni, quantita, servizio.getPrezzo()*quantita*giorni, fasciaOraria);

                                        chiamatePrenotazioneOraria.creaPrenotazioneOraria(prenotazione);
                                    }

                                    System.err.println("\nScegli il servizio da prenotare, digita il numero corrispondente tra 0 e "+ (serO.size()-1));
                                    System.err.println("999. Esci\n");
                                    sceltaServizioO = scanner.nextInt();
                                    scanner.nextLine();

                                }
                                break;
                        }
                        System.out.println("Scegli un'opzione:");
                        System.out.println("1. Prenota un servizio giornaliero");
                        System.out.println("2. Prenota un servizio orario");
                        System.out.println("999. Esci");
                        sceltaCliente = scanner.nextInt();
                        scanner.nextLine();
                    }
                    if (sceltaCliente == 999) {
                        break;
                    }
                case 2:
                    System.out.println("Hai scelto di accedere come utente.");
                    System.out.println("Inserisci il tuo username:");
                    String username = scanner.next();
                    System.out.println("Inserisci la tua password:");
                    String password = scanner.next();
                    Utente ut = chiamateUtente.loginUtente(username, password);
                    if (ut != null ) {
                        System.out.println("Login effettuato con successo!");

                        System.out.println("Benvenuto " + ut.getUsername());
                        int sceltaUtente;
                        System.out.println("Scegli un'opzione:");
                        System.out.println("1. Visualizza i servizi giornalieri presenti");//aggiungere delete
                        System.out.println("2. Visualizza i servizi orari presenti");//aggiungere delete
                        System.out.println("3. Crea un nuovo servizio giornaliero");
                        System.out.println("4. Crea un nuovo servizio orario");
                        System.out.println("5. Visualizza tutte le prenotazioni per servizi giornalieri");
                        System.out.println("6. Visualizza tutte le prenotazioni per servizi orari");
                        System.out.println("0. Esci\n");
                        sceltaUtente = scanner.nextInt();

                        while (sceltaUtente != 0) {
                            switch (sceltaUtente) {
                                case 1:
                                    System.out.println("Hai scelto di visualizzare i servizi giornalieri presenti.\n");
                                    List<ServiziGiornalieri> ser = chiamateServiziGiornalieri.getAllServiziGiornalieri();
                                    AtomicInteger contatore= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                    ser.forEach( servizio -> {
                                        System.out.println(contatore+")\nNome: " + servizio.getNome()+"\nDescrizione: "+servizio.getDescrizione());
                                        contatore.getAndIncrement();
                                    });
                                    System.err.println("\nPer visualizzare i dettagli di un servizio e/o gestirne l'eliminazione digita il numero corrispondente tra 0 e "+ (ser.size()-1));
                                    System.err.println("999. Esci\n");
                                    int sceltaServizio = scanner.nextInt();
                                    if(sceltaServizio<0 || sceltaServizio>(ser.size()-1) && sceltaServizio != 999){
                                        System.out.println("Hai inserito un numero non valido");
                                        break;
                                    }

                                    while(sceltaServizio != 999){
                                        System.out.println("Hai scelto di visualizzare i dettagli del servizio numero " + sceltaServizio);

                                        System.out.println(ser.get(sceltaServizio).getNome()+"\nDescrizione: "+ ser.get(sceltaServizio).getDescrizione()+"\nPrezzo: "+ser.get(sceltaServizio).getPrezzo()+"€\nDisponibilità: " + ser.get(sceltaServizio).getDisponibilita()+"\nPrenotazioni: "+ser.get(sceltaServizio).getPrenotazioniPerData()+"\n");
                                        System.out.println("Desideri eliminare il servizio?");
                                        System.out.println("y = Si");
                                        System.out.println("n || invio = No");
                                        char risposta = scanner.next().charAt(0);
                                        if (risposta == 'y') {
                                            chiamateServiziGiornalieri.deleteServizioGiornaliero(ser.get(sceltaServizio).getId());
                                            ser.remove(sceltaServizio);
                                            System.out.println("Servizio eliminato con successo!");
                                        }else if(risposta == 'n'){
                                            System.out.println("Hai scelto di non eliminare il servizio");
                                        }else{
                                            System.out.println("Hai inserito una risposta non valida\n");
                                        }



                                        System.err.println("Inserisci il numero di un altro servizio tra 0 e "+(ser.size()-1)+" per vedere i dettagli");
                                        System.err.println("999. Esci\n");
                                        sceltaServizio = scanner.nextInt();

                                        //dopo l'eliminazione continuava ad accettare l'indice del servizio eliminato cusando crash
                                        if(sceltaServizio<0 || sceltaServizio>(ser.size()-1) && sceltaServizio != 999){
                                            System.out.println("Hai inserito un numero non valido");
                                            break;
                                        }

                                    }





                                    break;
                                case 2:
                                    System.out.println("Hai scelto di visualizzare i servizi orari presenti.\n");
                                    List<ServiziOrari> serO = chiamateServiziOrari.getAllServiziOrari();
                                    AtomicInteger contatoreO= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                    serO.forEach( servizio -> {
                                        System.out.println(contatoreO+")\nNome: " + servizio.getNome()+"\nDescrizione: "+servizio.getDescrizione());
                                        contatoreO.getAndIncrement();
                                    });
                                    System.err.println("\nPer visualizzare i dettagli di un servizio digita il numero corrispondente tra 0 e "+ (serO.size()-1));
                                    System.err.println("999. Esci\n");
                                    int sceltaServizioO = scanner.nextInt();
                                    if(sceltaServizioO<0 || sceltaServizioO>(serO.size()-1) && sceltaServizioO != 999){
                                        System.out.println("Hai inserito un numero non valido");
                                        break;
                                    }

                                    while(sceltaServizioO != 999){
                                        System.out.println("Hai scelto di visualizzare i dettagli del servizio numero " + sceltaServizioO);

                                        System.out.println(serO.get(sceltaServizioO).getNome()+"\nDescrizione: "+ serO.get(sceltaServizioO).getDescrizione()+"\nPrezzo: "+serO.get(sceltaServizioO).getPrezzo()+"€\nDisponibilità per fascia: " + serO.get(sceltaServizioO).getDisponibilitaPerFascia()+"\nFasce orario: " + serO.get(sceltaServizioO).getFasceOrarie() +"\nPrenotazioni: "+serO.get(sceltaServizioO).getPrenotazioni()+"\n");

                                        System.out.println("Desideri eliminare il servizio?");
                                        System.out.println("y = Si");
                                        System.out.println("n || invio = No");
                                        char risposta = scanner.next().charAt(0);
                                        if (risposta == 'y') {
                                            chiamateServiziOrari.deleteServizioOrario(serO.get(sceltaServizioO).getId());
                                            serO.remove(sceltaServizioO);
                                            System.out.println("Servizio eliminato con successo!");
                                        }else if(risposta == 'n'){
                                            System.out.println("Hai scelto di non eliminare il servizio");
                                        }else{
                                            System.out.println("Hai inserito una risposta non valida\n");
                                        }

                                        System.err.println("Inserisci il numero di un altro servizio tra 0 e "+(serO.size()-1)+" per vedere i dettagli");
                                        System.err.println("999. Esci\n");
                                        sceltaServizioO = scanner.nextInt();

                                        //dopo l'eliminazione continuava ad accettare l'indice del servizio eliminato cusando crash
                                        if(sceltaServizioO<0 || sceltaServizioO>(serO.size()-1) && sceltaServizioO != 999){
                                            System.out.println("Hai inserito un numero non valido");
                                            break;
                                        }

                                    }





                                    break;
                                case 3:
                                    scanner.nextLine();
                                    System.out.println("Hai scelto di creare un nuovo servizio giornaliero.");
                                    System.out.println("Inserisci il nome del servizio:");
                                    String nomeServizio = scanner.nextLine();
                                    System.out.println("Inserisci la descrizione del servizio:");
                                    String descrizione = scanner.nextLine();
                                    System.out.println("Inserisci il numero di posti disponibili:");
                                    int postiDisponibili = scanner.nextInt();
                                    System.out.println("Inserisci il prezzo giornaliero del servizio");
                                    double prezzo = scanner.nextDouble();
                                    chiamateServiziGiornalieri.creaServizioGiornaliero(nomeServizio,descrizione,prezzo,postiDisponibili);

                                    break;
                                case 4:
                                    scanner.nextLine();
                                    System.out.println("Hai scelto di creare un nuovo servizio orario.");
                                    System.out.println("Inserisci il nome del servizio:");
                                    String nomeServizioOrario = scanner.nextLine();
                                    System.out.println("Inserisci la descrizione del servizio:");
                                    String descrizioneServizioOrario = scanner.nextLine();
                                    System.out.println("Quante fasce orarie vuoi aggiungere per questo servizio? Inserisci 0 per inserire tre fasce orario di default, altrimenti inserisci il numero di fasce da inserire:");
                                    int numFasce=scanner.nextInt();
                                    scanner.nextLine();
                                    List<TimeInterval> fasceOrarie = new ArrayList<>();
                                    if(numFasce==0){
                                        fasceOrarie=null;
                                    }else{
                                        for(int i=0;i<numFasce;i++){
                                            System.out.println("Inserisci la fascia oraria " + (i+1) + ":");
                                            System.out.println("Inserisci l'ora di inizio (in formato HH:mm):");
                                            String oraInizio = scanner.nextLine();
                                            System.out.println("Inserisci l'ora di fine (in formato HH:mm):");
                                            String oraFine = scanner.nextLine();
                                            int hhInizio = Integer.parseInt(oraInizio.split(":")[0]);
                                            int mmInizio = Integer.parseInt(oraInizio.split(":")[1]);
                                            int hhFine = Integer.parseInt(oraFine.split(":")[0]);
                                            int mmFine = Integer.parseInt(oraFine.split(":")[1]);
                                            TimeInterval fasciaOraria = new TimeInterval(LocalTime.of(hhInizio, mmInizio), LocalTime.of(hhFine, mmFine));
                                            fasceOrarie.add(fasciaOraria);
                                        }
                                    }
                                    System.out.println("Inserisci il numero di posti disponibili per ogni fascia oraria:");
                                    int postiDisponibiliServizioOrario = scanner.nextInt();
                                    System.out.println("Inserisci il prezzo per fascia oraria del servizio");
                                    double prezzoServizioOrario = scanner.nextDouble();
                                    chiamateServiziOrari.creaServizioOrario(nomeServizioOrario,descrizioneServizioOrario,prezzoServizioOrario,fasceOrarie,postiDisponibiliServizioOrario);


                                    break;

                                case 5:
                                    System.out.println("Hai scelto di visualizzare le prenotazioni per i servizi giornalieri.\n");
                                    List<PrenotazioneServizio> prG = chiamatePrenotazioneGiornaliera.getAllPrenotazioniServiziGiornalieri();
                                    AtomicInteger contatorePrG= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                    prG.forEach( pr -> {
                                        System.out.println(contatorePrG+")\nCliente: " + pr.getNomeCliente()+" "+ pr.getCognomeCliente()+"\nServizio: "+pr.getNomeServizio()+"\nData: "+pr.getDataInizio());
                                        contatorePrG.getAndIncrement();
                                    });
                                    System.err.println("\nPer visualizzare i dettagli della prenotazione digita il numero corrispondente tra 0 e "+ (prG.size()-1));
                                    System.err.println("999. Esci\n");
                                    int sceltaPrenotazioneGiornaliera = scanner.nextInt();
                                    if(sceltaPrenotazioneGiornaliera<0 || sceltaPrenotazioneGiornaliera>(prG.size()-1) && sceltaPrenotazioneGiornaliera != 999){
                                        System.out.println("Hai inserito un numero non valido");
                                        break;
                                    }

                                    while(sceltaPrenotazioneGiornaliera != 999){
                                        System.out.println("Hai scelto di visualizzare i dettagli della prenotazione numero " + sceltaPrenotazioneGiornaliera);

                                        System.out.println(prG.get(sceltaPrenotazioneGiornaliera).toString());

                                        System.err.println("Inserisci il numero di un' altra prenotazione per vedere i dettagli");
                                        System.err.println("999. Esci\n");
                                        sceltaPrenotazioneGiornaliera = scanner.nextInt();

                                    }


                                    break;

                                case 6:
                                    System.out.println("Hai scelto di visualizzare le prenotazioni per i servizi orari.\n");
                                    List<PrenotazioneServizioOrario> prO = chiamatePrenotazioneOraria.getAllPrenotazioniServiziOrari();
                                    AtomicInteger contatorePrO= new AtomicInteger();//fatto dall'ide perchè altrimenti non permetteva l'incremento nel forEach
                                    prO.forEach( pr -> {
                                        System.out.println(contatorePrO+")\nCliente: " + pr.getNomeCliente()+" "+ pr.getCognomeCliente()+"\nServizio: "+pr.getNomeServizio()+"\nData: "+pr.getDataInizio());
                                        contatorePrO.getAndIncrement();
                                    });
                                    System.err.println("\nPer visualizzare i dettagli della prenotazione digita il numero corrispondente tra 0 e "+ (prO.size()-1));
                                    System.err.println("999. Esci\n");
                                    int sceltaPrenotazioneOrarie = scanner.nextInt();
                                    if(sceltaPrenotazioneOrarie<0 || sceltaPrenotazioneOrarie>(prO.size()-1) && sceltaPrenotazioneOrarie != 999){
                                        System.out.println("Hai inserito un numero non valido");
                                        break;
                                    }

                                    while(sceltaPrenotazioneOrarie != 999){
                                        System.out.println("Hai scelto di visualizzare i dettagli della prenotazione numero " + sceltaPrenotazioneOrarie);

                                        System.out.println(prO.get(sceltaPrenotazioneOrarie).toString());

                                        System.err.println("Inserisci il numero di un' altra prenotazione per vedere i dettagli");
                                        System.err.println("999. Esci\n");
                                        sceltaPrenotazioneOrarie = scanner.nextInt();

                                    }

                                    break;


                            }

                            System.out.println("\nScegli un'opzione:");
                            System.out.println("1. Visualizza i servizi giornalieri presenti");
                            System.out.println("2. Visualizza i servizi orari presenti");
                            System.out.println("3. Crea un nuovo servizio giornaliero");
                            System.out.println("4. Crea un nuovo servizio orario");
                            System.out.println("5. Visualizza tutte le prenotazioni per servizi giornalieri");
                            System.out.println("6. Visualizza tutte le prenotazioni per servizi orari");
                            System.out.println("0. Esci");
                            sceltaUtente = scanner.nextInt();
                        }
                        ut=null;

                        }else{
                            System.out.println("Username o password errati!");
                        }

                        break;
                        default:
                            System.out.println("Scelta non valida. Riprova.");
                            break;
                    }

            System.out.println("Scegli un'opzione:");
            System.out.println("1. Accedi come cliente");
            System.out.println("2. Accedi come utente");
            System.out.println("0. Esci");

            scelta = scanner.nextInt();

            }

        }


    public static int safeNextInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Input non valido. Inserisci un numero intero: ");
            }
        }
    }
    }
