package io.github.ardonplay.infopanel.server.models.entities;

import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageElementTypeEntityTest {

    @Test
    void equals() {
        PageElementTypeEntity first = PageElementTypeEntity.builder().id(1).name(PageElementType.TEXT.name()).build();
        PageElementTypeEntity second = PageElementTypeEntity.builder().id(2).name(PageElementType.TEXT.name()).build();

        Assertions.assertEquals(first, second);
    }

    @Test
    void notEquals() {
        PageElementTypeEntity first = PageElementTypeEntity.builder().id(1).name(PageElementType.TEXT.name()).build();
        PageElementTypeEntity second = PageElementTypeEntity.builder().id(2).name("some other type").build();

        Assertions.assertNotEquals(first, second);
    }
}
