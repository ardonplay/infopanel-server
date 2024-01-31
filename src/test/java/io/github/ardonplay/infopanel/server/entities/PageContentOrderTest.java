package io.github.ardonplay.infopanel.server.entities;

import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.types.PageTypeEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PageContentOrderTest {


    private PageContent pageContent;

    private PageEntity pageEntity;


    @BeforeEach
    void setUp(){
        pageEntity = PageEntity.builder().id(1).title("Test page").orderId(1).pageType(new PageTypeEntity(PageType.PAGE)).build();

        pageContent = PageContent.builder().id(1).body(TextElement.builder().content("Test text").build().toJsonNode()).build();
    }
    @Test
    void equals(){

        PageContentOrder first = PageContentOrder.builder().id(1).page(pageEntity).pageContent(pageContent).orderId(1).build();

        PageContentOrder second = PageContentOrder.builder().id(2).page(pageEntity).pageContent(pageContent).orderId(1).build();

        Assertions.assertEquals(first, second);
    }

    @Test
    void notEquals(){
        PageContentOrder first = PageContentOrder.builder().id(1).page(pageEntity).pageContent(pageContent).orderId(1).build();

        PageContentOrder second = PageContentOrder.builder().id(2).page(pageEntity).pageContent(pageContent).orderId(2).build();

        Assertions.assertNotEquals(first, second);
    }
}
