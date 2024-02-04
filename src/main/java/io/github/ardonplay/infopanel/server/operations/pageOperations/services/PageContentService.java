package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageContentDTO;

import java.util.List;

public interface PageContentService {

    List<PageContent> updateByDTO(PageEntity page, List<PageContentDTO> pageContentDTOS);
}
