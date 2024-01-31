package io.github.ardonplay.infopanel.server.common.entities.pageContent;

import io.github.ardonplay.infopanel.server.common.entities.types.PageElementTypeEntity;
import jakarta.persistence.*;
import lombok.*;

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



    @ManyToOne()
    @JoinColumn(name = "element_type", referencedColumnName = "id")
    private PageElementTypeEntity pageElementType;

    @OneToMany(mappedBy = "pageContent", cascade=CascadeType.ALL)
    private List<PageContentOrder> orders;

    @OneToMany(mappedBy = "content", cascade=CascadeType.ALL)
    private List<PageContentLocalization> localizations;


    public PageContent(PageElementTypeEntity pageElementTypeEntity) {
        this.pageElementType = pageElementTypeEntity;
    }

    @Override
    public String toString() {
        return "PageContent{" +
                "id=" + id +
                ", body=" + localizations.toString() +
                ", type=" + pageElementType.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageContent content = (PageContent) o;
        return Objects.equals(localizations, content.localizations) && Objects.equals(pageElementType, content.pageElementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localizations, pageElementType);
    }
}
