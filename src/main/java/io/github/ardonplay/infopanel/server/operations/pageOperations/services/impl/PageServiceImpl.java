package io.github.ardonplay.infopanel.server.operations.pageOperations.services.impl;

import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageFolderDTO;
import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.types.PageTypeEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.exceptions.PageContentIsNullException;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import io.github.ardonplay.infopanel.server.common.repositories.PageRepository;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.PageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;

    private final PageContentServiceImpl pageContentService;

    private final TypeCacheService cacheService;

    public PageEntity getPage(int id, String lang) {

        PageEntity page = pageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Page with id: " + id + " not exist"));

        return page;
    }

    @Override
    @Transactional
    public PageEntity updatePage(PageDTO pageDTO) {

        PageEntity page = pageRepository.findById(pageDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Page with id " + pageDTO.getId() + " not found"));


        if (pageDTO.getType().equals(PageType.PAGE.name())) {
            List<PageEntity> children = page.getChildren();
            if (!children.isEmpty()) {
                children.forEach(child -> child.setParentPage(null));
                page.getChildren().clear();
            }
        }

        if (pageDTO.getType().equals(PageType.FOLDER.name())) {
//            updateChildren((PageFolderDTO) pageDTO, page);
        }

        updatePageType(pageDTO, page);
        updateTitle(pageDTO, page);
        updateParentPage(pageDTO, page);
        updateContent(pageDTO, page);


        return page;
    }

    private void updateChildren(PageFolderDTO folderDTO, PageEntity page) {
        if (folderDTO.getChildren() != null) {
            List<PageEntity> children = pageRepository.findAllById(folderDTO.getChildren().stream().map(PageDTO::getId).toList());
            page.getChildren().clear();
            page.getChildren().addAll(children);
        }
    }

    private void updatePageType(PageDTO pageDTO, PageEntity page) {
        if (pageDTO.getType() != null) {
            PageTypeEntity pageTypeEntity = cacheService.getPageTypes().get(pageDTO.getType());
            page.setPageType(pageTypeEntity);
        }
    }

    private void updateTitle(PageDTO pageDTO, PageEntity page) {
        if (pageDTO.getTitle() != null) {
//            page.setTitle(pageDTO.getTitle());
        }
    }

    private void updateParentPage(PageDTO pageDTO, PageEntity page) {
        if (pageDTO.getParentId() != null) {
            PageEntity parent = pageRepository.findById(pageDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent page with  id " + pageDTO.getParentId() + "not found"));
            page.setParentPage(parent);
        }
    }

    private void updateContent(PageDTO pageDTO, PageEntity page) {
        if (pageDTO.getContent() != null) {
            pageContentService.updateByDTO(page, pageDTO.getContent());
        }
    }


    @Override
    @Transactional
    @SneakyThrows
    public PageEntity savePage(PageDTO pageDTO) {
        if (pageDTO.hasNullImportantValues()) {
            throw new BadRequestException("OMG BRO WHY SO BAD!!!");
        }
        PageEntity page = createPageEntityFromDTO(pageDTO);

        switch (PageType.valueOf(pageDTO.getType())) {
            case FOLDER -> {
                return null;
//                return saveFolderPage(page, (PageFolderDTO) pageDTO);
            }
            case PAGE -> {
                return saveContentPage(page, pageDTO.getContent());
            }
            default -> {
                return null;
            }
        }
    }

    private PageEntity createPageEntityFromDTO(PageDTO pageDTO) {
        PageEntity page = new PageEntity();
//        page.setTitle(pageDTO.getTitle());
        PageTypeEntity pageTypeEntity = cacheService.getPageTypes().get(pageDTO.getType());
        page.setPageType(pageTypeEntity);
        page.setOrderId(pageDTO.getOrderId());

        if (pageDTO.getParentId() != null) {
            page.setParentPage(pageRepository.findById(pageDTO.getParentId()).orElse(null));
        }

        return page;
    }

    private PageEntity saveFolderPage(PageEntity page, PageFolderDTO folderDTO) throws BadRequestException {
        List<PageEntity> children = pageRepository.findAllById(folderDTO.getChildren().stream()
                .map(PageDTO::getId)
                .toList());

        for (PageEntity child : children) {
            if (child.getParentPage() != null) {
                throw new BadRequestException("child id: " + child.getId() + " belongs other page");
            }
        }
        page.setChildren(children);
        pageRepository.save(page);
        return page;
    }

    private PageEntity saveContentPage(PageEntity page, List<PageContentDTO> pageContentDTOS) throws PageContentIsNullException {
        if (pageContentDTOS == null) {
            throw new PageContentIsNullException();
        }
        pageRepository.save(page);
        return page;
    }


    @Override
    public Page<PageEntity> findAll(String lang, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pageRepository.findAll(pageable);
    }

    @Override
    public Integer deletePage(Integer id) {
        pageRepository.delete(pageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Page with id: " + id + " not exist")));
        return id;
    }

}
