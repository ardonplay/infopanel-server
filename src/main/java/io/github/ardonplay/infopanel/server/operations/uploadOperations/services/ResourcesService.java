package io.github.ardonplay.infopanel.server.operations.uploadOperations.services;

import io.github.ardonplay.infopanel.server.operations.uploadOperations.models.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourcesService {
    String uploadResource(MultipartFile multipartFile);

    Resource getResource(String hash);
}
