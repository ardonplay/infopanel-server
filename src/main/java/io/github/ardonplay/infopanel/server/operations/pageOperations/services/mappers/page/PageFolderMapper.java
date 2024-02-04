package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PageMapper.class)
public interface PageFolderMapper extends EntityMapper<PageEntity, PageFolderDTO> {
    @Override
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Mapping(target = "children", source = "children", qualifiedByName = "mapWithoutContent")
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    PageFolderDTO toDto(PageEntity entity);

    @Override
    PageEntity toEntity(PageFolderDTO domain);
}
