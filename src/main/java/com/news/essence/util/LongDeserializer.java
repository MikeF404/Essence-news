package com.news.essence.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LongDeserializer extends StdDeserializer<Long> {

    public LongDeserializer() {
        this(null);
    }

    public LongDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String value = p.getText();
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return UriConverter.convertStringToLong(value);
        }
    }
}
