package io.github.ardonplay.infopanel.server.models.entities;

import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
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
