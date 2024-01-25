package io.github.ardonplay.infopanel.server.models.dtos;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageFolderDTO extends PageDTO {

    List<PageDTO> children;
}
