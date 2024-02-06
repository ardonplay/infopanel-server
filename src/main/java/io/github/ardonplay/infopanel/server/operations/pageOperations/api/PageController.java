package io.github.ardonplay.infopanel.server.operations.pageOperations.api;

import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.ContentDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.GetPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.SaveNewPageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.dto.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/page")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.DELETE})
@AllArgsConstructor
public class PageController {

    @GetMapping("/{lang}/{id}")
    public ResponseEntity<GetPageDTO> getPage(@Valid @PathVariable String lang, @PathVariable UUID id){
        return new ResponseEntity<>(new GetPageDTO(id, PageType.PAGE, "Hi", UUID.randomUUID(),1, List.of(new ContentDTO(UUID.randomUUID()))),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<PageDTO> updatePage(@Valid @RequestBody PageDTO pageDTO){
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PageDTO> savePage(@Valid @RequestBody SaveNewPageDTO pageDTO){
        return new ResponseEntity<>(new PageDTO(UUID.randomUUID(), pageDTO.type(), pageDTO.title(), pageDTO.parentId(), pageDTO.orderId(), pageDTO.content()), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePage(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
