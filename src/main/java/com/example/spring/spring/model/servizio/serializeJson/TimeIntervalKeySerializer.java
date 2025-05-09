package com.example.spring.spring.model.servizio.serializeJson;
import com.example.spring.spring.model.servizio.TimeInterval;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TimeIntervalKeySerializer extends JsonSerializer<TimeInterval> {//Classe fatta perchè jackson mi dava problemi a serializzare TimeInterval
    @Override
    public void serialize(TimeInterval value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName(value.toString());
    }
}