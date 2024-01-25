package io.github.ardonplay.infopanel.server.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.infopanel.server.models.contentElements.TextElement;
import io.github.ardonplay.infopanel.server.models.dtos.PageContentDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.models.dtos.PageFolderDTO;
import io.github.ardonplay.infopanel.server.services.PageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

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

    @BeforeEach
    public void SetUp() {

    }

    @Test
    void getPage_folder() throws Exception {
        PageDTO child = PageDTO.builder().id(2).title("Test page").type("PAGE").parentId(1).orderId(1).build();

        Mockito.when(pageService.getPage(1)).thenReturn(PageFolderDTO.builder()
                .id(1)
                .title("Test folder")
                .type("FOLDER")
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
        ObjectMapper objectMapper = new ObjectMapper();
        TextElement contentElement = new TextElement(objectMapper);
        contentElement.setContent("Test text");

        PageDTO page = PageDTO.builder().id(2).title("Test page").type("PAGE").content(List.of(PageContentDTO.builder().type("TEXT").body(contentElement.toJsonNode()).build())).parentId(1).orderId(1).build();

        Mockito.when(pageService.getPage(2)).thenReturn(page);

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
