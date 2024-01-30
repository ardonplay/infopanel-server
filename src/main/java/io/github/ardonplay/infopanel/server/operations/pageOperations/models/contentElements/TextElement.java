package io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextElement extends AbstractContentElement {
    @JsonProperty("content")
    private String content;
}