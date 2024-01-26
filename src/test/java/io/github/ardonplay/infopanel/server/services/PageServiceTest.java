package io.github.ardonplay.infopanel.server.services;

import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.models.entities.*;
import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.models.enums.PageType;
import io.github.ardonplay.infopanel.server.repositories.PageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class PageServiceTest {

    @MockBean
    private PageRepository pageRepository;

    @MockBean
    private PageContentService pageContentService;

    @Autowired
    private PageService pageService;

    @MockBean
    private TypeCacheService cacheService;


    @Test
    void getPage_page(){
        int pageId = 2;
        PageDTO expectedPage = PageDTO.builder().id(pageId).title("Test page").type(PageType.PAGE.name())
                .content(List.of(PageContentDTO.builder()
                        .type(PageElementType.TEXT).body(new TextElement("This is text of test content").toJsonNode()).build())).parentId(1).orderId(1).build();
        TextElement element = new TextElement("This is text of test content");

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity(PageType.PAGE))
                .id(pageId).title("Test page").orderId(1).build();
        PageContentOrder contentOrder = PageContentOrder.builder()
                .page(pageEntity).orderId(1)
                .pageContent(PageContent.builder()
                        .body(element.toJsonNode())
                        .pageElementType(new PageElementTypeEntity(PageElementType.TEXT))
                        .build())
                .build();
        pageEntity.setContentOrders(List.of(contentOrder));
        PageEntity pageFolder = PageEntity.builder().pageType(new PageTypeEntity(PageType.FOLDER))
                .id(1).title("Test folder").orderId(1).children(List.of(pageEntity)).build();

        when(pageRepository.findById(pageId)).thenReturn(Optional.of(pageEntity));
        PageDTO entity = pageService.getPage(pageId);
        verify(pageRepository, times(1)).findById(pageId);
        Assertions.assertEquals(expectedPage, entity);
    }

    @Test
    void getPage_folder(){
        int pageId = 1;
        PageDTO page = PageDTO.builder().id(2).title("Test page").type(PageType.PAGE.name()).parentId(1).orderId(1).build();
       PageFolderDTO expectedDTO =  PageFolderDTO.builder()
                .id(pageId)
                .title("Test folder")
                .type(PageType.FOLDER.name())
                .orderId(1)
                .children(List.of(page)).build();

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity(PageType.PAGE))
                .id(2).title("Test page").orderId(1).build();
        PageEntity expectedPageFolder = PageEntity.builder().pageType(new PageTypeEntity(PageType.FOLDER))
                .id(pageId).title("Test folder").orderId(1).children(List.of(pageEntity)).build();

        when(pageRepository.findById(pageId)).thenReturn(Optional.of(expectedPageFolder));
        PageDTO entity = pageService.getPage(pageId);
        verify(pageRepository, times(1)).findById(pageId);
        Assertions.assertEquals(expectedDTO, entity);
    }

    @Test
    void updatePage(){
        int pageId = 1;
        PageDTO page = PageDTO.builder().id(pageId).title("Test page").type(PageType.PAGE.name()).parentId(1).orderId(1).build();
        PageFolderDTO expectedDTO =  PageFolderDTO.builder()
                .id(1)
                .title("Test folder")
                .type(PageType.FOLDER.name())
                .orderId(1)
                .children(List.of(page)).build();

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity(PageType.PAGE))
                .id(pageId).title("Test page").orderId(1).build();
        PageEntity expectedPageFolder = PageEntity.builder().pageType(new PageTypeEntity(PageType.FOLDER))
                .id(1).title("Test folder").orderId(1).children(List.of(pageEntity)).build();

        when(pageRepository.findById(pageId)).thenReturn(Optional.of(expectedPageFolder));
        PageDTO entity = pageService.getPage(pageId);
        verify(pageRepository, times(1)).findById(pageId);
        Assertions.assertEquals(expectedDTO, entity);
    }

}
