package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Table(name = "acquiring_bank")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Принимающий банк")
public class AcquiringBank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор принимающего банка", example = "1")
    private Long id;

    @Column(name = "bic")
    @Schema(description = "BIC банка", example = "RA000000000")
    private String bic;

    @Column(name = "abbreviated_name")
    @Schema(description = "Сокращённое название банка", example = "РБ")
    private String abbreviatedName;
}
