package io.github.ardonplay.infopanel.server.models.contentElements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TextElement extends AbstractContentElement{
    @JsonProperty("content")
    private String content;

    public TextElement(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
