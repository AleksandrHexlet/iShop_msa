package com.sprng.library.deserializer;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sprng.library.entity.Category;
import com.sprng.library.entity.Product;
import com.sprng.library.entity.ProductTrader;

import java.io.IOException;
import java.math.BigDecimal;

public class ProductDeserialized  extends StdDeserializer<Product> {

    protected ProductDeserialized(Class<?> vc) {
        super(vc);
    }
    protected ProductDeserialized() {
      this(null);
    }

    @Override
    public Product deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        Product product = new Product();
        ProductTrader productTrader = new ProductTrader();
        Category category = new Category();
        JsonNode node = parser.getCodec().readTree(parser);
        productTrader.setName(node.get("productTraderName").asText());
        productTrader.setCityStorage(node.get("productTraderCityStorage").asText());
        category.setId((Integer) node.get("categoryProductId").numberValue());

        product.setNameProduct(node.get("nameProduct").asText());
        product.setPrice(BigDecimal.valueOf((Double)node.get("price").numberValue()));
        product.setQuantityStock((Integer) node.get("quantityStock").numberValue());
        product.setProductManufacturer(productTrader);
        product.setCategoryProduct(category);

        return product;
    }
}