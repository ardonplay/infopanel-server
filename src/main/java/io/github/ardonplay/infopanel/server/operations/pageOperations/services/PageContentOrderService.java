package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.common.models.Pair;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;

import java.util.List;
import java.util.Map;

public interface PageContentOrderService {

    List<PageContentOrder> updateOrder(PageEntity page, List<Pair<PageElementType, Map<String, JsonNode>>> contentPairs, List<PageContent> pageContents);

    void deleteRowsThatNotInListForPage(PageEntity page, List<PageContentOrder> orders);
}
