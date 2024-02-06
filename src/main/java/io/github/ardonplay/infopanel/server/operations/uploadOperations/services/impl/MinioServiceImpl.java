package io.github.ardonplay.infopanel.server.operations.uploadOperations.services.impl;

import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceAlreadyExistException;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceException;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.models.Resource;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.services.StorageService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Service
public class MinioServiceImpl implements StorageService {

    @Value("${minio.bucket}")
    public String BUCKET_NAME;
    private final MinioClient minio;

    @Autowired
    public MinioServiceImpl(MinioClient minio) {
        this.minio = minio;
    }

    @Override
    public Resource getFile(String hash) {
        try {
            GetObjectResponse object = minio.getObject(
                    GetObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(hash)
                            .build()
            );
            return new Resource(object, MediaType.valueOf(Objects.requireNonNull(object.headers().get("Content-Type"))));
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from MinIO", e);
        }
    }

    @Override
    public void putFile(String hash, MultipartFile multipartFile) throws ResourceAlreadyExistException, ResourceException {
        try {

            InputStream inputStream = multipartFile.getInputStream();
            if (!isObjectExist(hash)) {
                minio.putObject(PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(hash)
                        .contentType(multipartFile.getContentType())
                        .stream(inputStream, inputStream.available(), -1).build());
                inputStream.close();
            } else {
                inputStream.close();
                throw new ResourceAlreadyExistException("File already exist at MinIO");
            }
        } catch (ResourceAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceException("Failed to upload file to MinIO", e);
        }
    }

    @Override
    public void deleteFile(String hash) {
        try {
            minio.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(hash)
                    .build());
        } catch (Exception e) {
            throw new ResourceException("Failed to delete file", e);
        }

    }


    @Override
    public Iterable<Result<Item>> getAllFiles() {
        return minio.listObjects(ListObjectsArgs.builder().bucket(BUCKET_NAME).build());
    }

    public boolean isObjectExist(String name) {
        try {
            minio.statObject(StatObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(name).build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
