package io.github.ardonplay.infopanel.server.common.entities.types;

import io.github.ardonplay.infopanel.server.common.entities.page.PageLocalization;
import io.github.ardonplay.infopanel.server.common.entities.pageContent.PageContentLocalization;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "localization_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LocalizationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "language", cascade=CascadeType.ALL)
    private List<PageContentLocalization> pageContentLocalizations;

    @OneToMany(mappedBy = "language", cascade=CascadeType.ALL)
    private List<PageLocalization> pageLocalizations;
}
