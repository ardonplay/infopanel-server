package io.github.ardonplay.infopanel.server.common.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    private String pass;

    @NonNull
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role", referencedColumnName = "id")
    private UserRoleEntity userRole;
}
