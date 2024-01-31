package io.github.ardonplay.infopanel.server.services.mappers;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentOrder;
import io.github.ardonplay.infopanel.server.common.entities.types.PageElementTypeEntity;
import io.github.ardonplay.infopanel.server.common.entities.types.PageTypeEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.PageMapper;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers.PageMapperImpl;
import org.junit.jupiter.api.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Map;


public class PageMapperTest {

    private final PageMapper pageMapper = new PageMapperImpl();

    private PageDTO expectedPageDTO;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withInitScript("init.sql")
            .withDatabaseName("infopanel")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    static MinIOContainer minIOContainer =  new MinIOContainer("minio/minio")
            .withUserName("testuser")
            .withPassword("testpassword")
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


    @BeforeEach
    void setUp() {
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
    void mapToFolderDTO() {
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
