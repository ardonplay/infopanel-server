package io.github.ardonplay.infopanel.server.common.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

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
