package io.github.ardonplay.infopanel.server.common.entities.pageContent;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "page_content_order")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class PageContentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NonNull
    private Integer orderId;

    @NonNull
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private PageEntity page;

    @NonNull
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private PageContent pageContent;


    @Override
    public String toString() {
        return "PageContentOrder{" +
                "orderId=" + orderId +
                ", page=" + page.getId() +
                ", pageContent=" + pageContent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageContentOrder that = (PageContentOrder) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(page, that.page) && Objects.equals(pageContent, that.pageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, page, pageContent);
    }
}
