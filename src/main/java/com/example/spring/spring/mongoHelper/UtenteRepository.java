package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.persona.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends MongoRepository<Utente, String> {
    // Metodi di query personalizzati possono essere definiti qui
    List<Utente> findByNome(String nome);
    Optional<Utente> findByEmail(String email);
    List<Utente> findByNomeAndCognome(String nome, String cognome);
    List<Utente> findByNomeLikeIgnoreCase(String nome);
}
