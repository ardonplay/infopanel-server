package io.github.ardonplay.infopanel.server.common.entities.page;

import io.github.ardonplay.infopanel.server.common.entities.types.LocalizationTypeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "page_localization")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne()
    @JoinColumn(name = "language", referencedColumnName = "id")
    private LocalizationTypeEntity language;

    @ManyToOne()
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private PageEntity page;
}
