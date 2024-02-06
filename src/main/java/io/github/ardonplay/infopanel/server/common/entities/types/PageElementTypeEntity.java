package io.github.ardonplay.infopanel.server.common.entities.types;

import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContent;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "page_element_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PageElementTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pageElementType", cascade = CascadeType.ALL)
    private List<PageContent> pageContents;

    public PageElementTypeEntity(PageElementType type){
        this.name = type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageElementTypeEntity that = (PageElementTypeEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

