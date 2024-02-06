package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record PageDTO(@NotNull UUID id, PageType type, Map<String, String> title, UUID parentId, Integer orderId,  List<ContentDTO> content) {
}
