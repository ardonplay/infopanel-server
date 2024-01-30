package io.github.ardonplay.infopanel.server.operations.uploadOperations.services.impl;

import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceAlreadyExistException;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceException;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceNotExistException;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.models.Resource;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.services.StorageService;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.services.ResourcesService;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@AllArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {
    private final StorageService storageService;

    @Override
    public String uploadResource(MultipartFile multipartFile) {
        String hash = null;
        try {

            InputStream inputStream = multipartFile.getInputStream();
            hash = DigestUtils.md5DigestAsHex(inputStream);
            inputStream.close();

            syncWithStorage(hash, multipartFile);

            return hash;
        } catch (ResourceAlreadyExistException e) {
            return hash;
        } catch (IOException | MinioException e) {
            throw new ResourceException(e);
        }
    }


    private void syncWithStorage(String hash, MultipartFile multipartFile) throws IOException, MinioException, ResourceAlreadyExistException {
        storageService.putFile(hash, multipartFile);
    }

    @Override
    public Resource getResource(String hash) {
        try {
            return storageService.getFile(hash);
        } catch (RuntimeException e) {
            throw new ResourceNotExistException("Can't load this resource", e);
        }
    }
}
