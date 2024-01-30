package io.github.ardonplay.infopanel.server.controllers;

import io.github.ardonplay.infopanel.server.operations.uploadOperations.services.StorageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.DigestUtils;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageService storageService;

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
        MvcResult result = mockMvc.perform(post("/api/v1/auth")
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
    void uploadFileTest() throws Exception {
        URL resource = getClass().getClassLoader().getResource("TestImage.png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            MockMultipartFile file = new MockMultipartFile("resources",
                    "file", MediaType.IMAGE_PNG.toString(), Files.readAllBytes(Path.of(resource.getPath())));

            InputStream stream = file.getInputStream();
            String expectedHash = DigestUtils.md5DigestAsHex(stream);
            stream.close();

            mockMvc.perform(multipart("/api/v1/resource").file(file).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isOk()).andExpect(content().string(expectedHash));

            storageService.deleteFile(expectedHash);
        }
    }

    @Test
    @Sql("/beforeAll.sql")
    void uploadFileThatUploaded() throws Exception {
        URL resource = getClass().getClassLoader().getResource("TestImage.png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            MockMultipartFile file = new MockMultipartFile("resources",
                    "file", MediaType.IMAGE_PNG.toString(), Files.readAllBytes(Path.of(resource.getPath())));

            InputStream stream = file.getInputStream();
            String expectedHash = DigestUtils.md5DigestAsHex(stream);
            stream.close();

            storageService.putFile(expectedHash, file);
            mockMvc.perform(multipart("/api/v1/resource").file(file).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isOk()).andExpect(content().string(expectedHash));
            storageService.deleteFile(expectedHash);
        }
    }

    @Test
    void getFileThatExists() throws Exception {
        URL resource = getClass().getClassLoader().getResource("TestImage.png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            MockMultipartFile file = new MockMultipartFile("resources",
                    "file", MediaType.IMAGE_PNG.toString(), Files.readAllBytes(Path.of(resource.getPath())));

            InputStream stream = file.getInputStream();
            String expectedHash = DigestUtils.md5DigestAsHex(stream);
            stream.close();

            storageService.putFile(expectedHash, file);
            mockMvc.perform(get("/api/v1/resource/{id}", expectedHash)).andExpect(status().isOk()).andExpect(content().bytes(file.getBytes()));
            storageService.deleteFile(expectedHash);
        }
    }


    @Test
    void getFileThatNotExists() throws Exception {
        URL resource = getClass().getClassLoader().getResource("TestImage.png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            MockMultipartFile file = new MockMultipartFile("resources",
                    "file", MediaType.IMAGE_PNG.toString(), Files.readAllBytes(Path.of(resource.getPath())));

            InputStream stream = file.getInputStream();
            String expectedHash = DigestUtils.md5DigestAsHex(stream);
            stream.close();

            storageService.putFile(expectedHash, file);
            mockMvc.perform(get("/api/v1/resource/{id}", "some hash")).andExpect(status().isBadRequest());
            storageService.deleteFile(expectedHash);
        }
    }


}
