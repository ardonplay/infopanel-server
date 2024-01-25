package io.github.ardonplay.infopanel.server.models.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageContentDTO {
    String type;
    JsonNode body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageContentDTO that = (PageContentDTO) o;
        return Objects.equals(type, that.type) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, body);
    }
}
