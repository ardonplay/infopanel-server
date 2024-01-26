package io.github.ardonplay.infopanel.server.services.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.models.entities.*;
import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.models.enums.PageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class PageMapperTest {
    @Autowired
    private PageMapper pageMapper;

    private PageDTO expectedPageDTO;
    @BeforeEach
    void setUp(){
        expectedPageDTO = PageDTO.builder().id(1).title("Test page").type(PageType.PAGE.name()).orderId(1).build();
    }
    @Test
    void mapToPageDTOWithoutContent() {

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity(PageType.PAGE))
                .id(1).title("Test page").orderId(1).build();

        PageDTO pageDTO = pageMapper.mapToPageDTOWithoutContent(pageEntity);

        Assertions.assertEquals(expectedPageDTO, pageDTO);
    }


    @Test
    void mapToPageDTOWithContent() {

        TextElement element = new TextElement("This is text of test content");

        expectedPageDTO.setContent(List.of(PageContentDTO.builder().type(PageElementType.TEXT).body(element.toJsonNode()).build()));

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity("PAGE"))
                .id(1).title("Test page").orderId(1).build();
        PageContentOrder contentOrder = PageContentOrder.builder()
                .page(pageEntity).orderId(1)
                .pageContent(PageContent.builder()
                        .body(element.toJsonNode())
                        .pageElementType(PageElementTypeEntity.builder()
                                .name("TEXT")
                                .build())
                        .build())
                .build();

        pageEntity.setContentOrders(List.of(contentOrder));
        PageDTO pageDTO = pageMapper.mapToPageDTO(pageEntity);

        Assertions.assertEquals(expectedPageDTO, pageDTO);
    }

    @Test
    void mapToFolderDTO(){
        PageDTO expectedFolderDTO = PageFolderDTO.builder()
                .id(2)
                .title("Test folder")
                .type(PageType.FOLDER.name())
                .orderId(1)
                .children(List.of(expectedPageDTO)).build();

        expectedPageDTO.setParentId(2);

        PageEntity pageEntity = PageEntity.builder().pageType(new PageTypeEntity(PageType.PAGE))
                .id(1).title("Test page").orderId(1).build();

        PageEntity pageFolder = PageEntity.builder().pageType(new PageTypeEntity(PageType.FOLDER))
                .id(2).title("Test folder").orderId(1).children(List.of(pageEntity)).build();

        PageFolderDTO pageFolderDTO = pageMapper.mapToFolder(pageFolder);

        Assertions.assertEquals(expectedFolderDTO, pageFolderDTO);
    }

}
