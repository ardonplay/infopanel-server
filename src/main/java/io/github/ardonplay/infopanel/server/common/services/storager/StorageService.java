package io.github.ardonplay.infopanel.server.common.services.storager;


import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;

public interface StorageService {
    InputStream getFile(String hash);

    void putFile(String hash, InputStream inputStream);

    Iterable<Result<Item>> getAllFiles();
}
