package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
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
        @JsonSubTypes.Type(value = PageFolderDTO.class, name = "FOLDER"),
        @JsonSubTypes.Type(value = PageDTO.class, name = "PAGE")
})
public class PageDTO extends AbstractPageDTO{

    Map<String, String> title;

    List<PageContentDTO> content;


    public boolean hasNullImportantValues() {
        return title == null || type == null || orderId == null;
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
