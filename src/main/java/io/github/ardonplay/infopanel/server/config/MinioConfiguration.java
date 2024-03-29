package io.github.ardonplay.infopanel.server.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Slf4j
public class MinioConfiguration {
    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucket}")
    public String BUCKET_NAME;

    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        var client = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();

        log.info("BUCKET_NAME: {}", BUCKET_NAME);
        boolean isBucketExist = client.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());

        if (!isBucketExist) {
            client.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
        }

        return client;
    }
}
