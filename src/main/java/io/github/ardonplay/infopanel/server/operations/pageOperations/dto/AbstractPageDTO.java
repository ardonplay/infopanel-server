package io.github.ardonplay.infopanel.server.operations.pageOperations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPageDTO {
    protected Integer id;

    protected String type;

    protected Integer orderId;

    protected Integer parentId;
}
