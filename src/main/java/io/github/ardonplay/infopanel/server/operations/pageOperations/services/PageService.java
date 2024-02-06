package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.SaveNewPageDTO;
import org.springframework.data.domain.Page;

public interface PageService {
    PageEntity getPage(int id, String lang);


    PageEntity updatePage(PageDTO pageDTO);


    PageEntity savePage(SaveNewPageDTO pageDTO);


    Page<PageEntity> findAll(String lang, int page, int size);

    Integer deletePage(Integer id);
}
