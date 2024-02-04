package io.github.ardonplay.infopanel.server.services;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.common.entities.types.PageElementTypeEntity;
import io.github.ardonplay.infopanel.server.common.entities.types.PageTypeEntity;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import io.github.ardonplay.infopanel.server.common.repositories.PageRepository;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.impl.PageContentServiceImpl;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.impl.PageServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class PageServiceTest {

    @MockBean
    private PageRepository pageRepository;

    @MockBean
    private PageContentServiceImpl pageContentService;

    @Autowired
    private PageServiceImpl pageService;

    @MockBean
    private TypeCacheService cacheService;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withInitScript("init.sql")
            .withDatabaseName("infopanel")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    static MinIOContainer minIOContainer =  new MinIOContainer("minio/minio")
            .withExposedPorts(9000, 9001)
            .withEnv(Map.of("MINIO_ACCESS_KEY", "minio1234567890",
                    "MINIO_SECRET_KEY", "minio1234567890"));


    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        minIOContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.start();
        minIOContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("minio.url", minIOContainer::getS3URL);
        registry.add("minio.accessKey", () -> "minio1234567890");
        registry.add("minio.secretKey", () -> "minio1234567890");
        registry.add("minio.bucket", () -> "resources");
    }

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
