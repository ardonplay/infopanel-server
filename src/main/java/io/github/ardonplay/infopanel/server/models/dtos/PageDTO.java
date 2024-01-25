package io.github.ardonplay.infopanel.server.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    Integer id;

    String title;

    String type;

    Integer orderId;

    Integer parentId;

    List<PageContentDTO> content;

    public boolean hasNullImportantValues() {
        return title == null || type == null || orderId == null || content == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDTO pageDTO = (PageDTO) o;
        return Objects.equals(id, pageDTO.id) && Objects.equals(title, pageDTO.title) && Objects.equals(type, pageDTO.type) && Objects.equals(orderId, pageDTO.orderId) && Objects.equals(parentId, pageDTO.parentId) && Objects.equals(content, pageDTO.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, orderId, parentId, content);
    }
}
