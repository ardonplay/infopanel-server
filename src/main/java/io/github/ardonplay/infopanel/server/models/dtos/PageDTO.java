package io.github.ardonplay.infopanel.server.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
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
}
