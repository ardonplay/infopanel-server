package io.github.ardonplay.infopanel.server.entities;

import io.github.ardonplay.infopanel.server.common.entities.PageContent;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageContentTest {

    @Test
    void equals() {
        PageContent first = PageContent.builder().id(1).body(TextElement.builder().content("Test text").build().toJsonNode()).build();

        PageContent second = PageContent.builder().id(2).body(TextElement.builder().content("Test text").build().toJsonNode()).build();

        Assertions.assertEquals(first, second);
    }

    @Test
    void notEquals() {
        PageContent first = PageContent.builder().id(1).body(TextElement.builder().content("Test text").build().toJsonNode()).build();

        PageContent second = PageContent.builder().id(2).body(TextElement.builder().content("Test text second ").build().toJsonNode()).build();

        Assertions.assertNotEquals(first, second);
    }
}
