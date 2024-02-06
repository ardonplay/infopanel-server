package io.github.ardonplay.infopanel.server.common.entities.pageContent;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.entities.types.LocalizationTypeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "page_content_localization")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageContentLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
