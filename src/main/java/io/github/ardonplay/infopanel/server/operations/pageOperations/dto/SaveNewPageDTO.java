package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SaveNewPageDTO(@NotNull PageType type, @NotNull Map<String, String> title, @NotBlank UUID parentId, @NotNull Integer orderId, @NotBlank List<ContentDTO> content) {
}
