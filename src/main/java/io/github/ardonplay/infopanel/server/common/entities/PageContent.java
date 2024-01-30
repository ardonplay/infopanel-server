package io.github.ardonplay.infopanel.server.common.entities;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "page_content")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "body", columnDefinition = "jsonb")
    private JsonNode body;

    @ManyToOne()
    @JoinColumn(name = "element_type", referencedColumnName = "id")
    private PageElementTypeEntity pageElementType;

    @OneToMany(mappedBy = "pageContent", cascade=CascadeType.ALL)
    private List<PageContentOrder> orders;

    public PageContent(PageElementTypeEntity pageElementTypeEntity, JsonNode body) {
        this.pageElementType = pageElementTypeEntity;
        this.body = body;
    }

    @Override
    public String toString() {
        return "PageContent{" +
                "id=" + id +
                ", body=" + body.toString() +
                ", type=" + pageElementType.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageContent content = (PageContent) o;
        return Objects.equals(body, content.body) && Objects.equals(pageElementType, content.pageElementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, pageElementType);
    }
}
