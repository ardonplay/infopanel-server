package io.github.ardonplay.infopanel.server.operations.uploadOperations.api;


import io.github.ardonplay.infopanel.server.operations.uploadOperations.services.ResourcesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.HEAD, RequestMethod.GET, RequestMethod.POST})
@AllArgsConstructor
public class UploadController {

    private final ResourcesService resourcesService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getResource(@PathVariable String id) {
        var resource = resourcesService.getResource(id);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(resource.getType());


        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(resource.getFileStream()));
    }

    @PostMapping()
    public ResponseEntity<?> postMultipleResource(@RequestParam("resources") List<MultipartFile> files) {
        log.info("START [upload]");
        long time = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        for (var file : files) {
            long fileTime = System.currentTimeMillis();
            log.info("START [upload][file]");
            var id = resourcesService.uploadResource(file);
            result.append(id).append('\n');
            log.info("FINISH [upload][file] [{}]", System.currentTimeMillis() - fileTime);
        }
        log.info("FINISH [upload] [{}]", System.currentTimeMillis() - time);

        return ResponseEntity.ok(result);
    }
}
