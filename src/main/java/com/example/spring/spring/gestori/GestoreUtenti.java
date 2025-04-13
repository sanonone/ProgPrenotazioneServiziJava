package com.example.spring.spring.gestori;

import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.mongoHelper.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestoreUtenti {
    @Autowired
    private UtenteRepository utenteRepository;


    public void creaUtente(Utente utente) {
        utenteRepository.save(utente);
    }
    public void eliminaUtente(Utente utente) {
        utenteRepository.delete(utente);
    }
    public void modificaUtente(Utente utente) {
        utenteRepository.save(utente);
    }
    public void eliminaUtenteById(String id) {
        utenteRepository.deleteById(id);
    }
    public Utente getUtenteById(String id) {
        return utenteRepository.findById(id).orElse(null);
    }
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

}
