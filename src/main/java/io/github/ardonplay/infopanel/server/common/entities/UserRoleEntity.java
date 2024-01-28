package io.github.ardonplay.infopanel.server.common.entities;

import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_role")
@NoArgsConstructor
@Getter
@Setter
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL)
    private List<User> users;

    public UserRoleEntity(UserType type){
        this.name = type.name();
    }
}
