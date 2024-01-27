package io.github.ardonplay.infopanel.server.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PageCRUDTest {
    @Autowired
    private MockMvc mockMvc;

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3").withInitScript("init.sql");


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
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
        mockMvc.perform(patch("/api/v1/page").contentType(APPLICATION_JSON).content("""
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
                          """))
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
                          """))
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
        mockMvc.perform(delete("/api/v1/page?id={id}", 1)).andExpect(status().isOk());
    }

    @Test
    @Sql("/beforeAll.sql")
    void deletePageThatAlreadyDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/page?id={id}", 1)).andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/page?id={id}", 1)).andExpect(status().isBadRequest());
    }

}
