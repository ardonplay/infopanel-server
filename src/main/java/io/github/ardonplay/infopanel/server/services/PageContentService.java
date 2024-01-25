package io.github.ardonplay.infopanel.server.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.Pair;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.entities.PageContent;
import io.github.ardonplay.infopanel.server.models.entities.PageEntity;
import io.github.ardonplay.infopanel.server.models.entities.PageContentOrder;
import io.github.ardonplay.infopanel.server.repositories.PageContentRepository;
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
    public List<PageContent> getAllByContentPairs(List<Pair<String, JsonNode>> contentPairs) {
        return pageContentRepository
                .findPageContentsByPageElementTypeAndBody(
                        contentPairs.stream().map(Pair::first).toList(),
                        contentPairs.stream().map(Pair::second).toList());
    }

    @Transactional
    protected List<PageContent> updatePageContent(List<PageContent> pageContents, List<Pair<String, JsonNode>> contentPairs) {
        List<PageContent> result = new ArrayList<>(pageContents);

        for (Pair<String, JsonNode> pair : contentPairs) {
            if (!pageContentRepository.containsPair(result, pair)) {
                PageContent pageContent = new PageContent(cacheService.getPageElementTypes().get(pair.first()), pair.second());
                pageContent = pageContentRepository.save(pageContent);
                result.add(pageContent);
            }
        }
        return result;
    }

    @Transactional
    public List<PageContent> updateByDTO(PageEntity page, List<PageContentDTO> pageContentDTOS){
        List<Pair<String, JsonNode>> contentPairs = pageContentDTOS.stream().map((content) -> new Pair<>(content.getType(), content.getBody())).toList();

        List<PageContent> pageContents = getAllByContentPairs(contentPairs);

        pageContents = updatePageContent(pageContents, contentPairs);

        List<PageContentOrder> newOrder = pageContentOrderService.updateOrder(page, contentPairs, pageContents);

        pageContentOrderService.deleteRowsThatNotInListForPage(page, newOrder);

        return newOrder.stream().map(PageContentOrder::getPageContent).toList();
    }
}
