package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentDTO(@NotNull UUID id, String type, String title) {
    public ContentDTO(UUID id) {
        this(id, null, null);
    }
}
