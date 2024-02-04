package io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import lombok.*;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalizedPageContentDTO {
    PageElementType type;

    JsonNode body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedPageContentDTO that = (LocalizedPageContentDTO) o;
        return Objects.equals(type, that.type) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, body);
    }
}
