package io.github.ardonplay.infopanel.server.operations.pageOperations.api;

import io.github.ardonplay.infopanel.server.operations.pageOperations.dtos.PageDTO;
import io.github.ardonplay.infopanel.server.operations.pageOperations.services.PageService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/page")
@AllArgsConstructor
public class PageController {

    private final PageService service;

    @GetMapping
    private ResponseEntity<PageDTO> getPage(@RequestParam int id) {
        return new ResponseEntity<>(service.getPage(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    private ResponseEntity<Page<PageDTO>> findAll(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size){
      return new ResponseEntity<>(service.findAll(page, size), HttpStatus.OK);
    }

    @PatchMapping
    private ResponseEntity<PageDTO> updatePage(@RequestBody PageDTO pageDTO) {
        return new ResponseEntity<>(service.updatePage(pageDTO), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<PageDTO> savePage(@RequestBody PageDTO pageDTO) throws BadRequestException {
        return new ResponseEntity<>(service.savePage(pageDTO), HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<PageDTO> deletePage(@RequestParam int id) {
        service.deletePage(id);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setId(id);
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }


}
