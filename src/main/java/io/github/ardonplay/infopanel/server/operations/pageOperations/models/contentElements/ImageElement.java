package io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements;

import lombok.*;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageElement extends AbstractContentElement{

    private String id;
}
