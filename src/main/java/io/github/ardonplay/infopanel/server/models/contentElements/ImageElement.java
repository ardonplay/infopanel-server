package io.github.ardonplay.infopanel.server.models.contentElements;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ImageElement extends AbstractContentElement{

    private String id;
    public ImageElement(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
