package com.sprng.library.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sprng.library.entity.Category;

import java.io.IOException;
import java.util.Iterator;

public class CategoryDeserialized extends StdDeserializer<Category> {
    public CategoryDeserialized(Class<?> vc) {
        super(vc);
    }

    public CategoryDeserialized() {
        this(null);
    }

    @Override
    public Category deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        Category category = new Category();
        Category parentCategory = new Category();
        JsonNode node = parser.getCodec().readTree(parser);
        category.setName(node.get("name").asText());
        category.setUrl(node.get("url").asText());

        Iterator<JsonNode> elements = node.get("parent").elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            parentCategory.setName(element.get("name").asText());
            parentCategory.setUrl(element.get("url").asText());
            category.setParent(parentCategory);
        }
        ;
        return category;
    };
}




//    private String name;
//    private String url;
//    private Category parent;

