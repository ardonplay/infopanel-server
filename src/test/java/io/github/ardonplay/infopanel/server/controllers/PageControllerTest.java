package io.github.ardonplay.infopanel.server.controllers;


import io.github.ardonplay.infopanel.server.operations.pageOperations.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.PageService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;


import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageService pageService;

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
    void getPage_folder() throws Exception {
        PageDTO child = PageDTO.builder().id(2).title("Test page").type(PageType.PAGE.name()).parentId(1).orderId(1).build();

        when(pageService.getPage(1)).thenReturn(PageFolderDTO.builder()
                .id(1)
                .title("Test folder")
                .type(PageType.FOLDER.name())
                .orderId(1)
                .children(List.of(child)).build());

        mockMvc.perform(get("/api/v1/page?id={id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test folder"))
                .andExpect(jsonPath("$.type").value("FOLDER"))
                .andExpect(jsonPath("$.order_id").value(1))
                .andExpect(jsonPath("$.children[0].id").value(2))
                .andExpect(jsonPath("$.children[0].title").value("Test page"))
                .andExpect(jsonPath("$.children[0].type").value("PAGE"))
                .andExpect(jsonPath("$.children[0].order_id").value(1))
                .andExpect(jsonPath("$.children[0].parent_id").value(1));

        verify(pageService, times(1)).getPage(1);
    }

    @Test
    void getPage_page() throws Exception {
        TextElement contentElement = new TextElement("Test text");

        PageDTO page = PageDTO.builder().id(2).title("Test page").type(PageType.PAGE.name()).content(List.of(PageContentDTO.builder().type(PageElementType.TEXT).body(contentElement.toJsonNode()).build())).parentId(1).orderId(1).build();

        when(pageService.getPage(2)).thenReturn(page);

        mockMvc.perform(get("/api/v1/page?id={id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                       
                        {
                               "id": 2,
                               "title": "Test page",
                               "type": "PAGE",
                               "order_id": 1,
                               "parent_id": 1,
                               "content": [
                                   {
                                       "type": "TEXT",
                                       "body": {
                                           "content": "Test text"
                                       }
                                   }
                               ]
                           }"""));

        verify(pageService, times(1)).getPage(2);
    }

}
