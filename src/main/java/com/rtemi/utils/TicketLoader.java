package com.rtemi.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TicketLoader <T> {
    public List<T> load(String path, Class<T> ticketType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);

        return objectMapper.readerForListOf(ticketType).readValue(Paths.get(path).toFile());
    }
}
