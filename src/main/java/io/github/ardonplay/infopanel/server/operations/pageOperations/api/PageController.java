package io.github.ardonplay.infopanel.server.operations.pageOperations.api;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.AbstractPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.localized.LocalizedPageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.impl.PageServiceImpl;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/page")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PATCH})
public class PageController {

    private final PageServiceImpl service;

    private final TypeCacheService cacheService;

    private final EntityMapper<PageEntity, PageDTO> pageMapper;

    private final EntityMapper<PageEntity, PageFolderDTO> pageFolderMapper;

    private final EntityMapper<PageEntity, LocalizedPageDTO> localizedPageMapper;

    private final EntityMapper<PageEntity, LocalizedPageFolderDTO> localizedPageFolderMapper;

    @GetMapping("/{lang}")
    private ResponseEntity<AbstractPageDTO> getPage(@RequestParam int id, @PathVariable String lang) {
        PageEntity page = service.getPage(id, lang);
        AbstractPageDTO pageDTO = null;
        if(page.getPageType().getName().equals(PageType.PAGE.name())){
            pageDTO = localizedPageMapper.toDto(page);
        }
        else {
            pageDTO = localizedPageFolderMapper.toDto(page);
        }
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @GetMapping("/{lang}/all")
    private ResponseEntity<Page<AbstractPageDTO>> findAll(@PathVariable String lang, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size) {
        Page<PageEntity> pages = service.findAll(lang, page, size);

        Page<AbstractPageDTO> dtos = pages.map(pageabe -> {
            AbstractPageDTO pageDTO;
            if(pageabe.getPageType().getName().equals(PageType.PAGE.name())){
                pageDTO = localizedPageMapper.toDto(pageabe);
            }
            else {
                pageDTO = localizedPageFolderMapper.toDto(pageabe);
            }
            return pageDTO;
        });

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PatchMapping
    private ResponseEntity<AbstractPageDTO> updatePage(@RequestBody PageDTO pageDTO) {
        PageEntity page = service.updatePage(pageDTO);
        AbstractPageDTO dto;
        if(page.getPageType().getName().equals(PageType.PAGE.name())){
            dto = pageMapper.toDto(page);
        }
        else {
            dto = pageFolderMapper.toDto(page);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<AbstractPageDTO> savePage(@RequestBody PageDTO pageDTO) {
        PageEntity page =service.savePage(pageDTO);
        AbstractPageDTO dto;
        if(page.getPageType().getName().equals(PageType.PAGE.name())){
            dto = pageMapper.toDto(page);
        }
        else {
            dto = pageFolderMapper.toDto(page);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<Integer> deletePage(@RequestParam int id) {
        service.deletePage(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
