package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page;

import com.fasterxml.jackson.databind.JsonNode;

import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentLocalization;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PageContentMapper extends EntityMapper<PageContent, PageContentDTO> {
    @Override
    @Named("mapContentToDto")
    @Mapping(target = "type", source = "pageElementType.name")
    @Mapping(target = "body", source = "localizations", qualifiedByName = "mapLocalizations")
    PageContentDTO toDto(PageContent entity);

    @Override
    PageContent toEntity(PageContentDTO domain);

    default List<PageContentDTO> map(List<PageContentOrder> orders) {
        return orders.stream().map(PageContentOrder::getPageContent).map(this::toDto).toList();
    }

    @Named("mapLocalizations")
    @MapMapping
    default Map<String, JsonNode> mapLocalizations(List<PageContentLocalization> localizations) {
        Map<String, JsonNode> map = new HashMap<>();

        localizations.forEach(localization -> map.put(localization.getLanguage().getName(), localization.getBody()));

        return map;
    }


}
