package io.github.ardonplay.infopanel.server.common.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.ardonplay.infopanel.server.operations.userOperations.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonPropertyOrder({"id", "username", "password", "user_role"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonProperty("password")
    @Column(name = "pass")
    private String password;

    @NonNull
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role", referencedColumnName = "id")
    private UserRoleEntity userRole;

    @JsonProperty("user_role")
    public UserRole getUserRoleAsEnum(){
        return UserRole.valueOf(userRole.getName());
    }
}
