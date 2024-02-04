package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page.localized;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = LocalizedPageMapper.class)
public interface LocalizedPageFolderMapper extends EntityMapper<PageEntity, LocalizedPageFolderDTO> {
    @Override
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Mapping(target = "children", source = "children", qualifiedByName = "mapWithoutContent")
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    LocalizedPageFolderDTO toDto(PageEntity entity);



    @Override
    PageEntity toEntity(LocalizedPageFolderDTO domain);

}
