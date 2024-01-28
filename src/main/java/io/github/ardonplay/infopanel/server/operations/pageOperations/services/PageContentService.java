package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.models.Pair;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.common.entities.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.PageContentOrder;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.common.repositories.PageContentRepository;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PageContentService {
    private final PageContentRepository pageContentRepository;

    private final PageContentOrderService pageContentOrderService;

    private final TypeCacheService cacheService;

    @Transactional
    public List<PageContent> getAllByContentPairs(List<Pair<PageElementType, JsonNode>> contentPairs) {
        return pageContentRepository
                .findPageContentsByPageElementTypeAndBody(
                        contentPairs.stream().map(pair -> pair.first().name()).toList(),
                        contentPairs.stream().map(Pair::second).toList());
    }

    @Transactional
    protected List<PageContent> updatePageContent(List<PageContent> pageContents, List<Pair<PageElementType, JsonNode>> contentPairs) {
        List<PageContent> result = new ArrayList<>(pageContents);

        for (Pair<PageElementType, JsonNode> pair : contentPairs) {
            if (!pageContentRepository.containsPair(result, pair)) {
                PageContent pageContent = new PageContent(cacheService.getPageElementTypes().get(pair.first().name()), pair.second());
                pageContent = pageContentRepository.save(pageContent);
                result.add(pageContent);
            }
        }
        return result;
    }

    @Transactional
    public List<PageContent> updateByDTO(PageEntity page, List<PageContentDTO> pageContentDTOS){
        List<Pair<PageElementType, JsonNode>> contentPairs = pageContentDTOS.stream().map((content) -> new Pair<>(content.getType(), content.getBody())).toList();

        List<PageContent> pageContents = getAllByContentPairs(contentPairs);

        pageContents = updatePageContent(pageContents, contentPairs);

        List<PageContentOrder> newOrder = pageContentOrderService.updateOrder(page, contentPairs, pageContents);

        pageContentOrderService.deleteRowsThatNotInListForPage(page, newOrder);

        return newOrder.stream().map(PageContentOrder::getPageContent).toList();
    }
}
