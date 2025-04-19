package com.example.spring.spring.model.servizio.serializeJson;

import com.example.spring.spring.model.servizio.TimeInterval;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeIntervalKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        String[] parts = key.split("-");
        if (parts.length != 2) {
            throw new IOException("Invalid TimeInterval key format: " + key);
        }

        LocalTime start = LocalTime.parse(parts[0], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("HH:mm"));

        return new TimeInterval(start, end);
    }
}