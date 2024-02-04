package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page.localized;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.page.PageLocalization;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocalizedPageContentMapper.class})
public interface LocalizedPageMapper extends EntityMapper<PageEntity, LocalizedPageDTO> {
    @Override
    @Named("mapWithContent")
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Mapping(target = "content", source = "contentOrders")
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    LocalizedPageDTO toDto(PageEntity entity);

    @Override
    PageEntity toEntity(LocalizedPageDTO domain);

    @Named("mapWithoutContent")
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    LocalizedPageDTO mapWithoutContent(PageEntity entity);


    @Named("mapTitleLocalizations")
    @MapMapping
    default String mapTitleLocalizations(List<PageLocalization> localizations) {
        return localizations.get(0).getTitle();
    }
}
