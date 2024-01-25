package io.github.ardonplay.infopanel.server.models.contentElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class AbstractContentElement {

    @JsonIgnore
    private final ObjectMapper objectMapper;

    public AbstractContentElement fromJsonNode(JsonNode jsonNode) {
        return objectMapper.convertValue(jsonNode, this.getClass());
    }

    public JsonNode toJsonNode() {
        return objectMapper.valueToTree(this);
    }
}

