package io.github.ardonplay.infopanel.server.models.contentElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
public abstract class AbstractContentElement {

    @JsonIgnore
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static AbstractContentElement fromJsonNode(JsonNode jsonNode, Class<? extends AbstractContentElement> contentElement) {
        return objectMapper.convertValue(jsonNode, contentElement);
    }

    public JsonNode toJsonNode() {
        return objectMapper.valueToTree(this);
    }
}

