package io.github.ardonplay.infopanel.server.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "page_element_type")
@NoArgsConstructor
@Getter
@Setter
public class PageElementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pageElementType", cascade = CascadeType.ALL)
    private List<PageContent> pageContents;
}

