package io.github.ardonplay.infopanel.server.models.dtos;

import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PageDTOTest {

    @Test
    void notEquals(){

        PageContentDTO contentDTO = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageDTO first = PageDTO.builder().id(1).title("First test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();
        PageDTO second = PageDTO.builder().id(3).title("Second test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();

        Assertions.assertNotEquals(first, second);
    }

    @Test
    void equals(){

        PageContentDTO contentDTO = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageDTO first = PageDTO.builder().id(1).title("First test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();
        PageDTO second = PageDTO.builder().id(1).title("First test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();

        Assertions.assertEquals(first, second);
    }


}
