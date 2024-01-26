package io.github.ardonplay.infopanel.server.models.entities;

import io.github.ardonplay.infopanel.server.models.enums.PageElementType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "page_element_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
}

