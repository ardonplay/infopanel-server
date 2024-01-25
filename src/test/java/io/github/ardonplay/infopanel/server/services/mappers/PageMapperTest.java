package io.github.ardonplay.infopanel.server.services.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.infopanel.server.ServerApplication;
import io.github.ardonplay.infopanel.server.ServerApplicationTest;
import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.models.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ServerApplication.class)
public class PageMapperTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mapToPageDTOWithoutContent() {
        PageMapper pageMapper = new PageMapperImpl();

        PageDTO expectedDTO = PageDTO.builder().id(1).title("Test page").type("PAGE").orderId(1).build();

        PageEntity pageEntity = PageEntity.builder().pageType(new PageType("PAGE"))
                .id(1).title("Test page").orderId(1).build();

        PageDTO pageDTO = pageMapper.mapToPageDTOWithoutContent(pageEntity);

        Assertions.assertEquals(expectedDTO, pageDTO);
    }

    @Test
    public void mapToPageDTOWithContent() {
        PageMapper pageMapper = new PageMapperImpl();

        PageDTO expectedDTO = PageDTO.builder().id(1).title("Test page").type("PAGE").orderId(1).build();

        TextElement element = new TextElement(objectMapper);
        element.setContent("This is text of test content");

        expectedDTO.setContent(List.of(PageContentDTO.builder().type("TEXT").body(element.toJsonNode()).build()));
        PageEntity pageEntity = PageEntity.builder().pageType(new PageType("PAGE"))
                .id(1).title("Test page").orderId(1).build();
        PageContentOrder contentOrder = PageContentOrder.builder()
                .page(pageEntity).orderId(1)
                .pageContent(PageContent.builder()
                        .body(element.toJsonNode())
                        .pageElementType(PageElementType.builder()
                                .name("TEXT")
                                .build())
                        .build())
                .build();

        pageEntity.setContentOrders(List.of(contentOrder));
        PageDTO pageDTO = pageMapper.mapToPageDTO(pageEntity);

        Assertions.assertEquals(expectedDTO, pageDTO);
    }

}
