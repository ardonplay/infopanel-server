package io.github.ardonplay.infopanel.server.common.entities.pageContent;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.entities.types.LocalizationTypeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "page_content_localization")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageContentLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "body", columnDefinition = "jsonb")
    private JsonNode body;

    @ManyToOne()
    @JoinColumn(name = "language", referencedColumnName = "id")
    private LocalizationTypeEntity language;

    @ManyToOne()
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private PageContent content;

}
