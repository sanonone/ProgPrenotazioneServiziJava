package com.example.spring.spring.mongoHelper;

import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrenotazioneOrariaRepository extends MongoRepository<PrenotazioneServizioOrario, String> {
}
