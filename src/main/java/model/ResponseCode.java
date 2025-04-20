package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Table(name = "response_code")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Код ответа (ошибки)")
public class ResponseCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор кода ошибки", example = "101")
    private Long id;

    @Column(name = "error_code")
    @Schema(description = "Код ошибки", example = "404")
    private String errorCode;

    @Column(name = "error_description")
    @Schema(description = "Описание ошибки", example = "Не найдено")
    private String errorDescription;

    @Column(name = "error_level")
    @Schema(description = "Уровень ошибки", example = "Высокий")
    private String errorLevel;
}
