package io.github.ardonplay.infopanel.server.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserCRUDTest {
    @Autowired
    private MockMvc mockMvc;


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
    void auth_success() throws Exception {

        String body = """
                {
                       "username": "spring_test",
                       "password": "spring_test"
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("token").exists());
    }

    @Test
    @Sql("/beforeAll.sql")
    void auth_unsuccessful() throws Exception {

        String body = """
                {
                       "username": "spring_tes_that_not_exists",
                       "password": "spring_test"
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized()).andExpect(jsonPath("token").doesNotExist());
    }

    @Test
    @Sql("/beforeAll.sql")
    void signUp_successful() throws Exception {
        String body = """
                {
                       "username": "spring_test_2",
                       "password": "spring_test_2",
                       "user_role": "ROLE_ADMIN"
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/signup")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("username").exists())
                .andExpect(jsonPath("password").exists())
                .andExpect(jsonPath("user_role").exists())
                .andExpect(jsonPath("id").exists());
    }

    @Test
    @Sql("/beforeAll.sql")
    void signUp_unsuccessful() throws Exception {
        String body = """
                {
                       "username": "spring_test",
                       "password": "spring_test_2",
                       "user_role": "ROLE_ADMIN"
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/signup")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }
}