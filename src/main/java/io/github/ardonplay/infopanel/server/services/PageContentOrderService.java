package io.github.ardonplay.infopanel.server.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.Pair;
import io.github.ardonplay.infopanel.server.models.entities.PageContent;
import io.github.ardonplay.infopanel.server.models.entities.PageEntity;
import io.github.ardonplay.infopanel.server.models.entities.PageContentOrder;
import io.github.ardonplay.infopanel.server.repositories.PageContentOrderRepository;
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

    private Map<Pair<Integer, Pair<String, JsonNode>>, PageContentOrder> createOldOrderMap(PageEntity page){
        List<PageContentOrder> oldOrder = findAllByPage(page);

        Map<Pair<Integer, Pair<String, JsonNode>>, PageContentOrder> oldOrderMap = new HashMap<>();

        oldOrder.forEach(order -> oldOrderMap.put(new Pair<>(order.getOrderId(), new Pair<>(order.getPageContent().getPageElementType().getName(), order.getPageContent().getBody())), order));

        return oldOrderMap;
    }

    private Map<Pair<String, JsonNode>, PageContent> createPageContentMap(List<PageContent> pageContents){
        Map<Pair<String, JsonNode>, PageContent> contentMap = new HashMap<>();

        pageContents.forEach(content -> contentMap.put(new Pair<>(content.getPageElementType().getName(), content.getBody()), content));
        return  contentMap;
    }

    @Transactional
    public List<PageContentOrder> updateOrder(PageEntity page, List<Pair<String, JsonNode>> contentPairs, List<PageContent> pageContents){
        List<PageContentOrder> newOrder = new ArrayList<>();

        Map<Pair<String, JsonNode>, PageContent> contentMap = createPageContentMap(pageContents);

        Map<Pair<Integer, Pair<String, JsonNode>>, PageContentOrder> oldOrderMap = createOldOrderMap(page);

        int currentIndex = 1;
        for (Pair<String, JsonNode> currentPair : contentPairs) {
            Pair<Integer, Pair<String, JsonNode>> pairKey = new Pair<>(currentIndex, currentPair);

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

    public List<PageContentOrder> findAllByPage(PageEntity page){
       return pageContentOrderRepository.findAllByPage(page);
    }

}
