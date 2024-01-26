package io.github.ardonplay.infopanel.server.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pages")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne()
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private PageEntity parentPage;

    @OneToMany(mappedBy = "parentPage")
    private List<PageEntity> children;

    @ManyToOne()
    @JoinColumn(name = "type", referencedColumnName = "id")
    private PageTypeEntity pageType;

    @OneToMany(mappedBy = "page", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<PageContentOrder> contentOrders;

    @Override
    public String toString(){
        return String.format("{ page: { id: %s, title: %s, pageType:  %s, order: %s } }", id, title, pageType.getName(), orderId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageEntity page = (PageEntity) o;
        return Objects.equals(title, page.title) && Objects.equals(orderId, page.orderId) && Objects.equals(parentPage, page.parentPage) && Objects.equals(children, page.children) && Objects.equals(pageType, page.pageType) && Objects.equals(contentOrders, page.contentOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, orderId, parentPage, children, pageType, contentOrders);
    }
}