package model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "user_access")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Пользователи")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор пользователя", example = "101")
    private Long id;

    @Column(name = "user_login")
    @Schema(description = "Логин пользователя", example = "Alexey12234")
    private String username;

    @Column(name = "user_password")
    @Schema(description = "Пароль пользователя", example = "77627722pomX!")
    private String password;

    @Column(name = "full_name")
    @Schema(description = "Полное имя пользователя", example = "Иванов Алексей Владимирович")
    private String fullName;

    @Column(name = "user_role")
    @Schema(description = "Наменование роли пользователя", example = "manager")
    private String role;


}
