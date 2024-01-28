package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.models.Pair;
import io.github.ardonplay.infopanel.server.common.entities.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.PageContentOrder;
import io.github.ardonplay.infopanel.server.common.entities.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.common.repositories.PageContentOrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PageContentOrderService {
    private final PageContentOrderRepository pageContentOrderRepository;

    @Transactional
    public void deleteRowsThatNotInListForPage(PageEntity page, List<PageContentOrder> orders) {

        List<Integer> ordersId = orders.stream().map(PageContentOrder::getId).toList();

        pageContentOrderRepository.deleteAllByPageAndPageContentNotInList(page.getId(), ordersId);
    }

    private Map<Pair<Integer, Pair<PageElementType, JsonNode>>, PageContentOrder> createOldOrderMap(PageEntity page) {
        List<PageContentOrder> oldOrder = findAllByPage(page);

        Map<Pair<Integer, Pair<PageElementType, JsonNode>>, PageContentOrder> oldOrderMap = new HashMap<>();

        oldOrder.forEach(order -> oldOrderMap.put(new Pair<>(order.getOrderId(),
                new Pair<>(PageElementType.valueOf
                        (order.getPageContent().getPageElementType().getName()),
                        order.getPageContent().getBody())), order));

        return oldOrderMap;
    }

    private Map<Pair<PageElementType, JsonNode>, PageContent> createPageContentMap(List<PageContent> pageContents) {
        Map<Pair<PageElementType, JsonNode>, PageContent> contentMap = new HashMap<>();

        pageContents.forEach(content -> contentMap.put(new Pair<>(PageElementType.valueOf
                (content.getPageElementType().getName()), content.getBody()), content));
        return contentMap;
    }

    @Transactional
    public List<PageContentOrder> updateOrder(PageEntity page, List<Pair<PageElementType, JsonNode>> contentPairs, List<PageContent> pageContents) {
        List<PageContentOrder> newOrder = new ArrayList<>();

        Map<Pair<PageElementType, JsonNode>, PageContent> contentMap = createPageContentMap(pageContents);

        Map<Pair<Integer, Pair<PageElementType, JsonNode>>, PageContentOrder> oldOrderMap = createOldOrderMap(page);

        int currentIndex = 1;
        for (Pair<PageElementType, JsonNode> currentPair : contentPairs) {
            Pair<Integer, Pair<PageElementType, JsonNode>> pairKey = new Pair<>(currentIndex, currentPair);

            if (!oldOrderMap.containsKey(pairKey)) {
                PageContentOrder newContentOrder = new PageContentOrder(currentIndex, page, contentMap.get(currentPair));
                newContentOrder = pageContentOrderRepository.save(newContentOrder);
                newOrder.add(newContentOrder);
            } else {
                PageContentOrder existingContentOrder = oldOrderMap.get(pairKey);
                newOrder.add(existingContentOrder);
            }
            currentIndex++;
        }
        return newOrder;
    }

    public List<PageContentOrder> findAllByPage(PageEntity page) {
        return pageContentOrderRepository.findAllByPageId(page.getId());
    }

}
