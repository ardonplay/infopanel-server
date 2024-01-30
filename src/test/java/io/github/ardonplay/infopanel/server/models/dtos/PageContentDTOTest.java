package io.github.ardonplay.infopanel.server.models.dtos;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageContentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageContentDTOTest {

    @Test
    void equals(){
        PageContentDTO first = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageContentDTO second = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        Assertions.assertEquals(first, second);
    }
    @Test
    void notEquals(){
        PageContentDTO first = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageContentDTO second = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content second").build().toJsonNode()).build();

        Assertions.assertNotEquals(first, second);
    }
}
