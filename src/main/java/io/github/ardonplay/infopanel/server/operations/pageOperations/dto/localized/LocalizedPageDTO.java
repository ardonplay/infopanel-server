package io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.AbstractPageDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocalizedPageFolderDTO.class, name = "FOLDER"),
        @JsonSubTypes.Type(value = LocalizedPageDTO.class, name = "PAGE")
})
public class LocalizedPageDTO extends AbstractPageDTO {

    private String title;

    private List<LocalizedPageContentDTO> content;

    public boolean hasNullImportantValues() {
        return title == null || type == null || orderId == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedPageDTO pageDTO = (LocalizedPageDTO) o;
        return Objects.equals(id, pageDTO.id) && Objects.equals(title, pageDTO.title) && Objects.equals(type, pageDTO.type) && Objects.equals(orderId, pageDTO.orderId) && Objects.equals(parentId, pageDTO.parentId) && Objects.equals(content, pageDTO.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, orderId, parentId, content);
    }
}