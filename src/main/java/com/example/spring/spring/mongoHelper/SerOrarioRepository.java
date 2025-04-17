package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.servizio.ServiziOrari;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SerOrarioRepository extends MongoRepository<ServiziOrari, String> {
}
