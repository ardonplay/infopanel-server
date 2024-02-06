package io.github.ardonplay.infopanel.server.common.entities.types;

import io.github.ardonplay.infopanel.server.common.entities.user.UserEntity;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL)
    private List<UserEntity> users;

    public UserRoleEntity(UserType type){
        this.name = type.name();
    }

    @Override
    public String toString(){
        return name;
    }
}
