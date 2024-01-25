package io.github.ardonplay.infopanel.server.services.mappers;

import io.github.ardonplay.infopanel.server.models.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.models.entities.PageContent;
import io.github.ardonplay.infopanel.server.models.entities.PageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PageMapper {


    @Mapping(target = "type", source = "pageType.name")
    @Named("mapToPageDTOWithoutContent")
    PageDTO mapToPageDTOWithoutContent(PageEntity source);

    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Mapping(target = "children", source = "children", qualifiedByName = "mapToPageDTOWithoutContent")
    @Named("mapToFolder")
    PageFolderDTO mapToFolder(PageEntity source);

    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Named("mapToPageDTO")
    PageDTO mapToPageDTO(PageEntity source);


    @Mapping(target = "type", source = "pageElementType.name")
    @Named("mapContent")
    PageContentDTO mapContentToDTO(PageContent source);


    @Mapping(target = "pageType.name", source = "type")
    @Named("mapDTOtoPage")
    PageEntity mapDTOtoSheet(PageDTO dto);

    void updateTarget(@MappingTarget PageEntity target, PageDTO source);
}
