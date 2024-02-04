package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page;

import io.github.ardonplay.infopanel.server.common.entities.page.PageLocalization;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {PageContentMapper.class})
public interface PageMapper extends EntityMapper<PageEntity, PageDTO> {

    @Override
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "parentId", source = "parentPage.id")
    @Mapping(target = "content", source = "contentOrders")
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    PageDTO toDto(PageEntity entity);

    @Named("mapWithoutContent")
    @Mapping(target = "type", source = "pageType.name")
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "title", source = "localizations", qualifiedByName = "mapTitleLocalizations")
    PageDTO mapWithoutContent(PageEntity entity);

    @Override
    PageEntity toEntity(PageDTO domain);


    @Named("mapTitleLocalizations")
    @MapMapping
    default Map<String, String> mapTitleLocalizations(List<PageLocalization> localizations) {
        Map<String, String> map = new HashMap<>();

        for (PageLocalization localization : localizations) {
            map.put(localization.getLanguage().getName(), localization.getTitle());
        }
        return map;
    }
}
