package io.github.ardonplay.infopanel.server.operations.pageOperations.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.models.Pair;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageContentDTO;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.common.repositories.PageContentRepository;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.PageContentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PageContentServiceImpl implements PageContentService {
    private final PageContentRepository pageContentRepository;

    private final PageContentOrderServiceImpl pageContentOrderService;

    private final TypeCacheService cacheService;

    @Transactional
    public List<PageContent> getAllByContentPairs(List<Pair<PageElementType, Map<String, JsonNode>>> contentPairs) {
        return pageContentRepository
                .findPageContentsByPageElementTypeAndBody(
                        contentPairs.stream().map(pair -> pair.first().name()).toList());
//                        contentPairs.stream().map(Pair::second).toList());
    }

    @Transactional
    protected List<PageContent> updatePageContent(List<PageContent> pageContents, List<Pair<PageElementType, Map<String,JsonNode>>> contentPairs) {
        List<PageContent> result = new ArrayList<>(pageContents);

        for (Pair<PageElementType, Map<String,JsonNode>> pair : contentPairs) {
            if (!pageContentRepository.containsPair(result, pair)) {
//                PageContent pageContent = new PageContent(cacheService.getPageElementTypes().get(pair.first().name()), pair.second());
//                pageContent = pageContentRepository.save(pageContent);
//                result.add(pageContent);
            }
        }
        return result;
    }

    @Transactional
    public List<PageContent> updateByDTO(PageEntity page, List<PageContentDTO> pageContentDTOS){
        List<Pair<PageElementType, Map<String, JsonNode>>> contentPairs = pageContentDTOS.stream().map((content) -> new Pair<>(content.getType(), content.getBody())).toList();

        List<PageContent> pageContents = getAllByContentPairs(contentPairs);

        pageContents = updatePageContent(pageContents, contentPairs);

        List<PageContentOrder> newOrder = pageContentOrderService.updateOrder(page, contentPairs, pageContents);

        pageContentOrderService.deleteRowsThatNotInListForPage(page, newOrder);

        return newOrder.stream().map(PageContentOrder::getPageContent).toList();
    }
}
