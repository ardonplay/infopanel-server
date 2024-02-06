package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;

import java.util.List;
import java.util.UUID;

public record GetPageDTO(UUID id, PageType type, String title, UUID parentId, Integer orderId, List<ContentDTO> content) {
}
