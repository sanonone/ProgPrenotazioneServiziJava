package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.persona.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    // Metodi di query personalizzati possono essere definiti qui
    List<Cliente> findByNome(String nome);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByNomeAndCognome(String nome, String cognome);
    List<Cliente> findByNomeLikeIgnoreCase(String nome);

}