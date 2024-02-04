package io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized;

import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.AbstractPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
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
public class LocalizedPageFolderDTO extends AbstractPageDTO {
    private String title;

    private List<LocalizedPageDTO> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LocalizedPageFolderDTO that = (LocalizedPageFolderDTO) o;
        return Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), children);
    }
}