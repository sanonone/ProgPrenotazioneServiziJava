package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrenotazioneGiornalieraRepository  extends MongoRepository<PrenotazioneServizio, String> {

}
