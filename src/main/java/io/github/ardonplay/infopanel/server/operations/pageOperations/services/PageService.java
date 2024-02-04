package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface PageService {
    PageEntity getPage(int id, String lang);


    PageEntity updatePage(PageDTO pageDTO);


    PageEntity savePage(PageDTO pageDTO);


    Page<PageEntity> findAll(String lang, int page, int size);

    Integer deletePage(Integer id);
}
