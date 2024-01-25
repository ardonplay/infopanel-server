package io.github.ardonplay.infopanel.server.models.contentElements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextElement {
    @JsonProperty("content")
    private String content;
}
