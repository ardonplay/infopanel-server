package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.page.localized;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentLocalization;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalizedPageContentMapper extends EntityMapper<PageContent, LocalizedPageContentDTO> {

    @Override
    @Named("mapLocalizedContentToDto")
    @Mapping(target = "type", source = "pageElementType.name")
    @Mapping(target = "body", source = "localizations", qualifiedByName = "mapLocalizations")
    LocalizedPageContentDTO toDto(PageContent entity);

    @Override
    PageContent toEntity(LocalizedPageContentDTO domain);


    default List<LocalizedPageContentDTO> map(List<PageContentOrder> orders) {
        return orders.stream().map(PageContentOrder::getPageContent).map(this::toDto).toList();
    }

    @Named("mapLocalizations")
    default JsonNode mapLocalizations(List<PageContentLocalization> localizations) {
        System.out.println(localizations.get(0).getBody());
        return localizations.get(0).getBody();
    }


}
