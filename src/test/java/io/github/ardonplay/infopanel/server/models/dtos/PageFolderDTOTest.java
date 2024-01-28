package io.github.ardonplay.infopanel.server.models.dtos;

import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.models.enums.PageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PageFolderDTOTest {

    @Test
    void equals(){
        PageContentDTO contentDTO = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageDTO pageDTO = PageDTO.builder().id(1).type(PageType.PAGE.name()).title("First test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();

        PageFolderDTO first = PageFolderDTO.builder().id(2).orderId(1).children(List.of(pageDTO)).title("Test folder").type(PageType.FOLDER.name()).build();
        PageFolderDTO second = PageFolderDTO.builder().id(2).orderId(1).children(List.of(pageDTO)).title("Test folder").type(PageType.FOLDER.name()).build();

        Assertions.assertEquals(first, second);
    }

    @Test
    void notEquals(){
        PageContentDTO contentDTO = PageContentDTO.builder().type(PageElementType.TEXT).body(TextElement.builder().content("Test content").build().toJsonNode()).build();

        PageDTO pageDTO = PageDTO.builder().id(1).type(PageType.PAGE.name()).title("First test page DTO").parentId(2).orderId(1).content(List.of(contentDTO)).build();

        PageFolderDTO first = PageFolderDTO.builder().id(2).orderId(1).children(List.of(pageDTO)).title("Test folder").type(PageType.FOLDER.name()).build();
        PageFolderDTO second = PageFolderDTO.builder().id(3).orderId(1).children(List.of(pageDTO)).title("Test folder").type(PageType.FOLDER.name()).build();

        Assertions.assertNotEquals(first, second);
    }
}
