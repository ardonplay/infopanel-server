package io.github.ardonplay.infopanel.server.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PageCRUDTest {
    @Autowired
    private MockMvc mockMvc;

    private String token;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withInitScript("init.sql")
            .withDatabaseName("infopanel")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio")
            .withExposedPorts(9000, 9001)
            .withEnv(Map.of("MINIO_ACCESS_KEY", "minio1234567890",
                    "MINIO_SECRET_KEY", "minio1234567890"));


    @BeforeAll
    static void startContainers() {
        postgreSQLContainer.start();
        minIOContainer.start();
    }

    @AfterAll
    static void stopContainers() {
        postgreSQLContainer.start();
        minIOContainer.stop();
    }

    @BeforeEach
    void getToken() throws Exception {
        String body = """
                {
                       "username": "spring_test",
                       "password": "spring_test"
                   }
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("Response: " + response);
        response = response.replace("{\"token\":\"", "");
        token = response.replace("\"}", "");
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
    @Sql("/beforeAll.sql")
    void getPage_folder() throws Exception {

        mockMvc.perform(get("/api/v1/page?id={id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                          {
                            "id": 1,
                            "type": "FOLDER",
                            "title": "TEST_FOLDER",
                            "order_id": 1,
                            "children": [
                                {
                                    "id": 2,
                                    "type": "PAGE",
                                    "title": "TEST_PAGE",
                                    "order_id": 1,
                                    "parent_id": 1
                                }
                            ]
                        }
                          """));

    }

    @Test
    @Sql("/beforeAll.sql")
    void getPage_page() throws Exception {
        mockMvc.perform(get("/api/v1/page?id={id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                         {
                             "id": 2,
                             "type": "PAGE",
                             "title": "TEST_PAGE",
                             "order_id": 1,
                             "parent_id": 1,
                             "content": [
                                 {
                                     "type": "TEXT",
                                     "body": {
                                         "content": "Hello, this is test"
                                     }
                                 }
                             ]
                         }
                        """));

    }

    @Test
    @Sql("/beforeAll.sql")
    void updatePage_page_content_only() throws Exception {
        System.out.println("token: " + token);
        mockMvc.perform(patch("/api/v1/page")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                 {
                                    "id": 2,
                                    "type": "PAGE",
                                    "title": "TEST_PAGE",
                                    "order_id": 1,
                                    "parent_id": 1,
                                    "content": [
                                        {
                                            "type": "TEXT",
                                            "body": {
                                                "content": "Hello, this is editted test"
                                            }
                                        }
                                    ]
                                }
                                  """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                             "id": 2,
                             "type": "PAGE",
                             "title": "TEST_PAGE",
                             "order_id": 1,
                             "parent_id": 1,
                             "content": [
                                 {
                                     "type": "TEXT",
                                     "body": {
                                         "content": "Hello, this is editted test"
                                     }
                                 }
                             ]
                         }
                         """));
    }

    @Test
    @Sql("/beforeAll.sql")
    void updateSamePageTwoTimes() throws Exception {
        updatePage_page_content_only();
        updatePage_page_content_only();
    }

    @Test
    @Sql("/beforeAll.sql")
    void addPage_page() throws Exception {
        mockMvc.perform(post("/api/v1/page").contentType(APPLICATION_JSON).content("""
                         {
                            "type": "PAGE",
                            "title": "TEST_PAGE",
                            "order_id": 1,
                            "parent_id": 1,
                            "content": [
                                {
                                    "type": "TEXT",
                                    "body": {
                                        "content": "Hello, this is editted test"
                                    }
                                }
                            ]
                        }
                          """) .header(HttpHeaders.AUTHORIZATION, "Bearer " +token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                               
                        {
                            "id": 3,
                            "type": "PAGE",
                            "title": "TEST_PAGE",
                            "order_id": 1,
                            "parent_id": 1,
                            "content": [
                                {
                                    "type": "TEXT",
                                    "body": {
                                        "content": "Hello, this is editted test"
                                    }
                                }
                            ]
                        }
                        """));
    }

    @Test
    @Sql("/beforeAll.sql")
    void deletePage() throws Exception {
        mockMvc.perform(delete("/api/v1/page?id={id}", 1).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isOk());
    }

    @Test
    @Sql("/beforeAll.sql")
    void deletePageThatAlreadyDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/page?id={id}", 1).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/page?id={id}", 1).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isBadRequest());
    }

}
