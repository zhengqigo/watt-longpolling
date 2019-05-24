package cn.fuelteam.watt.longpolling.common;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PayloadUtil {

    private List<Payload> deserialiseList(final String sourceJson) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(sourceJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Payload.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Payload deserialise(final String sourceJson) {
        try {
            return new ObjectMapper().readValue(sourceJson, Payload.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String serialisePayload(final List<Payload> payload) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getMessagePayload() {
        try {
            final List<Payload> payload = new ArrayList<Payload>();
            payload.add(deserialise(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new Payload())));
            return serialisePayload(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String appendPayload(final String sourceJson, final String appendJson) {
        final List<Payload> sourcePayloadList = deserialiseList(sourceJson);
        if (appendJson != null) {
            final List<Payload> appendPayloadList = deserialiseList(appendJson);
            return serialisePayload(Stream.concat(sourcePayloadList.stream(), appendPayloadList.stream()).collect(toList()));
        }
        return sourceJson;
    }
}