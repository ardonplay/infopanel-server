package io.github.ardonplay.infopanel.server.common.entities;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "page_type")
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class PageTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    public PageTypeEntity(PageType type){
        this.name = type.name();
    }

    @OneToMany(mappedBy = "pageType", cascade = CascadeType.ALL)
    private List<PageEntity> pages;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageTypeEntity pageTypeEntity = (PageTypeEntity) o;
        return Objects.equals(name, pageTypeEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
