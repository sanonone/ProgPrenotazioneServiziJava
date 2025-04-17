package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SerGiornalieroRepository  extends MongoRepository<ServiziGiornalieri, String> {
}
