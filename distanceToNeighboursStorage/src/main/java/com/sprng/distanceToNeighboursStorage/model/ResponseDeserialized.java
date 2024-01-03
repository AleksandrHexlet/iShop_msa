package com.sprng.distanceToNeighboursStorage.model;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;


public class ResponseDeserialized extends StdDeserializer<DistanceApiResponse> {
    public ResponseDeserialized(Class<?> vc) {
        super(vc);
    }

    public ResponseDeserialized() {
        this(null);
    }

    @Override
    public DistanceApiResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        DistanceApiResponse distanceApiResponse = new DistanceApiResponse();
        JsonNode node = parser.getCodec().readTree(parser);

        Iterator<JsonNode> elements = node.get("rows").elements();
        while (elements.hasNext()) {
            JsonNode rowsElements = elements.next();
            Iterator<JsonNode> element = rowsElements.get("elements").elements();
            while (element.hasNext()) {
                JsonNode oneElement = element.next();
                distanceApiResponse.setCityDelivery(oneElement.get("origin").asText());
                distanceApiResponse.setCityStorage(oneElement.get("destination").asText());
                distanceApiResponse.setDistance(oneElement.get("distance").asInt());
            }
        }
        return distanceApiResponse;
    }
}

